import { randomBytes } from 'node:crypto';
import {
  closeSync,
  existsSync,
  mkdirSync,
  openSync,
  readFileSync,
  readdirSync,
  writeFileSync,
} from 'node:fs';
import net from 'node:net';
import path from 'node:path';
import { spawn, spawnSync } from 'node:child_process';
import { fileURLToPath } from 'node:url';

const rootDirectory = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const backendDirectory = path.join(rootDirectory, 'microservice-backend');
const frontendDirectory = path.join(rootDirectory, 'frontend');
const runtimeDirectory = path.join(rootDirectory, '.runtime');
const dataDirectory = path.join(runtimeDirectory, 'data');
const logsDirectory = path.join(runtimeDirectory, 'logs');
const outboxDirectory = path.join(runtimeDirectory, 'outbox');
const jwtSecretFile = path.join(runtimeDirectory, 'jwt-secret');
const internalTokenFile = path.join(runtimeDirectory, 'internal-token');
const isWindows = process.platform === 'win32';
const command = (name) => isWindows && ['mvn', 'npm'].includes(name) ? `${name}.cmd` : name;

const applications = [
  { name: 'service-registry', port: 8761 },
  { name: 'category-service', port: 9000 },
  { name: 'product-service', port: 9010 },
  { name: 'notification-service', port: 9020 },
  { name: 'auth-service', port: 9030 },
  { name: 'user-service', port: 9050 },
  { name: 'cart-service', port: 9060 },
  { name: 'order-service', port: 9070 },
  { name: 'api-gateway', port: 8080 },
];

const childProcesses = [];
let shuttingDown = false;

function verifyCommand(name, args) {
  const executable = command(name);
  const result = spawnSync(executable, args, {
    encoding: 'utf8',
    shell: executable.endsWith('.cmd'),
  });
  if (result.error || result.status !== 0) {
    throw new Error(`${name} is required but was not found on PATH.`);
  }
  const version = `${result.stdout ?? ''}${result.stderr ?? ''}`.trim().split(/\r?\n/, 1)[0];
  console.log(`[tool] ${name}: ${version}`);
}

function run(commandName, args, cwd, environment = process.env) {
  const result = spawnSync(commandName, args, {
    cwd,
    env: environment,
    shell: commandName.endsWith('.cmd'),
    stdio: 'inherit',
  });
  if (result.error || result.status !== 0) {
    throw new Error(`Command failed in ${cwd}: ${commandName} ${args.join(' ')}`);
  }
}

function prepareRuntime() {
  for (const directory of [dataDirectory, logsDirectory, outboxDirectory]) {
    mkdirSync(directory, { recursive: true });
  }
  if (!existsSync(jwtSecretFile)) {
    writeFileSync(jwtSecretFile, randomBytes(48).toString('base64'), { encoding: 'utf8', mode: 0o600 });
  }
  if (!existsSync(internalTokenFile)) {
    writeFileSync(internalTokenFile, randomBytes(32).toString('base64url'), { encoding: 'utf8', mode: 0o600 });
  }
}

function localEnvironment() {
  return {
    ...process.env,
    DEBUG: 'false',
    CLOUDCART_DATA_DIR: dataDirectory,
    CLOUDCART_OUTBOX_DIR: outboxDirectory,
    CLOUDCART_NOTIFICATION_MODE: 'outbox',
    CLOUDCART_JWT_SECRET: readFileSync(jwtSecretFile, 'utf8').trim(),
    CLOUDCART_INTERNAL_TOKEN: readFileSync(internalTokenFile, 'utf8').trim(),
    VITE_API_BASE_URL: 'http://localhost:8080/api',
  };
}

function buildProject(environment) {
  console.log('\n[build] Building Spring Boot applications...');
  for (const application of applications) {
    console.log(`[build] ${application.name}`);
    run(command('mvn'), ['--batch-mode', '--quiet', 'clean', 'package'], path.join(backendDirectory, application.name), environment);
  }

  console.log('\n[build] Installing and building the frontend...');
  run(command('npm'), ['ci'], frontendDirectory, environment);
  run(command('npm'), ['run', 'build'], frontendDirectory, environment);
}

function findJar(applicationName) {
  const targetDirectory = path.join(backendDirectory, applicationName, 'target');
  const jarName = readdirSync(targetDirectory).find((fileName) =>
    fileName.endsWith('.jar') && !fileName.endsWith('.jar.original'));
  if (!jarName) {
    throw new Error(`No runnable jar found for ${applicationName}.`);
  }
  return path.join(targetDirectory, jarName);
}

function startProcess(name, executable, args, cwd, environment) {
  const logPath = path.join(logsDirectory, `${name}.log`);
  const logDescriptor = openSync(logPath, 'w');
  const child = spawn(executable, args, {
    cwd,
    env: environment,
    detached: !isWindows,
    shell: executable.endsWith('.cmd'),
    stdio: ['ignore', logDescriptor, logDescriptor],
  });
  closeSync(logDescriptor);
  childProcesses.push({ name, child, logPath });
  child.once('exit', (code) => {
    if (!shuttingDown) {
      console.error(`[failed] ${name} exited with code ${code}. See ${logPath}`);
      shutdown(1);
    }
  });
  return child;
}

