# Stage: backend
# Purpose: The final image
# Backend Build Stage
FROM gradle:8.10.0-jdk21 AS compile

WORKDIR /app
COPY ./src .

RUN gradle clean build -x test

# Stage: backend production
# Purpose: The final image
# Backend Build Stage
FROM openjdk:21-jdk-slim AS production

WORKDIR /app
COPY --from=compile /app/build/libs/*.jar app.jar

# Set up the volume for HSQLDB data
RUN mkdir -p /app/data/hsqldb
VOLUME ["/app/data/hsqldb"]
ENV DB_PATH=jdbc:hsqldb:file:/app/data/hsqldb/db
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080
CMD ["java","-jar","app.jar"]