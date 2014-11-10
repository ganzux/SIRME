package com.alcedomoreno.sirme.business.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.data.Work;

public class WorkDTO extends AdviceDTO{

	@JsonProperty
	private List<ReportDTO> reports;

	public WorkDTO(){}
	
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

	@Override
	public String toString() {
		return super.toString() + " - WorkDTO [reports=" + reports + "]";
	}
	
	

}