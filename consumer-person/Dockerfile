# Etapa 1: Build (Compilación)
# Usa una imagen de Maven con Java 17 para compilar tu proyecto.
FROM maven:3.9.4-eclipse-temurin-17 AS build
# Establece el directorio de trabajo dentro del contenedor.
WORKDIR /app

# Copia todo el código fuente del microservicio al contenedor de compilación.
COPY . .

# Compila el proyecto Maven y empaqueta el JAR.
# '-DskipTests' omite la ejecución de los tests para acelerar la construcción de la imagen.
RUN mvn clean package -DskipTests

# Cambia a una imagen base más ligera (Alpine Linux con solo el JDK) para el entorno de ejecución.
# Esto resulta en un tamaño de imagen final mucho más pequeño y seguro.
FROM eclipse-temurin:17-jdk-alpine

# Instala las dependencias necesarias para los healthchecks de Docker Compose (curl y netcat).
RUN apk update && apk add --no-cache \
    curl \
    netcat-openbsd

# Establece el directorio de trabajo dentro del contenedor para la aplicación.
WORKDIR /app

# Copia el archivo JAR compilado desde la etapa 'build' al directorio de trabajo de la etapa 'runtime'.
# El JAR se renombra a 'app.jar' en esta ubicación.
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto por defecto en el que escuchará la aplicación Spring Boot.
EXPOSE 8082

# Define el comando predeterminado para iniciar la aplicación Spring Boot.
# Este ENTRYPOINT es el que se utilizaría si el contenedor se ejecuta directamente
# sin que Docker Compose sobrescriba la instrucción de inicio.
ENTRYPOINT ["java", "-jar", "app.jar"]
