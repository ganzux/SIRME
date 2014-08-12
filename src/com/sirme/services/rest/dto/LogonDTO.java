package com.sirme.services.rest.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class LogonDTO {

	@JsonProperty
	private boolean succesful = false;
	@JsonProperty
	private String teamName;
	@JsonProperty
	private int teamId;
	@JsonProperty
	private boolean canUploadPhotos = false;
	@JsonProperty
	private Date serverOnTime;

	public boolean isSuccesful() {
		return succesful;
	}

	public void setSuccesful(boolean succesful) {
		this.succesful = succesful;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String toString() {
		return teamName + ", " + succesful + ", " + teamId;
	}

	public boolean isCanUploadPhotos() {
		return canUploadPhotos;
	}

	public void setCanUploadPhotos(boolean canUploadPhotos) {
		this.canUploadPhotos = canUploadPhotos;
	}

	public Date getServerOnTime() {
		return serverOnTime;
	}

	public void setServerOnTime(Date serverOnTime) {
		this.serverOnTime = serverOnTime;
	}

}
