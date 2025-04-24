package com.commentary.domain.event;

import com.commentary.domain.event.model.Commentary;
import com.commentary.domain.event.model.ResponseKafka;

public interface CommentaryEventListener {

	void onCommentaryCreate(ResponseKafka<Commentary> response);
}
