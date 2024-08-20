# Frontend Dockerfile

# Verwende ein Node-Image für den Build
FROM node:18-alpine AS build

# Setze das Arbeitsverzeichnis
WORKDIR /app

# Kopiere package.json und package-lock.json und installiere die Abhängigkeiten
COPY package.json .
COPY package-lock.json .
RUN npm install

# Kopiere den Rest der Anwendung und baue sie
COPY . .
RUN npm run build

# Verwende ein NGINX-Image, um die statischen Dateien zu servieren
FROM nginx:1.27.1-alpine

# Kopiere die gebauten Dateien nach NGINX
COPY --from=build /app/dist /usr/share/nginx/html

# Exponiere den Standardport
EXPOSE 80

# Standardmäßiger Startbefehl für NGINX
CMD ["nginx", "-g", "daemon off;"]
