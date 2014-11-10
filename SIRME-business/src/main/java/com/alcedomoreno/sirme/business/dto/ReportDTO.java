package com.alcedomoreno.sirme.business.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.annotate.JsonProperty;

import com.alcedomoreno.sirme.business.data.QuestionGroup;
import com.alcedomoreno.sirme.business.data.ReplyGroup;
import com.alcedomoreno.sirme.business.data.Report;

public class ReportDTO {

	public ReportDTO(){
		super();
	}

	public ReportDTO( Report report ){
		idReport = report.getIdReport();
		nameReport = report.getNameReport();

		if ( report.getReplyGroups() != null ){
			replyGroups = new ArrayList<ReplyGroupDTO>();
			for ( ReplyGroup q:report.getReplyGroups() )
				replyGroups.add( new ReplyGroupDTO(q) );
		}
		
		if ( report.getQuestionGroups() != null ){
			questionGroups = new ArrayList<QuestionGroupDTO>();
			for ( QuestionGroup q:report.getQuestionGroups() )
				questionGroups.add( new QuestionGroupDTO(q) );
		}
	}
	
	@JsonProperty
	private int idReport;
	@JsonProperty
	private String nameReport;
	@JsonProperty
	private Collection<ReplyGroupDTO> replyGroups;
	@JsonProperty
	private Collection<QuestionGroupDTO> questionGroups;

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
	public Collection<ReplyGroupDTO> getReplyGroups() {
		return replyGroups;
	}
	public void setReplyGroups(Collection<ReplyGroupDTO> replyGroups) {
		this.replyGroups = replyGroups;
	}
	public Collection<QuestionGroupDTO> getQuestionGroups() {
		return questionGroups;
	}
	public void setQuestionGroups(Collection<QuestionGroupDTO> questionGroups) {
		this.questionGroups = questionGroups;
	}

	@Override
	public String toString() {
		return "ReportDTO [idReport=" + idReport + ", nameReport=" + nameReport
				+ ", replyGroups=" + replyGroups + ", questionGroups="
				+ questionGroups + "]";
	}


	

}
