# Backend Dockerfile

# Verwende ein Maven-Image zum Bauen der Anwendung
FROM gradle:8.10.0-jdk21 AS build

# Setze das Arbeitsverzeichnis
WORKDIR /app

# Kopiere die Gradle-Konfigurationsdateien und den Quellcode
COPY build.gradle settings.gradle ./
COPY src ./src

# Baue die Anwendung und erzeuge das JAR-File
RUN gradle clean build -x test

# Nutze ein schlankes OpenJDK-Image für den eigentlichen Laufzeitcontainer
FROM openjdk:21-jdk-slim

# Setze das Arbeitsverzeichnis
WORKDIR /app

# Kopiere das erzeugte JAR-File aus dem Build-Schritt
COPY --from=build /app/build/libs/*.jar app.jar

# Setze die Umgebungsvariable für den Speicherort der HSQLDB-Datenbankdateien
ENV SPRING_DATASOURCE_URL=jdbc:hsqldb:file:/app/db/hsqldb/db

# Exponiere den Standardport der Spring Boot Anwendung
EXPOSE 8080

# Starte die Spring Boot Anwendung
ENTRYPOINT ["java", "-jar", "app.jar"]