function startApplication(application, environment) {
  const serviceDirectory = path.join(backendDirectory, application.name);
  startProcess(application.name, 'java', ['-jar', findJar(application.name)], serviceDirectory, {
    ...environment,
    SERVER_PORT: String(application.port),
  });
}

function canConnect(port) {
  return new Promise((resolve) => {
    const socket = net.createConnection({ host: '127.0.0.1', port });
    socket.setTimeout(500);
    socket.once('connect', () => {
      socket.destroy();
      resolve(true);
    });
    const unavailable = () => {
      socket.destroy();
      resolve(false);
    };
    socket.once('error', unavailable);
    socket.once('timeout', unavailable);
  });
}

async function waitForPort(name, port, timeoutMs = 120_000) {
  const deadline = Date.now() + timeoutMs;
  while (Date.now() < deadline) {
    if (await canConnect(port)) {
      console.log(`[ready] ${name.padEnd(22)} http://localhost:${port}`);
      return;
    }
    await new Promise((resolve) => setTimeout(resolve, 500));
  }
  throw new Error(`${name} did not become ready on port ${port} within ${timeoutMs / 1000}s.`);
}

async function assignAvailableServicePorts() {
  const reservedPorts = new Set(applications.map(({ port }) => port));
  for (const application of applications.slice(1, -1)) {
    if (!await canConnect(application.port)) {
      continue;
    }

    const configuredPort = application.port;
    let candidate = configuredPort + 100;
    while (reservedPorts.has(candidate) || await canConnect(candidate)) {
      candidate += 1;
    }
    reservedPorts.delete(configuredPort);
    reservedPorts.add(candidate);
    application.port = candidate;
    console.log(`[port] ${application.name}: ${configuredPort} is busy, using ${candidate}`);
  }
}

async function assertPublicPortsAvailable() {
  const requiredPorts = [applications[0], applications.at(-1), { name: 'frontend', port: 5173 }];
  for (const application of requiredPorts) {
    if (await canConnect(application.port)) {
      throw new Error(`Port ${application.port} is already in use (${application.name}).`);
    }
  }
}

function stopProcess(child) {
  if (!child.pid || child.exitCode !== null) {
    return;
  }
  if (isWindows) {
    spawnSync('taskkill', ['/pid', String(child.pid), '/T', '/F'], { stdio: 'ignore', shell: false });
  } else {
    try {
      process.kill(-child.pid, 'SIGTERM');
    } catch {
      // The process already stopped.
    }
  }
}

function shutdown(exitCode = 0) {
  if (shuttingDown) {
    return;
  }
  shuttingDown = true;
  console.log('\n[shutdown] Stopping CloudCart processes...');
  for (const { child } of [...childProcesses].reverse()) {
    stopProcess(child);
  }
  process.exit(exitCode);
}

async function main() {
  console.log('CloudCart local launcher');
  verifyCommand('java', ['-version']);
  verifyCommand('mvn', ['-version']);
  verifyCommand('node', ['--version']);
  verifyCommand('npm', ['--version']);

  prepareRuntime();
  const environment = localEnvironment();
  await assertPublicPortsAvailable();
  await assignAvailableServicePorts();
  buildProject(environment);

  const registry = applications[0];
  console.log('\n[start] Starting service registry...');
  startApplication(registry, environment);
  await waitForPort(registry.name, registry.port);

  console.log('\n[start] Starting microservices...');
  const microservices = applications.slice(1, -1);
  for (const application of microservices) {
    startApplication(application, environment);
  }
  await Promise.all(microservices.map((application) => waitForPort(application.name, application.port)));

  const gateway = applications.at(-1);
  console.log('\n[start] Starting API gateway...');
  startApplication(gateway, environment);
  await waitForPort(gateway.name, gateway.port);

  console.log('\n[start] Starting React frontend...');
  startProcess('frontend', command('npm'), ['run', 'dev', '--', '--host', '0.0.0.0'], frontendDirectory, environment);
  await waitForPort('frontend', 5173);

  console.log('\nCloudCart is ready:');
  console.log('  Storefront:       http://localhost:5173');
  console.log('  API Gateway:      http://localhost:8080');
  console.log('  Eureka Dashboard: http://localhost:8761');
  console.log(`  Notification outbox: ${outboxDirectory}`);
  console.log(`  Logs:                ${logsDirectory}`);
  console.log('\nPress Ctrl+C to stop every process.');
}

process.on('SIGINT', () => shutdown(0));
process.on('SIGTERM', () => shutdown(0));

main().catch((error) => {
  console.error(`\n[error] ${error.message}`);
  shutdown(1);
});
