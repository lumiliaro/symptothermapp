# Stage: compile-frontend
# Purpose: Compiles the frontend
# Notes:
#  - Does NPM stuff with Typescript and such
FROM node:20.2.0-slim AS compile-frontend

WORKDIR /app

COPY /src-ui/package*.json ./
RUN npm ci && npm cache clean --force

COPY /src-ui .

ARG NODE_ENV=production
ENV NODE_ENV=$NODE_ENV

RUN npm run build

# Stage: backend
# Purpose: The final image
# Backend Build Stage
FROM gradle:8.10.0-jdk21 AS backend-build

WORKDIR /app/src
COPY ./src /app/src

# COPY build.gradle settings.gradle ./
RUN gradle clean build -x test

# Stage: backend production
# Purpose: The final image
# Backend Build Stage
FROM openjdk:21-jdk-slim AS backend-production

WORKDIR /app/src

COPY --from=backend-build /app/src/build/libs/*.jar app.jar

ENV SPRING_DATASOURCE_URL=jdbc:hsqldb:file:/app/db/hsqldb/db

# Stage: frontend production
# Purpose: The final image
# Backend Build Stage
FROM nginx:1.27.1-alpine AS frontend-production

WORKDIR /app

# copy frontend
COPY --from=compile-frontend --chown=1000:1000 /app/dist /usr/share/nginx/html
COPY /src-ui/.docker/app/nginx/nginx.conf /etc/nginx/nginx.conf
COPY /src-ui/.docker/app/nginx/conf.d/ /etc/nginx/conf.d/
COPY /src-ui/.docker/app/entrypoint.sh /entrypoint.sh
COPY /src-ui/.docker/app/nginx/init-scripts/ /docker-entrypoint.d/

# ARG PORT=80
# ENV NGINX_PORT=${PORT}
# ENV NGINX_HOST=localhost


# Starten des Backends und NGINX über Supervisor, damit beide gleichzeitig laufen können
FROM alpine AS production

RUN apk add --no-cache openjdk21-jre nginx supervisor

WORKDIR /app

# Installiere Supervisor
RUN apk add --no-cache supervisor
# Install gettext to use envsubst
RUN apk add --no-cache gettext

# Kopiere die Backend-JAR und das Frontend von den vorherigen Stages
COPY --from=backend-production /app/src/app.jar /app/src/app.jar
COPY --from=frontend-production /usr/share/nginx/html /usr/share/nginx/html
COPY --from=frontend-production /etc/nginx/nginx.conf  /etc/nginx/nginx.conf
COPY --from=frontend-production /etc/nginx/conf.d/ /etc/nginx/conf.d/
COPY --from=frontend-production /entrypoint.sh /entrypoint.sh
COPY --from=frontend-production /docker-entrypoint.d/ /docker-entrypoint.d/

# Kopiere die Supervisor-Konfiguration
COPY supervisord.conf /etc/supervisord.conf

RUN chmod +x /entrypoint.sh

EXPOSE 8080 80

ENTRYPOINT ["/entrypoint.sh"]

# Starten von Supervisor, um beide Dienste zu verwalten
CMD ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]