package com.demo.main.config.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.core.entities.CommentaryEntity;
import com.demo.core.repositories.CommentaryRepository;
import com.demo.core.repositories.PersonRepository;
import com.demo.core.repositories.PublicationRepository;

@Component
public class CommentaryDataInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentaryDataInitializer.class);

	 private final CommentaryRepository repository;
	 private final PublicationRepository publicationRepository;
	 private final PersonRepository personRepository;
	 
	 public CommentaryDataInitializer(PublicationRepository publicationRepository, 
			 						  PersonRepository personRepository,
			 						  CommentaryRepository repository) {
		 this.repository = repository;
		 this.publicationRepository = publicationRepository;
		 this.personRepository = personRepository;
	 }
	 
	 @Transactional
	 public void commentaryRun() {
		 LOGGER.info("Iniciando verificación e inicialización de datos de personas ...");
		 
		 if (repository.count() == 0) {
	            LOGGER.info("No se encontraron personas. Inicializando con personas por defecto.");

	            try {
	                 
	            	List<CommentaryEntity> commentaries = getCommentaries();

	            	repository.saveAll(commentaries);
	                LOGGER.info("Datos de personas inicializados exitosamente. Se insertaron {} personas.", commentaries.size());

	            } catch (Exception e) {
	                LOGGER.error("Error al inicializar los datos de personas: {}", e.getMessage(), e);
	            }
	        } else {
	            LOGGER.info("Ya existen personas en la base de datos. No se requiere inicialización.");
	        }
	        LOGGER.info("Finalizada la verificación e inicialización de datos de personas.");
	 }
	 
	 private List<CommentaryEntity> getCommentaries() {
		 
		 
		 List<CommentaryEntity> comentarios = new ArrayList<>();

		// Combinación 1: comentarios cruzados entre publicaciones y usuarios
		comentarios.add(new CommentaryEntity(null,
		    publicationRepository.findByTitle("Microservicios con Spring Boot").orElseThrow(),
		    personRepository.findByEmail("maria.gonzalez@example.com").orElseThrow(),
		    LocalDate.now(), "Muy útil esta guía de microservicios."));

		comentarios.add(new CommentaryEntity(null,
		    publicationRepository.findByTitle("Guía de Docker para Devs").orElseThrow(),
		    personRepository.findByEmail("juan.perez@example.com").orElseThrow(),
		    LocalDate.now(), "Me encanta Docker, buen contenido."));

		comentarios.add(new CommentaryEntity(null,
		    publicationRepository.findByTitle("Java vs Kotlin").orElseThrow(),
		    personRepository.findByEmail("ana.torres@example.com").orElseThrow(),
		    LocalDate.now(), "Yo prefiero Kotlin para nuevos proyectos."));

		comentarios.add(new CommentaryEntity(null,
		    publicationRepository.findByTitle("REST vs GraphQL").orElseThrow(),
		    personRepository.findByEmail("luis.martinez@example.com").orElseThrow(),
		    LocalDate.now(), "Interesante comparación de APIs."));

		comentarios.add(new CommentaryEntity(null,
		    publicationRepository.findByTitle("Reactive Programming").orElseThrow(),
		    personRepository.findByEmail("laura.rios@example.com").orElseThrow(),
		    LocalDate.now(), "¡WebFlux me costó al principio pero ahora lo amo!"));

		// Combinación 2: múltiples usuarios comentando en una misma publicación
		comentarios.add(new CommentaryEntity(null,
		    publicationRepository.findByTitle("Microservicios con Spring Boot").orElseThrow(),
		    personRepository.findByEmail("carlos.ramirez@example.com").orElseThrow(),
		    LocalDate.now(), "Gracias por este artículo, muy claro."));

		comentarios.add(new CommentaryEntity(null,
		    publicationRepository.findByTitle("Microservicios con Spring Boot").orElseThrow(),
		    personRepository.findByEmail("andres.lozano@example.com").orElseThrow(),
		    LocalDate.now(), "¿Tienen ejemplos en GitHub?"));

		comentarios.add(new CommentaryEntity(null,
		    publicationRepository.findByTitle("Microservicios con Spring Boot").orElseThrow(),
		    personRepository.findByEmail("diana.morales@example.com").orElseThrow(),
		    LocalDate.now(), "Me gustaría una parte 2 más avanzada."));

		// Combinación 3: misma persona comenta en varias publicaciones
		comentarios.add(new CommentaryEntity(null,
		    publicationRepository.findByTitle("MongoDB en proyectos reales").orElseThrow(),
		    personRepository.findByEmail("paula.vargas@example.com").orElseThrow(),
		    LocalDate.now(), "Estoy usando MongoDB, esto me ayudó."));

		comentarios.add(new CommentaryEntity(null,
		    publicationRepository.findByTitle("Testing con JUnit 5").orElseThrow(),
		    personRepository.findByEmail("paula.vargas@example.com").orElseThrow(),
		    LocalDate.now(), "JUnit 5 está muy bien explicado."));

		comentarios.add(new CommentaryEntity(null,
		    publicationRepository.findByTitle("Arquitectura hexagonal").orElseThrow(),
		    personRepository.findByEmail("paula.vargas@example.com").orElseThrow(),
		    LocalDate.now(), "Arquitectura hexagonal es clave para escalar."));

		return comentarios;

	 }

}
