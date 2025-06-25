package com.demo.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.core.entities.CommentaryEntity;

public interface CommentaryRepository extends JpaRepository<CommentaryEntity, Long> {

}
