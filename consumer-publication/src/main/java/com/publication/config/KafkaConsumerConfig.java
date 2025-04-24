package com.publication.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.publication.domain.event.model.Publication;
import com.publication.domain.event.model.ResponseKafka;
import com.publication.util.Constants;


@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Bean
	public ConsumerFactory<String, ResponseKafka<Publication>> responseKafkaConsumerFactory() {
		
	    JsonDeserializer<ResponseKafka<Publication>> deserializer = new JsonDeserializer<>(new TypeReference<ResponseKafka<Publication>>() {});
	    deserializer.addTrustedPackages("*");
		
	    Map<String, Object> props = new HashMap<>();
	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BOOTSTRAP_SERVERS_CONFIG); // Dirección del broker de Kafka
	    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Inicia el consumo desde el principio del topic si no hay offset anterior
	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ResponseKafkaDeserializer.class); // Manejador de la deserialización

	    return new DefaultKafkaConsumerFactory<>(props);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ResponseKafka<Publication>> responseKafkaListenerContainerFactory() {
	    ConcurrentKafkaListenerContainerFactory<String, ResponseKafka<Publication>> factory = new ConcurrentKafkaListenerContainerFactory<>();
	    factory.setConsumerFactory(responseKafkaConsumerFactory());

	    // Opcional: manejar errores en consumo
	    factory.setCommonErrorHandler(new DefaultErrorHandler((record, exception) -> {
	        // Aquí puedes hacer logging, guardar en BD, o cualquier lógica de fallback
	        System.err.println("Error deserializando: " + exception.getMessage());
	    }, new FixedBackOff(0L, 0))); // No reintentos
	    return factory;
	}
}
