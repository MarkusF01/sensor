FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY ./target/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]