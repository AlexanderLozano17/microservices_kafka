package com.demo.producer.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.support.SendResult;

public interface KafkaProducerPersonService {

	CompletableFuture<SendResult<String, Object>>  sendMessageRecordPerson(Object message);
	
}
