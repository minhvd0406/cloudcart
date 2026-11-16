resource "aws_vpc" "cloudcart_vpc" {
	cidr_block = var.vpc_cidr

	tags = {
		Name = "cloudcart_vpc"
	}
}