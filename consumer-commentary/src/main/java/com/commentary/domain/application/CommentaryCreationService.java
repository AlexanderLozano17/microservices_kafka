package com.commentary.domain.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.commentary.domain.event.CommentaryEventListener;
import com.commentary.domain.event.model.Commentary;
import com.commentary.domain.event.model.ResponseKafka;
import com.commentary.util.FunctionUtils;
import com.commentary.util.LogHelper;

@Service
public class CommentaryCreationService implements CommentaryEventListener {
	
	private final Logger logger = LoggerFactory.getLogger(CommentaryCreationService.class);

	@Override
	public void onCommentaryCreate(ResponseKafka<Commentary> response) {
		logger.info(LogHelper.start(getClass(), "onCommentaryCreate"));
		
		 // Lógica del negocio
       System.out.println("📥 Comentario recibida desde Kafka: ");

       FunctionUtils.printJsonPretty(response);
       
       // Aquí puedes: guardar en DB, notificar otro servicio, etc.
		
       logger.info(LogHelper.end(getClass(), "onCommentaryCreate"));
		
	}

}
