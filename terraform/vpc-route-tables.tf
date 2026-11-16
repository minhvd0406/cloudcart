resource "aws_route_table" "cloudcart_public_rt" {
    vpc_id = aws_vpc.cloudcart_vpc.id

    route {
        cidr_block = var.vpc_cidr
        gateway_id = "local"
    }

    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.cloudcart_igw.id
    }

    tags = {
        Name = "cloudcart_public_rt"
    }
}

resource "aws_route_table" "cloudcart_private_rt" {
    vpc_id = aws_vpc.cloudcart_vpc.id

    route {
        cidr_block = var.vpc_cidr
        gateway_id = "local"
    }

    route {
        cidr_block = "0.0.0.0/0"
        nat_gateway_id = aws_nat_gateway.cloudcart_nat_gateway.id
    }

    tags = {
        Name = "cloudcart_private_rt"
    }
}

resource "aws_route_table_association" "cloudcart_public_subnet_1a_rt" {
  subnet_id      = aws_subnet.cloudcart_public_subnet_1a.id
  route_table_id = aws_route_table.cloudcart_public_rt.id

  depends_on = [
    aws_subnet.cloudcart_public_subnet_1a,
    aws_route_table.cloudcart_public_rt
  ]
}

resource "aws_route_table_association" "cloudcart_public_subnet_1b_rt" {
  subnet_id      = aws_subnet.cloudcart_public_subnet_1b.id
  route_table_id = aws_route_table.cloudcart_public_rt.id

  depends_on = [
    aws_subnet.cloudcart_public_subnet_1b,
    aws_route_table.cloudcart_public_rt
  ]
}

resource "aws_route_table_association" "cloudcart_private_subnet_1a_rt" {
  subnet_id      = aws_subnet.cloudcart_private_subnet_1a.id
  route_table_id = aws_route_table.cloudcart_private_rt.id

  depends_on = [
    aws_subnet.cloudcart_private_subnet_1a,
    aws_route_table.cloudcart_private_rt
  ]
}

resource "aws_route_table_association" "cloudcart_private_subnet_1b_rt" {
  subnet_id      = aws_subnet.cloudcart_private_subnet_1b.id
  route_table_id = aws_route_table.cloudcart_private_rt.id

  depends_on = [
    aws_subnet.cloudcart_private_subnet_1b,
    aws_route_table.cloudcart_private_rt
  ]
}