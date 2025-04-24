package com.publication.domain.event.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Publication implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private String content;
	private LocalDate datePublication;
		
}
