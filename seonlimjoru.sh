#!/bin/bash

# EC2 인스턴스의 퍼블릭 DNS 주소
EC2_PUBLIC_DNS="ec2-3-34-133-25.ap-northeast-2.compute.amazonaws.com"

# SSH로 EC2 인스턴스에 접속하여 실행 중인 도커 컨테이너를 모두 종료합니다.
ssh -i "sshKey2.pem" ec2-user@$EC2_PUBLIC_DNS 'sudo docker container stop $(sudo docker container ls -aq)'

# 이미지 인덱스를 저장할 파일 경로를 설정합니다.
INDEX_FILE="image_index.txt"

# 이미지 인덱스를 파일에서 읽어옵니다. 없을 경우 기본값은 1입니다.
if [ -f "$INDEX_FILE" ]; then
    IMAGE_INDEX=$(cat "$INDEX_FILE")
else
    IMAGE_INDEX=1
fi

# 이미지 이름을 설정합니다.
IMAGE_NAME="seonlimjoru${IMAGE_INDEX}"

# 첫 번째 빌드 전에 gradle clean build 실행
./gradlew clean build

# 이미지 빌드 및 푸시
docker build --no-cache -t $IMAGE_NAME --platform linux/amd64 .
docker tag $IMAGE_NAME:latest kurigohan73/$IMAGE_NAME:latest
docker push kurigohan73/$IMAGE_NAME:latest

# 이미지 인덱스를 1 증가시킵니다.
((IMAGE_INDEX++))

# 다음에 실행될 때 사용할 이미지 인덱스를 파일에 저장합니다.
echo "$IMAGE_INDEX" > "$INDEX_FILE"

# SSH로 EC2 인스턴스에 접속하여 새로운 이미지를 실행합니다.
# keystore.p12 파일을 EC2 인스턴스로 복사
scp -i "sshKey2.pem" /Users/makisekurisu/Downloads/MarketRestore/marketplace/rank/backend/keystore.p12 ec2-user@$EC2_PUBLIC_DNS:/home/ec2-user/keystore.p12

# SSH로 EC2 인스턴스에 접속하여 새로운 이미지를 실행합니다.
ssh -i "sshKey2.pem" ec2-user@$EC2_PUBLIC_DNS "sudo docker run -d -p 443:443 -v /home/ec2-user/keystore.p12:/keystore.p12 kurigohan73/$IMAGE_NAME:latest"

## EC2 인스턴스의 퍼블릭 DNS 주소
#EC2_PUBLIC_DNS="ec2-3-34-133-25.ap-northeast-2.compute.amazonaws.com"
#
## SSH로 EC2 인스턴스에 접속하여 실행 중인 도커 컨테이너를 모두 종료합니다.
#ssh -i "sshKey2.pem" ec2-user@$EC2_PUBLIC_DNS 'sudo docker container stop $(sudo docker container ls -aq)'
#
## 이미지 이름 설정
#IMAGE_NAME="seonlimjoru"
#
## 첫 번째 빌드 전에 gradle clean build 실행
#./gradlew clean build
#
## 이미지 빌드 및 푸시
#docker build --no-cache -t $IMAGE_NAME --platform linux/amd64 .
#docker tag $IMAGE_NAME:latest kurigohan73/$IMAGE_NAME:latest
#docker push kurigohan73/$IMAGE_NAME:latest
#
## SSH로 EC2 인스턴스에 접속하여 새로운 이미지를 실행합니다.
#ssh -i "sshKey2.pem" ec2-user@$EC2_PUBLIC_DNS "sudo docker run -d -p 8080:8080 kurigohan73/$IMAGE_NAME:latest"
