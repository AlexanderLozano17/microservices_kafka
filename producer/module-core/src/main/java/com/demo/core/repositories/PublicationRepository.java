package com.demo.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.core.entities.PublicationEntity;
import java.util.List;


public interface PublicationRepository extends JpaRepository<PublicationEntity, Long> {
	
	Optional<PublicationEntity> findByTitle(String title);
}
