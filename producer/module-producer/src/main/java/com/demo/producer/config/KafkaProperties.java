package com.demo.producer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka")// Carga automáticamente todas las propiedades que empiezan con el prefijo spring.kafka.producer desde el archivo application.yml o application.properties.
public class KafkaProperties {
    
    private String bootstrapServers;
    private ProducerProperties producer = new ProducerProperties();
    
    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public ProducerProperties getProducer() {
        return producer;
    }

    public void setProducer(ProducerProperties producer) {
        this.producer = producer;
    }
    
    public static class ProducerProperties {
        private int retries;
        private int batchSize;
        private int bufferMemory;
        private int lingerMs;
        private String acks;
        private boolean enableIdempotence;
        private String keySerializer;
        private String valueSerializer;
        
        private ProducerSpecificProperties properties = new ProducerSpecificProperties();


        public int getRetries() {
            return retries;
        }

        public void setRetries(int retries) {
            this.retries = retries;
        }

        public int getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(int batchSize) {
            this.batchSize = batchSize;
        }

        public int getBufferMemory() {
            return bufferMemory;
        }

        public void setBufferMemory(int bufferMemory) {
            this.bufferMemory = bufferMemory;
        }

        public int getLingerMs() {
            return lingerMs;
        }

        public void setLingerMs(int lingerMs) {
            this.lingerMs = lingerMs;
        }

        public String getAcks() {
            return acks;
        }

        public void setAcks(String acks) {
            this.acks = acks;
        }

        public boolean isEnableIdempotence() {
            return enableIdempotence;
        }

        public void setEnableIdempotence(boolean enableIdempotence) {
            this.enableIdempotence = enableIdempotence;
        }

        public String getKeySerializer() {
            return keySerializer;
        }

        public void setKeySerializer(String keySerializer) {
            this.keySerializer = keySerializer;
        }

        public String getValueSerializer() {
            return valueSerializer;
        }

        public void setValueSerializer(String valueSerializer) {
            this.valueSerializer = valueSerializer;
        }
        
        public ProducerSpecificProperties getProperties() {
            return properties;
        }
        
        public void setProperties(ProducerSpecificProperties properties) {
            this.properties = properties;
        }
        
     // *** NUEVA CLASE INTERNA PARA LAS PROPIEDADES ESPECÍFICAS DEL JSON SERIALIZER ***
        public static class ProducerSpecificProperties {
            private SpringJsonProperties springJson = new SpringJsonProperties();

            public SpringJsonProperties getSpringJson() {
                return springJson;
            }

            public void setSpringJson(SpringJsonProperties springJson) {
                this.springJson = springJson;
            }

            public static class SpringJsonProperties {
                private boolean addTypeHeaders; // Mapea a 'add-type-headers'

                public boolean isAddTypeHeaders() { // isAddTypeHeaders para booleanos
                    return addTypeHeaders;
                }

                public void setAddTypeHeaders(boolean addTypeHeaders) {
                    this.addTypeHeaders = addTypeHeaders;
                }
            }
        }
    }
}
