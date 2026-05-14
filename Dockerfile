# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Speed up builds
COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -DskipTests package spring-boot:repackage


FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Spring Boot jar name is based on artifactId/version
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]

