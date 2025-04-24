# Etapa 1: Build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copia el código fuente del microservicio
COPY . .

# Compila el proyecto y empaqueta el JAR
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR del modulo principal al contenedor y lo renombra
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto de la app (puede ser fijo o usar variable de entorno)
EXPOSE 8084

# Comando de inicio para ejecutar la aplicacion Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
