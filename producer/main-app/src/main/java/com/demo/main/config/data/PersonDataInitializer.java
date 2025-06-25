package com.demo.main.config.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.core.entities.PersonEntity;
import com.demo.core.repositories.PersonRepository;

@Component
public class PersonDataInitializer {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(PersonDataInitializer.class);

	 private final PersonRepository repository;
	 
	 public PersonDataInitializer(PersonRepository repository) {
		 this.repository = repository;
	 }
	 
	 @Transactional
	 public void personRun() {
		 LOGGER.info("Iniciando verificación e inicialización de datos de personas ...");
		 
		 if (repository.count() == 0) {
	            LOGGER.info("No se encontraron personas. Inicializando con personas por defecto.");

	            try {
	                 
	            	List<PersonEntity> people = getPeople();

	            	repository.saveAll(people);
	                LOGGER.info("Datos de personas inicializados exitosamente. Se insertaron {} personas.", people.size());

	            } catch (Exception e) {
	                LOGGER.error("Error al inicializar los datos de personas: {}", e.getMessage(), e);
	            }
	        } else {
	            LOGGER.info("Ya existen personas en la base de datos. No se requiere inicialización.");
	        }
	        LOGGER.info("Finalizada la verificación e inicialización de datos de personas.");
	 }
	 
	 private List<PersonEntity> getPeople() {
		 return List.of(
		    new PersonEntity(null, "Carlos", "Ramírez", 30, "carlos.ramirez@example.com", "3001234567", new ArrayList<>(), new ArrayList<>()),
		    new PersonEntity(null, "María", "González", 25, "maria.gonzalez@example.com", "3109876543", new ArrayList<>(), new ArrayList<>()),
		    new PersonEntity(null, "Juan", "Pérez", 40, "juan.perez@example.com", "3112345678", new ArrayList<>(), new ArrayList<>()),
		    new PersonEntity(null, "Ana", "Torres", 33, "ana.torres@example.com", "3014567890", new ArrayList<>(), new ArrayList<>()),
		    new PersonEntity(null, "Luis", "Martínez", 28, "luis.martinez@example.com", "3203456789", new ArrayList<>(), new ArrayList<>()),
		    new PersonEntity(null, "Laura", "Ríos", 31, "laura.rios@example.com", "3123456790", new ArrayList<>(), new ArrayList<>()),
		    new PersonEntity(null, "Andrés", "Lozano", 36, "andres.lozano@example.com", "3009876543", new ArrayList<>(), new ArrayList<>()),
		    new PersonEntity(null, "Diana", "Morales", 27, "diana.morales@example.com", "3198765432", new ArrayList<>(), new ArrayList<>()),
		    new PersonEntity(null, "Miguel", "Salazar", 45, "miguel.salazar@example.com", "3023456789", new ArrayList<>(), new ArrayList<>()),
		    new PersonEntity(null, "Paula", "Vargas", 29, "paula.vargas@example.com", "3187654321", new ArrayList<>(), new ArrayList<>())
		);
	 }
	 
}
