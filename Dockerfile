# Build stage
FROM gradle:8.8.0-jdk17 AS build
WORKDIR /home/gradle/src

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar --no-daemon

# Package stage
FROM amazoncorretto:17
WORKDIR /app

# Copiamos el jar generado y le damos nombre fijo
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

# Aseguramos permisos legibles
RUN chmod 644 /app/app.jar

EXPOSE ${PORT:-8080}
ENTRYPOINT ["java","-jar","/app/app.jar"]
