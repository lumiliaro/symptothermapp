# Build-Stage
FROM gradle:8.10.0-jdk21-alpine AS build

WORKDIR /app
COPY ./src .
RUN gradle clean build -x test --no-daemon

# Production-Stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

# Nicht-Root-Benutzer erstellen und verwenden
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]