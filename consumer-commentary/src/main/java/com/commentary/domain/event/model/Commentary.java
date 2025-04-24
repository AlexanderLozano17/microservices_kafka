package com.commentary.domain.event.model;

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
public class Commentary implements Serializable {  

		private static final long serialVersionUID = 1L;		
		private Long id;
		private Long publication_id;
		private Long person_id;
		private LocalDate dateCommentary;
		private String content;
}
