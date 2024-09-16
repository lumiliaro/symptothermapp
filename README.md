# SymptothermApp

SymptothermApp ist eine Webanwendung zur Unterstützung von Frauen bei der Überwachung ihrer Fruchtbarkeit mittels der symptothermalen Methode (NFP).
Die Anwendung besteht aus einem Frontend und einem Backend, die beide in einem Docker-Container ausgeführt werden können.

## Inhaltsverzeichnis

-   [Funktionen](#funktionen)
-   [Technologien](#technologien)
-   [Projektstruktur](#projektstruktur)
-   [Voraussetzungen](#voraussetzungen)
-   [Installation](#installation)
-   [Verwendung](#verwendung)
-   [Docker-Setup](#docker-setup)
-   [Entwicklung](#entwicklung)
-   [Lizenz](#lizenz)

## Funktionen

-   Überwachung der Fruchtbarkeit mittels symptothermaler Methode
-   Webbasierte Benutzeroberfläche für einfache Dateneingabe und -verwaltung
-   Backend für sichere Datenverarbeitung und -speicherung
-   Zyklusdiagramm mit Recharts
-   Benutzerauthentifizierung und -verwaltung
-   **Benutzeroberfläche auf Deutsch**

## Technologien

-   **Frontend**: TypeScript, React, Redux Toolkit, Mantine, Recharts
-   **Backend**: Java, Spring Boot
-   **Datenbank**: PostgreSQL (Containerisiert)
-   **Containerisierung**: Docker
-   **Build-Tool**: Gradle

## Projektstruktur

-   `src-ui/`: Frontend-Quellcode
-   `src/`: Backend-Quellcode
-   `src/main/resources/db/`: Datenbankdateien
-   `Dockerfile`: Docker-Konfiguration
-   `src/build.gradle`: Gradle-Build-Konfiguration

## Voraussetzungen

-   [Docker](https://www.docker.com/) (Version 20.10 oder höher)
-   Optional: [Docker Compose](https://docs.docker.com/compose/) (Version 1.29 oder höher)
-   Für die Entwicklung: Node.js, Java JDK 11+, Gradle

## Installation

1. **Repository klonen:**

    ```bash
    git clone https://github.com/lumiliaro/symptothermapp.git
    cd symptothermapp
    ```

2. **Docker-Image bauen und ausführen:**

    ```bash
    docker build -t symptothermapp .
    docker run -p 8080:8080 symptothermapp
    ```

3. **Zugriff auf die Anwendung:**

    - Frontend und Backend: [http://localhost:8080](http://localhost:8080)

## Verwendung

Nach dem Start des Containers kann auf die Anwendung über einen Webbrowser zugegriffen werden. Beginnen Sie mit der Überwachung Ihrer Fruchtbarkeit durch Eingabe Ihrer täglichen Messwerte und Beobachtungen.

## Docker-Setup

Das Projekt verwendet Docker für eine einfache Bereitstellung und Ausführung. Die `Dockerfile` im Wurzelverzeichnis definiert den Build- und Ausführungsprozess.

### Dockerfile-Struktur

Die `Dockerfile` verwendet einen Multi-Stage-Build-Prozess:

1. **Build-Stage**:

    - Basis: `gradle:8.10.0-jdk21-alpine`
    - Kopiert den Quellcode und baut die Anwendung mit Gradle
    - Führt `gradle clean build -x test --no-daemon` aus

2. **Production-Stage**:
    - Basis: `eclipse-temurin:21-jre-alpine`
    - Kopiert die gebaute JAR-Datei aus der Build-Stage
    - Setzt das Spring-Profil auf `prod`
    - Erstellt einen nicht-Root-Benutzer für verbesserte Sicherheit

### Sicherheitsaspekte

-   Verwendung eines nicht-Root-Benutzers (`appuser`) für die Ausführung der Anwendung
-   Nutzung des Alpine-Linux-Basisimages für eine minimale Angriffsfläche

### Ports

Der Container exponiert Port 8080 für den Zugriff auf die Anwendung.

### Umgebungsvariablen

-   `SPRING_PROFILES_ACTIVE=prod`: Setzt das Spring-Profil auf Produktion

### Docker-Befehle

1. **Image bauen:**

    ```bash
    docker build -t symptothermapp .
    ```

2. **Container starten:**
    ```bash
    docker run -p 8080:8080 symptothermapp
    ```

## Entwicklung

Für die lokale Entwicklung:

1. Datenbank (PostgreSQL) starten:

    ```bash
    cd ./src && docker compose up -d
    ```

2. Backend starten:

    ```bash
    ./gradlew bootRun --args='--spring.profiles.active=dev'
    ```

3. Frontend starten:
    ```bash
    cd src-ui
    npm install
    npm run dev
    ```

## Lizenz

Dieses Projekt ist unter der [MIT-Lizenz](LICENSE) lizenziert.
