# Stage 1: Build stage
FROM gradle:8.4-jdk17-alpine AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
RUN gradle build --no-daemon --parallel --continue || true
COPY src ./src
RUN gradle clean build -x test --no-daemon

# Stage 2: Production stage
FROM openjdk:17-jdk-slim
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
