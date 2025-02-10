FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/auth-service.jar auth-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
