provider "aws" {
	region = var.region
}

provider "kubernetes" {
  host = data.aws_eks_cluster.cloudcart_cluster.endpoint
  cluster_ca_certificate = base64decode(data.aws_eks_cluster.cloudcart_cluster.certificate_authority[0].data)
  token = data.aws_eks_cluster_auth.cloudcart_cluster.token
}

provider "helm" {
  kubernetes = {
    host = data.aws_eks_cluster.cloudcart_cluster.endpoint
    cluster_ca_certificate = base64decode(data.aws_eks_cluster.cloudcart_cluster.certificate_authority[0].data)
    token = data.aws_eks_cluster_auth.cloudcart_cluster.token
  }
}
