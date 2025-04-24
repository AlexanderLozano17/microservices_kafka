# 📦 Proyecto Spring Boot + Apache Kafka (Docker Ready)

Este proyecto demuestra cómo integrar **Spring Boot con Apache Kafka**, permitiendo enviar, recibir y almacenar mensajes en formato **JSON** de manera eficiente. Además, utilizamos **Docker Compose** para simplificar la configuración y ejecución del entorno, y **Kafka UI** para visualizar y monitorear en tiempo real los tópicos, mensajes y el estado del **broker** Kafka.

El proyecto de **Spring Boot** está desarrollado de manera **modular**, lo que facilita la escalabilidad, mantenimiento y separación de responsabilidades.


## 🛠️ Tecnologías Utilizadas

| Tecnología          | Descripción                                               |
|---------------------|-----------------------------------------------------------|
| **Java 17**         | Lenguaje de programación principal                        |
| **Spring Boot**     | Framework para desarrollo rápido de aplicaciones Java     |
| **Spring Data JPA** | Abstracción para el acceso a datos con repositorios       |
| **Spring Kafka**    | Cliente Kafka para integración con Spring                 |
| **Apache Kafka**    | Plataforma distribuida de mensajería                      |
| **Kafka UI**        | Interfaz gráfica para gestionar Kafka                     |
| **Zookeeper**       | Coordinador de servicios para Kafka                       |
| **OpenAPI** 		  | Documentación interactiva de la API REST 				  |
| **Docker**          | Contenedorización de la aplicación                        |
| **Docker Compose**  | Orquestación de contenedores                              |
| **Jackson**         | Serialización y deserialización JSON                      |
| **Maven**           | Gestión del ciclo de vida del proyecto Java               |


## 💡 Buenas prácticas y patrones aplicados

- ✅ Uso de **DTO (Data Transfer Object)** para desacoplar entidades del modelo de datos
- ✅ Implementación de **JPQL (Java Persistence Query Language)** para consultas personalizadas
- ✅ Arquitectura por capas: `Controller → Service → Repository`
- ✅ Manejo de errores y trazabilidad con **logs estructurados (SLF4J + LogHelper)**
- ✅ Anotaciones clave como `@Transactional`, `@Service`, `@Repository`, `@Slf4j`
- ✅ Validación de entradas con `@Valid` y uso de clases request/response específicas
- ✅ Uso de excepciones personalizadas y manejo centralizado de errores

---

## **📌 Arquitectura del Proyecto**

```
📁 microservices
├── 📁 producer                 # Proyecto multi-módulo (parent POM adentro)
│   ├── 📁 main-app             # Punto de entrada Spring Boot
│   ├── 📁 module-core          # Lógica de negocio principal
│   ├── 📁 module-producer      # Productor de mensajes Kafka
│   ├── 📁 module-dto           # Data Transfer Objects (DTO)
│   ├── 📁 module-common        # Utilitarios, constantes, logs, etc.
│   ├── 📄 pom.xml              # POM raíz del proyecto producer
│
├── 📁 consumer-person          # Microservicio consumidor de mensajes de persona
├── 📁 consumer-publication     # Microservicio consumidor de publicaciones
├── 📁 consumer-commentary      # Microservicio consumidor de comentarios
│
├── 📄 docker-compose.yml       # Servicios Kafka, Zookeeper y Kafka UI
└── 📄 README.md                # Documentación general del sistema


```
Este diseño modular permite mayor **reutilización** y **mantenibilidad**.

---

## **📝 Logging**
Se utiliza **SLF4J** junto con un helper personalizado (**LogHelper**) para estructurar los logs.
Cada acción del sistema (inicio, éxito, error, fin) es registrada, facilitando la trazabilidad y el análisis de errores.

---

## **📌 Pasos para levantar los servicios**

### 🔥 **Iniciar los contenedores**
Ejecuta el siguiente comando en la terminal:

```sh
docker-compose up -d --build
```

📌 *Construye las imagenes Docker necesarias según los Dockerfile, luego iniciará los servicios en segundo plano.*

### 🖥️ **Verificar que los contenedores están corriendo**
Ejecuta:

```sh
docker ps
```

Deberías ver los contenedores `zookeeper`, `kafka1`, `kafka2`, `kafka-ui`, `postgresKafka` y `producerKafka` en ejecución.

### 🌍 **Acceder a la interfaz web de Kafka**
Abre un navegador y entra a:

```
http://localhost:8080
```

Aquí podrás visualizar los topics y administrar Kafka gráficamente.

### ✅ **Probar la conexión con Kafka**
Para listar los topics en Kafka, usa:

```sh
docker exec -it kafka1 kafka-topics.sh --bootstrap-server kafka1:9092 --list
```

### ✅ **Crear los siguientes Topics**
Para crear los topics en Kafka, usa:

```sh
docker exec -it kafka1 kafka-topics.sh --create --topic topic-persons --bootstrap-server kafka1:9092 --partition 3 --replication-factor 1
docker exec -it kafka1 kafka-topics.sh --create --topic topic-publications --bootstrap-server kafka1:9092 --partition 3 --replication-factor 1
docker exec -it kafka1 kafka-topics.sh --create --topic topic-commentaries --bootstrap-server kafka1:9092 --partition 3 --replication-factor 1
```
### ✅ **URL Documentación interactiva de la API REST **

```
http://localhost:8081/swagger-ui/index.html
```