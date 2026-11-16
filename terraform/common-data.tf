data "aws_iam_user" "github_actions_user" {
    user_name = var.github_actions_user_name
}

data "aws_iam_user" "local_cli_user" {
    user_name = var.local_cli_user_name
}

data "aws_caller_identity" "current" {
}

data "aws_eks_cluster" "cloudcart_cluster" {
    name = aws_eks_cluster.cloudcart_cluster.name
}

data "aws_eks_cluster_auth" "cloudcart_cluster" {
    name = aws_eks_cluster.cloudcart_cluster.name
}

# eksctl utils associate-iam-oidc-provider --region=us-east-1 --cluster=cloudcart-cluster --approve
# eksctl create iamserviceaccount --cluster=cloudcart-cluster --namespace=kube-system --name=alb-controller --attach-policy-arn=arn:aws:iam::<AWS_ACCOUNT_ID>:policy/AWSLoadBalancerControllerIAMPolicy --override-existing-serviceaccounts --region us-east-1 --approve
# eksctl delete iamserviceaccount --region=us-east-1 --cluster=cloudcart-cluster --namespace=kube-system --name=alb-controller

# helm repo add eks https://aws.github.io/eks-charts
# helm repo update eks
# aws eks update-kubeconfig --region us-east-1 --name cloudcart-cluster
# helm upgrade aws-load-balancer-controller eks/aws-load-balancer-controller -n kube-system --set clusterName=cloudcart-cluster --set serviceAccount.create=false --set serviceAccount.name=alb-controller --set region=us-east-1 --set vpcId=<VPC_ID> --version 1.13.0
# kubectl rollout restart deploy aws-load-balancer-controller -n kube-system
# aws iam create-policy-version --policy-arn arn:aws:iam::<AWS_ACCOUNT_ID>:policy/AWSLoadBalancerControllerIAMPolicy --policy-document file://"<PATH_TO_POLICY_JSON>" --set-as-default
