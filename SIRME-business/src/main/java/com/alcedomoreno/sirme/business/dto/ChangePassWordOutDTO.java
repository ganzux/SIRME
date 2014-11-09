package com.alcedomoreno.sirme.business.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class ChangePassWordOutDTO {

	@JsonProperty
	private String team;
	@JsonProperty
	private String operation;
	@JsonProperty
	private String error;

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}



	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "LoginInDTO [team=" + team + ", operation=" + operation + ", error=" + error + "]";
	}

}
