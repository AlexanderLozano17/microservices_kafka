package com.publication.domain.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.publication.domain.event.PublicationEventListener;
import com.publication.domain.event.model.Publication;
import com.publication.domain.event.model.ResponseKafka;
import com.publication.util.FunctionUtils;
import com.publication.util.LogHelper;

@Service
public class PublicationCreationService implements PublicationEventListener {

	private final Logger logger = LoggerFactory.getLogger(PublicationCreationService.class);


	@Override
	public void onPublicationCreate(ResponseKafka<Publication> response) {
		logger.info(LogHelper.start(getClass(), "onPersonCreate"));
		
		 // Lógica del negocio
        System.out.println("📥 Publicación recibida desde Kafka: ");

        FunctionUtils.printJsonPretty(response);
        
        // Aquí puedes: guardar en DB, notificar otro servicio, etc.
		
        logger.info(LogHelper.end(getClass(), "onPersonCreate"));
	}

}
