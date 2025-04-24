package com.publication.domain.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.publication.domain.event.PublicationEventListener;
import com.publication.domain.event.model.Publication;
import com.publication.domain.event.model.ResponseKafka;
import com.publication.util.Constants;
import com.publication.util.LogHelper;

@Component
public class KafkaPublicationEventListener {

	private final Logger logger = LoggerFactory.getLogger(KafkaPublicationEventListener.class);

	private final PublicationEventListener publicationEventListener;

	
	public KafkaPublicationEventListener(PublicationEventListener publicationEventListener) {
		this.publicationEventListener = publicationEventListener;
	}
	
	@KafkaListener(
			topics = Constants.TOPIC_PUBLICATION, 
			groupId = Constants.GROUP_PUBLICATION,
			containerFactory = "responseKafkaListenerContainerFactory") // para especificar qué fábrica de contenedores usar al consumir mensajes desde Kafka.
	public void consume(ResponseKafka<Publication> event) {
		logger.info(LogHelper.start(getClass(), "consume"));
		logger.info("📥 Mensaje recibido: {}", event);
		
		publicationEventListener.onPublicationCreate(event);
		
		logger.info(LogHelper.end(getClass(), "consume"));
	}
	
}
