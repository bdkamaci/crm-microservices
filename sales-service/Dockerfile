FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/sales-service.jar sales-service.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "sales-service.jar"]
