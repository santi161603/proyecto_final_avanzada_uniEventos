#
# Build stage
#
FROM gradle:latest AS build
WORKDIR /home/gradle/src

# Copia el wrapper y los archivos de build
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Copia el resto del código
COPY src src

# Asegura que el wrapper sea ejecutable
RUN chmod +x gradlew

# Compila la aplicación
RUN ./gradlew clean bootJar --no-daemon

#
# Package stage
#
FROM amazoncorretto:17
WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "/app.jar"]
