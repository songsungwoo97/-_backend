# 베이스 이미지 설정
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 포트 설정
EXPOSE 443

# 애플리케이션 JAR 파일 추가
COPY build/libs/rank-0.0.1-SNAPSHOT.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
