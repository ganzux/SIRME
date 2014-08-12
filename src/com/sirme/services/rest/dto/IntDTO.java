package com.sirme.services.rest.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class IntDTO {

	@JsonProperty("id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	
}
