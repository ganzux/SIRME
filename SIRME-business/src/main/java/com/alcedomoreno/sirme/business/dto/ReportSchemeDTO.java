package com.alcedomoreno.sirme.business.dto;

import java.util.Collection;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReportSchemeDTO {

	@JsonProperty("id")
	private int idReport;
	@JsonProperty("name")
	private String nameReport;
	@JsonProperty("questionGroups")
	private Collection<QuestionGroupDTO> questionGroups;
	@JsonProperty("work")
	private WorkDTO workDTO;

	public int getIdReport() {
		return idReport;
	}

	public void setIdReport(int idReport) {
		this.idReport = idReport;
	}

	public String getNameReport() {
		return nameReport;
	}

	public void setNameReport(String nameReport) {
		this.nameReport = nameReport;
	}

	public Collection<QuestionGroupDTO> getQuestionGroups() {
		return questionGroups;
	}

	public void setQuestionGroups(Collection<QuestionGroupDTO> questionGroups) {
		this.questionGroups = questionGroups;
	}

	public WorkDTO getWorkDTO() {
		return workDTO;
	}

	public void setWorkDTO(WorkDTO workDTO) {
		this.workDTO = workDTO;
	}

}
