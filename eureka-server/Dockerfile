# Etapa 1: Build - Compila el proyecto usando Maven con Java 17
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copia el contenido del proyecto al contenedor
COPY . .

# Ejecuta el empaquetado del JAR, omitiendo los tests
RUN mvn clean package -DskipTests

# Etapa 2: Runtime - Imagen liviana con JDK 17 para producción
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia el JAR compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto estándar del servidor Eureka
EXPOSE 8761

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
