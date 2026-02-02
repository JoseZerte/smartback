# ETAPA 1: Compilación (Build)
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Compilamos el proyecto saltándonos los tests para que el despliegue sea rápido
RUN gradle build -x test --no-daemon

# ETAPA 2: Ejecución (Runtime)
FROM openjdk:17-jdk-slim
EXPOSE 8080
# Copiamos el .jar generado en la etapa anterior
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
# Ejecutamos la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]