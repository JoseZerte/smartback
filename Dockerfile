# ETAPA 1: Compilación (Build)
# Cambiamos a gradle:8.12 para que sea compatible con Spring Boot 4
FROM gradle:8.12-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon

# ETAPA 2: Ejecución (Runtime)
FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]