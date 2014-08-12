package com.sirme.services.rest.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class WorkInfoRequestDTO {

	@JsonProperty
	private int workId;

	public int getWorkId() {
		return workId;
	}

	public void setWorkId(int workId) {
		this.workId = workId;
	}

}
