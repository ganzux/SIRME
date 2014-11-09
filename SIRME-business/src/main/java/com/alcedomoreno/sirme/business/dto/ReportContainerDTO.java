package com.alcedomoreno.sirme.business.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import com.alcedomoreno.sirme.business.data.Report;

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
