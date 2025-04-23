package com.demo.person.config;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.demo.person.domain.event.model.Person;
import com.demo.person.domain.event.model.ResponseKafka;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Es para que Kafka pueda leer bytes de un topic y convertirlos a objetos.
 */
public class ResponseKafkaDeserializer implements Deserializer<ResponseKafka<Person>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No necesita configuración adicional
    }

    @Override
    public ResponseKafka<Person> deserialize(String topic, byte[] data) {
        try {
            if (data == null) return null;
            return objectMapper.readValue(data, new TypeReference<ResponseKafka<Person>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error deserializando el mensaje Kafka", e);
        }
    }

    @Override
    public void close() {
        // Nada que cerrar
    }
}
