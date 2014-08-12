package com.sirme.services.rest.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class TeamWorksListRequestDTO {
	
	@JsonProperty
	private int teamId;

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
}
