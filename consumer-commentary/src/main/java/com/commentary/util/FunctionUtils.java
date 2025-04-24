package com.commentary.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class FunctionUtils {

	/**
	 * 
	 * @param object
	 */
	public static void printJsonPretty(Object object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule()); // soporte para Java 8 date/time
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // para que use el formato ISO (yyyy-MM-dd)
			mapper.enable(SerializationFeature.INDENT_OUTPUT); // activa el "pretty print"

			String prettyJson = mapper.writeValueAsString(object);
			System.out.println(prettyJson);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
