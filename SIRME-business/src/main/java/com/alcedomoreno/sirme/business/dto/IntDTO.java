package com.alcedomoreno.sirme.business.dto;

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
