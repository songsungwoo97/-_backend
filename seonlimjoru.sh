#!/bin/bash

# EC2 인스턴스의 퍼블릭 DNS 주소
EC2_PUBLIC_DNS="ec2-3-34-133-25.ap-northeast-2.compute.amazonaws.com"

# SSH 키 파일 경로
SSH_KEY="sshKey2.pem"

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

# 빌드가 성공했는지 확인
if [ ! -f "build/libs/rank-0.0.1-SNAPSHOT.jar" ]; then
    echo "Error: JAR file not found. Build might have failed."
    exit 1
fi

# 이미지 빌드 및 푸시
docker build --no-cache -t kurigohan73/$IMAGE_NAME --platform linux/amd64 .
docker push kurigohan73/$IMAGE_NAME:latest

# 이미지 인덱스를 1 증가시킵니다.
((IMAGE_INDEX++))

# 다음에 실행될 때 사용할 이미지 인덱스를 파일에 저장합니다.
echo "$IMAGE_INDEX" > "$INDEX_FILE"

# docker-compose.yml 파일 동적으로 생성
cat <<EOF > docker-compose.yml
version: '3.8'

services:
  app:
    image: kurigohan73/${IMAGE_NAME}:latest
    ports:
      - "443:443"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://testrds.c7k1ulhtgd52.ap-northeast-2.rds.amazonaws.com:3306/fuck
      SPRING_DATASOURCE_USERNAME: admin
      SPRING_DATASOURCE_PASSWORD: abcd1234
      SPRING_DATA_MONGODB_URI: mongodb://mongodb:27017/chatapp
      SPRING_JPA_DATABASE_PLATFORM: org.hibernate.dialect.MySQL8Dialect
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
      SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL: true
      SPRING_JPA_PROPERTIES_HIBERNATE_SHOW_SQL: true
      SPRING_JPA_DEFER_DATASOURCE_INITIALIZATION: true
      OAUTH_KAKAO_CLIENT_ID: 920865d3fcdbfd024c9e2f35b102beb6
      OAUTH_KAKAO_REDIRECT_URI: https://repo-frontend-chi.vercel.app/oauth/redirected/kakao
      OAUTH_KAKAO_CLIENT_SECRET: dXIKF4iENKboF2RGWERWSgOLZwRRbfQ4
      OAUTH_NAVER_CLIENT_ID: tsCjaf_Sguy3on43jxmt
      OAUTH_NAVER_REDIRECT_URI: https://repo-frontend-chi.vercel.app/oauth/redirected/naver
      OAUTH_NAVER_CLIENT_SECRET: F5BCjIhXHv
      JWT_HEADER: Authorization
      JWT_SECRET: a2FyaW10b2thcmltdG9rYXJpbXRva2FyaW10b2thcmltdG9rYXJpbXRva2FyaW10b2thcmltdG9rYXJpbXRva2FyaW10b2thcmltdG9rYXJpbXRva2FyaW10b2thcmltdG9rYXJpbXRva2FyaW10b2thcmltdG9rYXJpbXRva2FyaW10b2thcmltdG9rYXJpbQ==
      JWT_TOKEN_VALIDITY_IN_SECONDS: 86400000
      SERVER_SSL_KEY_STORE: /keystore.p12
      SERVER_SSL_KEY_STORE_PASSWORD: abcd1234
      SERVER_SSL_KEY_STORE_TYPE: PKCS12
      SERVER_SSL_KEY_ALIAS: mysslkey
    volumes:
      - ./keystore.p12:/keystore.p12
    depends_on:
      - mongodb

  mongodb:
    image: mongo:4.4
    ports:
      - "27017:27017"
    volumes:
      - mongo_data:/data/db

volumes:
  mongo_data:
EOF

# keystore.p12 파일을 EC2 인스턴스로 복사
scp -i "$SSH_KEY" /Users/makisekurisu/Downloads/MarketRestore/marketplace/rank/backend/keystore.p12 ec2-user@$EC2_PUBLIC_DNS:/home/ec2-user/keystore.p12

# 동적으로 생성된 docker-compose.yml 파일을 EC2 인스턴스로 복사
scp -i "$SSH_KEY" docker-compose.yml ec2-user@$EC2_PUBLIC_DNS:/home/ec2-user/docker-compose.yml

# SSH로 EC2 인스턴스에 접속하여 Docker와 Docker Compose 설치 및 실행
ssh -i "$SSH_KEY" ec2-user@$EC2_PUBLIC_DNS << EOF
    # Docker 설치
    sudo dnf update -y
    sudo dnf install -y docker
    sudo systemctl start docker
    sudo systemctl enable docker
    sudo usermod -aG docker ec2-user
    newgrp docker

    # Docker Compose 설치
    sudo curl -L "https://github.com/docker/compose/releases/download/v2.12.2/docker-compose-\$(uname -s)-\$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
    docker-compose --version

    # 기존 컨테이너 중지 및 삭제
    sudo docker-compose -f /home/ec2-user/docker-compose.yml down

    # Docker Hub에서 최신 이미지 가져오기
    sudo docker pull kurigohan73/$IMAGE_NAME:latest

    # 새로운 컨테이너 실행
    sudo docker-compose -f /home/ec2-user/docker-compose.yml up -d

    # 로그 확인
    sudo docker-compose -f /home/ec2-user/docker-compose.yml logs
EOF
