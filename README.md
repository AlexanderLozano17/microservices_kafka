# 📦 Proyecto Spring Boot + Apache Kafka (Docker Ready)

Este proyecto demuestra cómo integrar ** Spring Boot**  con ** Apache Kafka** , permitiendo enviar, recibir y almacenar mensajes en formato **JSON**  de manera eficiente. Además, utilizamos **Docker Compose**  para simplificar la configuración y ejecución del entorno, y **Kafka UI** para visualizar y monitorear en tiempo real los tópicos, mensajes y el estado del broker Kafka.

El proyecto de Spring Boot está desarrollado de manera modular, lo que facilita la escalabilidad, mantenimiento y separación de responsabilidades.

Adicionalmente, se incorpora un enfoque de arquitectura de microservicios utilizando Spring Cloud Netflix Eureka como servidor de descubrimiento para registrar y localizar automáticamente los distintos servicios del sistema. También se incluye un **API Gateway** basado en **Spring Cloud Gateway**, que actúa como punto de entrada único, gestionando el enrutamiento, autenticación y filtrado de solicitudes hacia los microservicios registrados en **Eureka**.


## 🛠️ Tecnologías Utilizadas

| Tecnología          			 | Descripción                                                   |
|--------------------------------|---------------------------------------------------------------|
| **Java 17**         			 | Lenguaje de programación principal                            |
| **Spring Boot**     			 | Framework para desarrollo rápido de aplicaciones Java         |
| **Spring Cloud Netflix Eureka**| Registro y descubrimiento de servicios                        |
| **Spring Cloud Gateway **      | punto de entrada centralizado para todas las solicitudes HTTP |
| **Spring Data JPA** 			 | Abstracción para el acceso a datos con repositorios           |
| **Spring Kafka**    			 | Cliente Kafka para integración con Spring                     |
| **Apache Kafka**    			 | Plataforma distribuida de mensajería                          |
| **Kafka UI**        			 | Interfaz gráfica para gestionar Kafka                         |
| **Zookeeper**       			 | Coordinador de servicios para Kafka                           |
| **OpenAPI** 		  			 | Documentación interactiva de la API REST 				     |
| **Docker**          			 | Contenedorización de la aplicación                            |
| **Docker Compose**  			 | Orquestación de contenedores                                  |
| **Jackson**         			 | Serialización y deserialización JSON                          |
| **Maven**           			 | Gestión del ciclo de vida del proyecto Java                   |


## 💡 Buenas prácticas y patrones aplicados

- ✅ Uso de **DTO (Data Transfer Object)** para desacoplar entidades del modelo de datos
- ✅ Implementación de **JPQL (Java Persistence Query Language)** para consultas personalizadas
- ✅ Arquitectura por capas: `Controller → Service → Repository`
- ✅ Manejo de errores y trazabilidad con **logs estructurados (SLF4J + LogHelper)**
- ✅ Anotaciones clave como `@Transactional`, `@Service`, `@Repository`, `@Slf4j`
- ✅ Validación de entradas con `@Valid` y uso de clases request/response específicas
- ✅ Uso de excepciones personalizadas y manejo centralizado de errores

---



## 📘 Arquitectura del Proyecto - Event-Driven Architecture

Puedes ver la arquitectura de microservicios en el siguiente PDF:

👉 [Ver arquitectura C4 en PDF](docs/arquitectura.pdf)

## **📌 Estructura del Proyecto **

```
📁 microservices
├── 📁 producer                 # Microservicio multi-módulo (parent POM adentro)
│   ├── 📁 main-app             # Punto de entrada Spring Boot
│   ├── 📁 module-core          # Lógica de negocio principal
│   ├── 📁 module-producer      # Productor de mensajes Kafka
│   ├── 📁 module-dto           # Data Transfer Objects (DTO)
│   ├── 📁 module-common        # Utilitarios, constantes, logs, etc.
│   ├── 📄 pom.xml              # POM raíz del proyecto producer
├── 📁 api-gateway	           # Microservicio api gate-way
├── 📁 eureka-server	           # Registro y descubrimiento de servicios 
├── 📁 consumer-person          # Microservicio consumidor de mensajes de persona
├── 📁 consumer-publication     # Microservicio consumidor de publicaciones
├── 📁 consumer-commentary      # Microservicio consumidor de comentarios
│
├── 📄 docker-compose.yml       # Servicios Kafka, Zookeeper y Kafka UI
└── 📄 README.md                # Documentación general del sistema


```
Producer esta diseño modular permite mayor **reutilización** y **mantenibilidad**.

---

## **📝 Logging**
Se utiliza **SLF4J** junto con un helper personalizado (**LogHelper**) para estructurar los logs.
Cada acción del sistema (inicio, éxito, error, fin) es registrada, facilitando la trazabilidad y el análisis de errores.

---


## 🚀 Pasos para ejecutar el proyecto

 **Paso 1: Clonar el repositorio:**

   ```bash
   git clone https://github.com/AlexanderLozano17/microservices_kafka.git
   ```

 **Paso 2: Renombrar archivos `.yml`:**  
 
	Reemplaza los nombres de los archivos de configuración eliminando la palabra `template` del nombre.  
	Por ejemplo:
	
	
	application-template.yml → application.yml
	docker-compose-template.yml → docker-compose.yml
	

 **Paso 3: Verificar que Docker esté instalado:**  

   Asegúrate de tener Docker y Docker Compose funcionando en tu máquina.
   
   ```bash
   docker --version
   ```

 **Paso 4: Levantar los contenedores:**  

   Desde la raíz del proyecto, ejecuta:
   
   ```bash
   docker-compose up -d --build
   ```

 **Paso 5: erificar que los contenedores estén corriendo:**

   ```bash
   docker ps
   ```

 **Paso 6: Acceder a Kafka UI:**  

   Visualiza y administra los topics desde:  
   
   ```bash
   👉 http://localhost:8087
   ```
   
### **Crear los topics (si no se crean automáticamente):**

   ```bash
   docker exec -it kafka1 kafka-topics.sh --create --topic topic-persons --bootstrap-server kafka1:9092 --partition 3 --replication-factor 1
   docker exec -it kafka1 kafka-topics.sh --create --topic topic-publications --bootstrap-server kafka1:9092 --partition 3 --replication-factor 1
   docker exec -it kafka1 kafka-topics.sh --create --topic topic-commentaries --bootstrap-server kafka1:9092 --partition 3 --replication-factor 1
   ```

### **Documentación de la API REST:**  
   Accede a Swagger UI para ver los endpoints disponibles:  
   
   ```bash
   👉 [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
   ```
  

---

✅ ¡Con esto ya puedes comenzar a trabajar con la arquitectura distribuida basada en Kafka!
