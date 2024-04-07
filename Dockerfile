FROM openjdk:17-slim
WORKDIR /app
COPY target/product-service-0.0.1-SNAPSHOT.jar /app/product-service.jar
EXPOSE 8081
CMD ["java", "-jar", "product-service.jar"]
