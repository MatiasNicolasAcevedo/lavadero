FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/lavadero-0.0.1-SNAPSHOT.jar lavadero.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "lavadero.jar"]