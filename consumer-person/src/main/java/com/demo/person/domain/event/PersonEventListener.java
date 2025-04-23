package com.demo.person.domain.event;

import com.demo.person.domain.event.model.Person;
import com.demo.person.domain.event.model.ResponseKafka;

public interface PersonEventListener {

	void onPersonCreate(ResponseKafka<Person> response);
}
