# SymptothermApp

SymptothermApp is a web application that helps women monitor their fertility using the symptothermal method. The application consists of a frontend and backend, both of which can be run in a Docker container.

## Contents

-   [Features](#features)
-   [Technologies](#technologies)
-   [Requirements](#requirements)
-   [Installation](#installation)
-   [Environment Variables](#environment-variables)
-   [Usage](#usage)
-   [Docker Setup](#docker-setup)
-   [License](#license)

## Features

-   Monitor fertility using the symptothermal method
-   Web-based interface for easy data entry and management
-   Backend for secure data processing and storage
-   **Frontend is in German**

## Technologies

-   **Frontend**: TypeScript, React, Redux Toolkit, Mantine
-   **Backend**: Java, Spring Boot
-   **Database**: HSQLDB (embedded)
-   **Containerization**: Docker

## Requirements

-   [Docker](https://www.docker.com/) (version 20.10 or higher)
-   Optional: [Docker Compose](https://docs.docker.com/compose/) (version 1.29 or higher)

## Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/lumiliaro/symptothermapp.git
    cd symptothermapp
    ```

2. **Build and run the Docker image:**

    ```bash
    docker build -t symptothermapp .
    docker run -p 80:80 -p 8080:8080 symptothermapp
    ```

3. **Access the application:**

    - Frontend: [http://localhost:80](http://localhost:80)
    - Backend: [http://localhost:8080](http://localhost:8080)

## Environment Variables

To properly configure the Docker container, the following environment variables should be set:

-   `VITE_BACKEND_URL`: URL for the backend server (default: `http://localhost:8080`)
-   `FRONTEND_URL`: URL for the web interface (default: `http://localhost:80`)

These variables can either be set directly in the Dockerfile or passed when starting the container.

Example:

```bash
docker run -e VITE_BACKEND_URL=http://your-backend-url:8080 -e FRONTEND_URL=http://your-frontend-url:80 -p 80:80 -p 8080:8080 symptothermapp
```

## Usage

Once the container is running, the application can be accessed through a web browser. Sign in or register to begin monitoring your fertility.

## Docker Setup

### Multi-Stage Build

The Docker setup uses a multi-stage build to minimize the size of the final image:

Frontend Build: Builds the frontend application using Node.js.
Backend Build: Builds the backend application using Gradle.
Production Image: Combines the frontend and backend into a final image and runs both using Supervisor.

### Ports

The container exposes two ports:

-   8080: The backend
-   80: The frontend

### Volumes

The HSQLDB database is stored in a volume to persist the data:

```bash
VOLUME ["/app/db/hsqldb"]
```

## License

This project is licensed under the [MIT License](LICENSE).

## Temp

docker build -t 12ukas/symptothermapp:latest . --push
