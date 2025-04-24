package com.commentary.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import com.commentary.domain.event.model.Commentary;
import com.commentary.domain.event.model.ResponseKafka;
import com.fasterxml.jackson.core.type.TypeReference;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	
	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public ConsumerFactory<String, ResponseKafka<Commentary>> responseKafkaConsumerFactory() {
		
	    JsonDeserializer<ResponseKafka<Commentary>> deserializer = new JsonDeserializer<>(new TypeReference<ResponseKafka<Commentary>>() {});
	    deserializer.addTrustedPackages("*");
		
	    Map<String, Object> props = new HashMap<>();
	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); // Dirección del broker de Kafka
	    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Inicia el consumo desde el principio del topic si no hay offset anterior
	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ResponseKafkaDeserializer.class); // Manejador de la deserialización

	    return new DefaultKafkaConsumerFactory<>(props);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ResponseKafka<Commentary>> responseKafkaListenerContainerFactory() {
	    ConcurrentKafkaListenerContainerFactory<String, ResponseKafka<Commentary>> factory = new ConcurrentKafkaListenerContainerFactory<>();
	    factory.setConsumerFactory(responseKafkaConsumerFactory());

	    // Opcional: manejar errores en consumo
	    factory.setCommonErrorHandler(new DefaultErrorHandler((record, exception) -> {
	        // Aquí puedes hacer logging, guardar en BD, o cualquier lógica de fallback
	        System.err.println("Error deserializando: " + exception.getMessage());
	    }, new FixedBackOff(0L, 0))); // No reintentos
	    return factory;
	}
}
