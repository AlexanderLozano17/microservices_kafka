package com.demo.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.core.entities.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
	
	Optional<PersonEntity> findByEmail(String email);

}
