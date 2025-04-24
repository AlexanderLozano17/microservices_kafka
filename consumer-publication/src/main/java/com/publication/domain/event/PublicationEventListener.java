package com.publication.domain.event;

import com.publication.domain.event.model.Publication;
import com.publication.domain.event.model.ResponseKafka;

public interface PublicationEventListener {

	void onPublicationCreate(ResponseKafka<Publication> response);
}
