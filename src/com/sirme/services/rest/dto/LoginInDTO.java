package com.sirme.services.rest.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class LoginInDTO {

	@JsonProperty
	private String team;
	@JsonProperty
	private String password;
	@JsonProperty
	private String deviceId;

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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceID) {
		this.deviceId = deviceID;
	}

	@Override
	public String toString() {
		return "LoginInDTO [team=" + team + ", password=" + password
				+ ", deviceID=" + deviceId + "]";
	}

}
