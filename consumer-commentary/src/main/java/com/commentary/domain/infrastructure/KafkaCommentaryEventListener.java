package com.commentary.domain.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.commentary.domain.event.CommentaryEventListener;
import com.commentary.domain.event.model.Commentary;
import com.commentary.domain.event.model.ResponseKafka;
import com.commentary.util.Constants;
import com.commentary.util.LogHelper;

@Component
public class KafkaCommentaryEventListener {


	private final Logger logger = LoggerFactory.getLogger(KafkaCommentaryEventListener.class);

	private final CommentaryEventListener commentaryEventListener;

	
	public KafkaCommentaryEventListener(CommentaryEventListener commentaryEventListener) {
		this.commentaryEventListener = commentaryEventListener;
	}
	
	@KafkaListener(
			topics = Constants.TOPIC_COMMENTARY, 
			groupId = Constants.GROUP_COMMENTARY,
			containerFactory = "responseKafkaListenerContainerFactory") // para especificar qué fábrica de contenedores usar al consumir mensajes desde Kafka.
	public void consume(ResponseKafka<Commentary> event) {
		logger.info(LogHelper.start(getClass(), "consume"));
		logger.info("📥 Mensaje recibido: {}", event);
		
		commentaryEventListener.onCommentaryCreate(event);
		
		logger.info(LogHelper.end(getClass(), "consume"));
	}
	
}
