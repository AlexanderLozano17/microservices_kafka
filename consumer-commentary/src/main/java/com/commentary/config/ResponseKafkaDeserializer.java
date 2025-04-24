package com.commentary.config;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.commentary.domain.event.model.Commentary;
import com.commentary.domain.event.model.ResponseKafka;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Es para que Kafka pueda leer bytes de un topic y convertirlos a objetos.
 */
public class ResponseKafkaDeserializer implements Deserializer<ResponseKafka<Commentary>> {
	
    private final ObjectMapper objectMapper;

    public ResponseKafkaDeserializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No necesita configuración adicional
    }

    @Override
    public ResponseKafka<Commentary> deserialize(String topic, byte[] data) {
        try {
            if (data == null) return null;
            return objectMapper.readValue(data, new TypeReference<ResponseKafka<Commentary>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error deserializando el mensaje Kafka", e);
        }
    }

    @Override
    public void close() {
        // Nada que cerrar
    }
}

