# Backend-Build-Stage
FROM gradle:8.10.1-jdk21-alpine AS backend-build

WORKDIR /app
COPY ./src .

WORKDIR /app
ENV SPRING_PROFILES_ACTIVE=prod
RUN gradle clean build -x test --no-daemon

# Frontend-Build-Stage
FROM node:22-alpine AS frontend-build

WORKDIR /app
COPY ./src-ui ./ui

WORKDIR /app/ui
RUN npm ci
RUN npx @rtk-query/codegen-openapi openapi-config.ts
RUN npm run build

# Production-Stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY --from=backend-build /app/build/libs/*.jar app.jar
COPY --from=frontend-build /app/ui/dist /app/static

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

# Nicht-Root-Benutzer erstellen und verwenden
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]