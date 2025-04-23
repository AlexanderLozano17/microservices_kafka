package com.demo.person.domain.event.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String names;
	private String lastNames;
	private int age;
	private String email;
	private String telephone;

}
