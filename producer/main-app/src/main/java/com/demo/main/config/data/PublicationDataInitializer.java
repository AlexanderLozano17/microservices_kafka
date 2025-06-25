package com.demo.main.config.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.core.entities.PersonEntity;
import com.demo.core.entities.PublicationEntity;
import com.demo.core.repositories.PersonRepository;
import com.demo.core.repositories.PublicationRepository;

@Component
public class PublicationDataInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(PublicationDataInitializer.class);

	 private final PublicationRepository repository;
	 private final PersonRepository personRepository;
	 
	 public PublicationDataInitializer(PublicationRepository repository, PersonRepository personRepository) {
		 this.repository = repository;
		 this.personRepository = personRepository;
	 }
	 
	 @Transactional
	 public void publicationRun() {
		 LOGGER.info("Iniciando verificación e inicialización de datos de Publicación ...");
		 
		 if (repository.count() == 0) {
	            LOGGER.info("No se encontraron personas. Inicializando con Publicación por defecto.");

	            try {
	                 
	            	List<PublicationEntity> people = getPublication();

	            	repository.saveAll(people);
	                LOGGER.info("Datos de Publicación inicializados exitosamente. Se insertaron {} personas.", people.size());

	            } catch (Exception e) {
	                LOGGER.error("Error al inicializar los datos de Publicación: {}", e.getMessage(), e);
	            }
	        } else {
	            LOGGER.info("Ya existen Publicación en la base de datos. No se requiere inicialización.");
	        }
	        LOGGER.info("Finalizada la verificación e inicialización de datos de Publicación.");
	 }
	 
	 private List<PublicationEntity> getPublication() {
		 
		 List<String> emails = new ArrayList<>();
		 emails.add("carlos.ramirez@example.com");
		 emails.add("maria.gonzalez@example.com");
		 emails.add("juan.perez@example.com");
		 emails.add("ana.torres@example.com");
		 emails.add("luis.martinez@example.com");
		 emails.add("laura.rios@example.com");
		 emails.add("andres.lozano@example.com");
		 emails.add("diana.morales@example.com");
		 emails.add("miguel.salazar@example.com");
		 emails.add("paula.vargas@example.com");
		 
		 List<PersonEntity> idPeople =  emails.stream()
				 .map(email -> personRepository.findByEmail(email)
						 .orElseThrow(() -> new RuntimeException("La persona con email '" + email + "' no se encontró en la base de datos.")))
				 .collect(Collectors.toList());		 
		 		 
		 return List.of(
			    new PublicationEntity(null, idPeople.get(0), "Microservicios con Spring Boot", "Una guía para crear microservicios modernos.", LocalDate.of(2024, 6, 1), new ArrayList<>()),
			    new PublicationEntity(null, idPeople.get(1), "Guía de Docker para Devs", "Todo lo que necesitas para contenerizar tu app.", LocalDate.of(2024, 6, 2), new ArrayList<>()),
			    new PublicationEntity(null, idPeople.get(2), "Java vs Kotlin", "Comparativa completa entre ambos lenguajes en backend.", LocalDate.of(2024, 6, 3), new ArrayList<>()),
			    new PublicationEntity(null, idPeople.get(3), "REST vs GraphQL", "¿Cuál conviene usar según tu caso?", LocalDate.of(2024, 6, 4), new ArrayList<>()),
			    new PublicationEntity(null, idPeople.get(4), "Reactive Programming", "Ventajas y desafíos del paradigma reactivo.", LocalDate.of(2024, 6, 5), new ArrayList<>()),
			    new PublicationEntity(null, idPeople.get(5), "MongoDB en proyectos reales", "Cómo integrar MongoDB en tu microservicio.", LocalDate.of(2024, 6, 6), new ArrayList<>()),
			    new PublicationEntity(null, idPeople.get(6), "Testing con JUnit 5", "Buenas prácticas para pruebas automatizadas.", LocalDate.of(2024, 6, 7), new ArrayList<>()),
			    new PublicationEntity(null, idPeople.get(7), "Spring Security + JWT", "Implementación completa de seguridad.", LocalDate.of(2024, 6, 8), new ArrayList<>()),
			    new PublicationEntity(null, idPeople.get(8), "Integración continua con GitHub Actions", "CI/CD simplificado en GitHub.", LocalDate.of(2024, 6, 9), new ArrayList<>()),
			    new PublicationEntity(null, idPeople.get(9), "Arquitectura hexagonal", "Separación de capas en proyectos escalables.", LocalDate.of(2024, 6, 10), new ArrayList<>())
			); 	 
	}

}
