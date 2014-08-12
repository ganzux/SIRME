package com.sirme.services.rest.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;


public class WorksByTeamDTO {
	
	@JsonProperty
	private List<WorkDTO> works;

	public List<WorkDTO> getWorks() {
		return works;
	}

	public void setWorks(List<WorkDTO> works) {
		this.works = works;
	}
	
	

}
