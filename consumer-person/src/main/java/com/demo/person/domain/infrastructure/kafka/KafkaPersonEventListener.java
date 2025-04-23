package com.demo.person.domain.infrastructure.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.demo.person.domain.event.PersonEventListener;
import com.demo.person.domain.event.model.Person;
import com.demo.person.domain.event.model.ResponseKafka;
import com.demo.person.util.Constants;
import com.demo.person.util.LogHelper;

@Component
public class KafkaPersonEventListener {
	
	private final Logger logger = LoggerFactory.getLogger(KafkaPersonEventListener.class);

	private final PersonEventListener personEventListener;

	
	public KafkaPersonEventListener(PersonEventListener personEventListener) {
		this.personEventListener = personEventListener;
	}
	
	@KafkaListener(
			topics = Constants.TOPIC_PERSONS, 
			groupId = Constants.GROUP_PERSON,
			containerFactory = "responseKafkaListenerContainerFactory") // para especificar qué fábrica de contenedores usar al consumir mensajes desde Kafka.
	public void consume(ResponseKafka<Person> event) {
		logger.info(LogHelper.start(getClass(), "consume"));
		logger.info("📥 Mensaje recibido: {}", event);
		
		personEventListener.onPersonCreate(event);
		
		logger.info(LogHelper.end(getClass(), "consume"));
	}
	
}
