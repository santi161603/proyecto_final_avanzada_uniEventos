# Build stage - usa JDK 17 para evitar errores de class-version
FROM gradle:8.8.0-jdk17 AS build
WORKDIR /home/gradle/src

# Copia sólo lo necesario para aprovechar cache de capas
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

# Asegura que el wrapper sea ejecutable y usa el wrapper
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar --no-daemon

# Package stage
FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

EXPOSE ${PORT:-8080}
ENTRYPOINT ["java","-jar","/app.jar"]
