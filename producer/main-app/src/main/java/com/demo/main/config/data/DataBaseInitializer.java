package com.demo.main.config.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInitializer implements CommandLineRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseInitializer.class);

	private final PersonDataInitializer personDataInitializer;
	private final CommentaryDataInitializer commentaryDataInitializer;
	private final PublicationDataInitializer publicationDataInitializer;

	public DataBaseInitializer(PersonDataInitializer personDataInitializer,
							   CommentaryDataInitializer commentaryDataInitializer,
							   PublicationDataInitializer publicationDataInitializer) {
		this.personDataInitializer = personDataInitializer;
		this.commentaryDataInitializer = commentaryDataInitializer;
		this.publicationDataInitializer = publicationDataInitializer;
	}
	
	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("init" + DataBaseInitializer.class.getName() + " | Insertando datos de pruebas");
		// El orden es CRÍTICO debido a las dependencias de clave foránea.
        // Primero entidades independientes, luego las que tienen dependencias.
        personDataInitializer.personRun();
        publicationDataInitializer.publicationRun();
        commentaryDataInitializer.commentaryRun();      
        LOGGER.info(DataBaseInitializer.class.getName() + " | fin inserción datos de pruebas");
		
	}

}
