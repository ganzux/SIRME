package com.alcedomoreno.sirme.business.dto;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.annotate.JsonProperty;

import com.alcedomoreno.sirme.business.data.QuestionGroup;
import com.alcedomoreno.sirme.business.data.ReplyGroup;
import com.alcedomoreno.sirme.business.data.Report;

public class ReportDTO implements Comparable<ReportDTO>{

	public ReportDTO(){
		super();
	}

	public ReportDTO(Report report){
		idReport = report.getIdReport();
		nameReport = report.getNameReport();

		if (report.getReplyGroups() != null){
			replyGroups = new ArrayList<ReplyGroupDTO>();
			for (ReplyGroup q:report.getReplyGroups())
				replyGroups.add(new ReplyGroupDTO(q));
		}
		
		if (report.getQuestionGroups() != null){
			questionGroups = new ArrayList<QuestionGroupDTO>();
			for ( QuestionGroup q:report.getQuestionGroups())
				questionGroups.add(new QuestionGroupDTO(q));
		}
	}
	
	public void sort(){
		if (questionGroups != null){
			Collections.sort(questionGroups);
			for (QuestionGroupDTO questionGroupsDTO : questionGroups){
				if (questionGroupsDTO.getQuestions() != null){
					Collections.sort(questionGroupsDTO.getQuestions());
				}
			}
		}

		if (replyGroups != null){
			Collections.sort(replyGroups);
			for (ReplyGroupDTO replyGroupDTO : replyGroups){
				if (replyGroupDTO.getReplies() != null){
					Collections.sort(replyGroupDTO.getReplies());
				}
			}
		}
	}
	
	@JsonProperty
	private int idReport;
	@JsonProperty
	private String nameReport;
	@JsonProperty
	private List<ReplyGroupDTO> replyGroups;
	@JsonProperty
	private List<QuestionGroupDTO> questionGroups;

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
	public List<ReplyGroupDTO> getReplyGroups() {
		return replyGroups;
	}
	public void setReplyGroups(List<ReplyGroupDTO> replyGroups) {
		this.replyGroups = replyGroups;
	}
	public List<QuestionGroupDTO> getQuestionGroups() {
		return questionGroups;
	}
	public void setQuestionGroups(List<QuestionGroupDTO> questionGroups) {
		this.questionGroups = questionGroups;
	}

	@Override
	public String toString() {
		return "ReportDTO [idReport=" + idReport + ", nameReport=" + nameReport
				+ ", replyGroups=" + replyGroups + ", questionGroups="
				+ questionGroups + "]";
	}

	@Override
	public int compareTo(ReportDTO o) {
		Collator esCollator = Collator.getInstance(new Locale("ES"));
	    esCollator.setStrength(Collator.PRIMARY);
	    return esCollator.compare(o.getNameReport(), getNameReport());
	}


	

}
