# ETAPA 1: Compilación (Build)
# Usamos exactamente Gradle 8.14 y JDK 17 como pide tu build.gradle
FROM gradle:8.14-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Compilamos saltando tests para evitar fallos de conexión a DB en el build
RUN gradle build -x test --no-daemon

# ETAPA 2: Ejecución (Runtime)
# Usamos el JRE 17 de Temurin (ligero y estable)
FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
# El asterisco ayuda a pillar el jar aunque el nombre cambie
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]