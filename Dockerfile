# Build-Stage
FROM gradle:8.10.0-jdk21-alpine AS backend-build

WORKDIR /app
COPY ./src ./src
COPY ./build.gradle ./
COPY ./settings.gradle ./
RUN gradle clean build -x test --no-daemon

# Build-Stage für Frontend
FROM node:20-alpine AS frontend-build

WORKDIR /app
COPY ./src-ui ./src-ui
WORKDIR /app/src-ui
RUN npm ci
RUN npm run build

# Production-Stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=backend-build /app/build/libs/*.jar app.jar
COPY --from=frontend-build /app/src-ui/build /app/static

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

# Nicht-Root-Benutzer erstellen und verwenden
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]