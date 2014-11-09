package com.alcedomoreno.sirme.business.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class ChangePassWordInDTO {

	@JsonProperty
	private String team;
	@JsonProperty
	private String password;
	@JsonProperty
	private String newPassword;

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "LoginInDTO [team=" + team + ", password=" + password+ ", newPassword=" + newPassword + "]";
	}

}
