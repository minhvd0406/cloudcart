resource "aws_eip" "cloudcart_nat_gateway_eip" {
    domain = "vpc"
    tags = {
        Name = "cloudcart_nat_gateway_eip"
    }
}

resource "aws_nat_gateway" "cloudcart_nat_gateway" {
    allocation_id = aws_eip.cloudcart_nat_gateway_eip.id
    subnet_id = aws_subnet.cloudcart_public_subnet_1a.id

    tags = {
        Name = "cloudcart_nat_gateway"
    }

    depends_on = [
        aws_eip.cloudcart_nat_gateway_eip
    ]
}
