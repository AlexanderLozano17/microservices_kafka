package com.demo.core.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.core.entities.PersonEntity;
import com.demo.core.repositories.PersonRepository;
import com.demo.core.services.PersonService;
import com.demo.dto.dto.PersonDTO;
import com.demo.dto.dto.PersonWithPublicationsDTO;
import com.demo.dto.dto.PublicationDTO;
import com.demo.dto.dto.ResponseKafka;
import com.demo.producer.services.KafkaProducerPersonService;
import com.demo.utils.LogHelper;
import com.demo.utils.LogPerson;

@Service
public class PersonServiceImpl implements PersonService {
	
	private final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

	private final PersonRepository personRepository;
	
	private final KafkaProducerPersonService producerService;
		
	public PersonServiceImpl(PersonRepository personRepository, KafkaProducerPersonService producerService) {
		this.personRepository = personRepository;
		this.producerService = producerService;
	}
	
	@Override
	@Transactional
	public Optional<PersonDTO> createPerson(PersonEntity person) {
		logger.info(LogHelper.start(getClass(), "createPerson"));
		try {
			PersonEntity savePerson = personRepository.save(person);	
			String message = String.format(LogPerson.PERSON_SAVE_SUCCESS, savePerson.getId());
			logger.info(LogHelper.success(getClass(), "createPerson", message));
			
			PersonDTO personDTO = personDTO(savePerson);
			// Envía el mensaje a Kafka y obtén el CompletableFuture
	        CompletableFuture<SendResult<String, Object>> kafkaFuture = producerService.sendMessageRecordPerson(new ResponseKafka(message, personDTO)); 		 
			 
	        // Bloquea y espera el resultado del envío a Kafka.
	        // Si quieres que el envío a Kafka sea parte de la transacción DB, DEBES ESPERAR SU RESULTADO.
	        // Si el envío falla, lanzamos una excepción para que la transacción de la base de datos se revierta.
	        kafkaFuture.join(); // Esto bloquea y espera el resultado. Si hay una excepción, la relanza.
	        
	        // Si llegamos aquí, el envío a Kafka fue exitoso, entonces la transacción DB hace commit.
	        return Optional.of(personDTO);
			
		} catch (Exception e) {
			logger.error(LogHelper.error(getClass(), "createPerson", String.format(LogPerson.PERSON_SAVE_ERROR, e.getMessage())), e);
			 // *** CRÍTICO para la transacción: relanzar la excepción si quieres rollback ***
	        // Si la excepción no es relanzada, @Transactional no la verá y hará commit.
	        // Puedes relanzar e como una RuntimeException o una excepción específica de negocio.
	        throw new RuntimeException(LogHelper.error(getClass(), "createPerson", e.getMessage())); 
	        // return Optional.empty(); // Esto evita el rollback. Solo úsalo si no quieres que la DB haga rollback.
	 
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<PersonDTO> getPersonById(Long id) {
		logger.info(LogHelper.start(getClass(), "getPersonById"));		
				
		Optional<PersonEntity> person = personRepository.findById(id);
		
		if (person.isPresent()) {
			PersonDTO personDTO = personDTO(person.get());
			logger.info(LogHelper.success(getClass(), "getPersonById", String.format(LogPerson.PERSON_FOUND, id)));
			return Optional.of(personDTO);
			
		} else {
			logger.warn(LogHelper.warn(getClass(), "getPersonById", String.format(LogPerson.PERSON_NOT_FOUND, id)));
		}
		logger.info(LogHelper.end(getClass(), "getPersonById"));
		return Optional.empty();
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PersonWithPublicationsDTO> getPersonWithPublications(Long id) {
		logger.info(LogHelper.start(getClass(), "getPersonWithPublications"));
		
		Optional<PersonEntity> personWithPublications = personRepository.findById(id);
		
		if (personWithPublications.isPresent()) {
			PersonWithPublicationsDTO personWithPublicationsDTO = personWithPublicationsDTO(personWithPublications.get());
			return Optional.of(personWithPublicationsDTO);
		}
		return Optional.empty();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PersonWithPublicationsDTO> getAllPeopleWithPublications() {
		logger.info(LogHelper.start(getClass(), "getAllPeopleWithPublications"));
		
		List<PersonEntity> listPerson = personRepository.findAll();		
		List<PersonWithPublicationsDTO> personWithPublicationsDTOs = new ArrayList<>();
		
		if (!listPerson.isEmpty()) {
			
			personWithPublicationsDTOs = listPerson.stream()
					.map(personWithPublications -> personWithPublicationsDTO(personWithPublications))
					.collect(Collectors.toList());				
			
			logger.info(LogHelper.success(getClass(), "getAllPeopleWithPublications", String.format(LogPerson.PERSON_LIST_SUCCESS, personWithPublicationsDTOs.size())));
		} else {
			logger.warn(LogHelper.warn(getClass(), "getAllPeopleWithPublications", LogPerson.PERSON_NOT_CONTENT));
		}
		return personWithPublicationsDTOs;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PersonDTO> getAllPersons() {
		logger.info(LogHelper.start(getClass(), "getAllPersons"));
		
		List<PersonEntity> listPerson = personRepository.findAll();
		List<PersonDTO> listPersonDTOs = new ArrayList<>();
		if (!listPerson.isEmpty()) {
			
			listPersonDTOs = getListPersonDTO(listPerson);
			logger.info(LogHelper.success(getClass(), "getAllPersons", String.format(LogPerson.PERSON_LIST_SUCCESS, listPerson.size())));
			
		} else {
			logger.warn(LogHelper.warn(getClass(), "getAllPersons", LogPerson.PERSON_NOT_CONTENT));
		}
		logger.info(LogHelper.end(getClass(), "getAllPersons"));	
		return listPersonDTOs;
	}
	
	@Override
	@Transactional
	public boolean deletePersonById(Long id) {
		logger.info(LogHelper.start(getClass(), "deletePersonById"));
		
		if (!personRepository.existsById(id)) {
			logger.warn(LogHelper.warn(getClass(), "deletePersonById", String.format(LogPerson.PERSON_NOT_FOUND, id)));
			return false;		
		}

		personRepository.deleteById(id);
		logger.info(LogHelper.success(getClass(), "deletePersonById", String.format(LogPerson.PERSON_DELETE_SUCCESS, id)));
		return true;			
	}
	
	/**
	 * 
	 * @param persona
	 * @return
	 */
	private PersonDTO personDTO(PersonEntity person) {
		logger.info(LogHelper.start(getClass(), "personDTO"));
		if (person == null) return null;
		return new PersonDTO(person.getId(),
				person.getNames(), 
				person.getLastNames(),
				person.getAge(), 
				person.getEmail(), 
				person.getTelephone()); 
	}
	
	/**
	 * 
	 * @param listPerson
	 * @return
	 */
	private List<PersonDTO> getListPersonDTO(List<PersonEntity> listPerson) {
		logger.info(LogHelper.start(getClass(), "getPersonDTO"));
		if (listPerson.size() == 0) return new ArrayList<PersonDTO>(); 
		return listPerson.stream().map(person -> personDTO(person)).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param person
	 * @return
	 */
	private PersonWithPublicationsDTO personWithPublicationsDTO(PersonEntity person) {
		logger.info(LogHelper.start(getClass(), "PersonWithPublicationsDTO"));
		
		if (person == null) return null;
		
		List<PublicationDTO> listPublicationsDTO = person.getPublications().stream()
				.map(publication -> new PublicationDTO(publication.getId(),
						publication.getTitle(), 
						publication.getContent(), 
						publication.getDatePublication())).collect(Collectors.toList());
		
		PersonWithPublicationsDTO personWithPublicationsDTO = new PersonWithPublicationsDTO(person.getId(), 
				person.getNames(), 
				person.getLastNames(),
				person.getAge(), 
				person.getEmail(), 
				person.getTelephone(),
				listPublicationsDTO);
		
		return personWithPublicationsDTO;
	}
}
