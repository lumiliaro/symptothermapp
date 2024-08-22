# Build stage ----------------------------------------------------------------------------------
# Verwende ein Node-Image für den Build
FROM node:20.2.0-slim as build-stage

# Setze das Arbeitsverzeichnis
WORKDIR /app

# Defaults to production, docker-compose overrides this to development on build and run.
ARG NODE_ENV=production
ENV NODE_ENV ${NODE_ENV}

# Kopiere package.json und package-lock.json und installiere die Abhängigkeiten
COPY package*.json ./
RUN npm ci && npm cache clean --force

# Kopiere den Rest der Anwendung
COPY . .

# Baue die Anwendung
RUN npm run build

# Production stage -----------------------------------------------------------------------------
# Verwende ein NGINX-Image, um die statischen Dateien zu servieren
FROM nginx:1.27.1-alpine as production-stage

# Definiere Umgebungsvariablen basierend auf den ARG-Werten
ARG VITE_BACKEND_URL=http://localhost:8088
ENV VITE_BACKEND_URL=${VITE_BACKEND_URL}


# Kopiere die gebauten Dateien nach NGINX
# COPY --from=build /app/dist /usr/share/nginx/html

# Exponiere den Standardport
ARG PORT=80
ENV NGINX_PORT=${PORT}
ENV NGINX_HOST=localhost

EXPOSE ${PORT}

COPY .docker/app/nginx/nginx.conf /etc/nginx/nginx.conf
COPY .docker/app/nginx/conf.d/ /etc/nginx/conf.d/
COPY .docker/app/entrypoint.sh /entrypoint.sh
COPY .docker/app/nginx/init-scripts/ /docker-entrypoint.d/

WORKDIR /usr/share/nginx/html

COPY --from=build-stage /app/dist ./

# When the container starts, env.sh will run and inject environment variables
# CMD ["/bin/sh", "-c", "/env.sh && nginx -g 'daemon off;'"]
