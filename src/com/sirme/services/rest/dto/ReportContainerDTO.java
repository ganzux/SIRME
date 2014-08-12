package com.sirme.services.rest.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import com.sirme.bussiness.Report;

public class ReportContainerDTO {

	@JsonProperty("reportScheme")
	private Report reportScheme;

	@JsonProperty("reportData")
	private Report reportData;

	public Report getReportScheme() {
		return reportScheme;
	}

	public void setReportScheme(Report reportScheme) {
		this.reportScheme = reportScheme;
	}

	public Report getReportData() {
		return reportData;
	}

	public void setReportData(Report reportData) {
		this.reportData = reportData;
	}

}
