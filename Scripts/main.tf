provider "aws" {
    region     = "us-east-1"
    access_key = "Give your access key"
    secret_key = "Give your secret access key"
}

resource "aws_instance" "example" {
    ami           = "ami-04b4f1a9cf54c11d0"
    instance_type = "t2.micro"
    vpc_security_group_ids = [ "sg-0a7667825425d9f20" ]
    user_data = <<-EOF
                #!/bin/bash
                echo "This is the webserver deployment using the terraform" > index.html
                nohup busybox httpd -f -p 8080 &
              EOF   

    tags = {
        Name = "Terraform-Webserver"
    }
}

resource "aws_security_group" "TerraformSG" {
    name = "TerraformSG"
    ingress {
        from_port   = 8080
        to_port     = 8080
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
}