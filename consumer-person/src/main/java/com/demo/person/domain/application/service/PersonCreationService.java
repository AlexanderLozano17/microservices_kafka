package com.demo.person.domain.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.person.domain.event.PersonEventListener;
import com.demo.person.domain.event.model.Person;
import com.demo.person.domain.event.model.ResponseKafka;
import com.demo.person.domain.infrastructure.kafka.KafkaPersonEventListener;
import com.demo.person.util.FunctionUtils;
import com.demo.person.util.LogHelper;

@Service
public class PersonCreationService implements PersonEventListener {
	
	private final Logger logger = LoggerFactory.getLogger(KafkaPersonEventListener.class);


	@Override
	public void onPersonCreate(ResponseKafka<Person> response) {
		logger.info(LogHelper.start(getClass(), "onPersonCreate"));
		
		 // Lógica del negocio
        System.out.println("📥 Persona recibida desde Kafka: ");

        FunctionUtils.printJsonPretty(response);
        
        // Aquí puedes: guardar en DB, notificar otro servicio, etc.
		
        logger.info(LogHelper.end(getClass(), "onPersonCreate"));
	}

}
