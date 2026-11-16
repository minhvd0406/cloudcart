resource "aws_internet_gateway" "cloudcart_igw" {
  vpc_id = aws_vpc.cloudcart_vpc.id

  tags = {
    Name = "cloudcart_igw"
  }
}