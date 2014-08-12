package com.sirme.services.rest.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.sirme.bussiness.Report;
import com.sirme.bussiness.Work;

public class WorkDTO extends AdviceDTO{

	@JsonProperty
	private List<ReportDTO> reports;

	
	public WorkDTO(Work w) {
		super( w );
		
		reports = new ArrayList<ReportDTO>();
		if ( w.getReports() != null && !w.getReports().isEmpty() )
			for ( Report rep:w.getReports() )
				reports.add( new ReportDTO(rep) );
	}
	public List<ReportDTO> getReports() {
		return reports;
	}
	public void setReports(List<ReportDTO> reports) {
		this.reports = reports;
	}

}