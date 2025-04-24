package com.publication.config;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.publication.domain.event.model.Publication;
import com.publication.domain.event.model.ResponseKafka;

/**
 * Es para que Kafka pueda leer bytes de un topic y convertirlos a objetos.
 */
public class ResponseKafkaDeserializer implements Deserializer<ResponseKafka<Publication>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No necesita configuración adicional
    }

    @Override
    public ResponseKafka<Publication> deserialize(String topic, byte[] data) {
        try {
            if (data == null) return null;
            return objectMapper.readValue(data, new TypeReference<ResponseKafka<Publication>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error deserializando el mensaje Kafka", e);
        }
    }

    @Override
    public void close() {
        // Nada que cerrar
    }
}

