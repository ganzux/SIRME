package com.sirme.services.rest.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.annotate.JsonProperty;

import com.sirme.bussiness.Question;
import com.sirme.bussiness.QuestionGroup;

public class QuestionGroupDTO {

	@JsonProperty("id")
	private int idQuestionGroup;
	@JsonProperty("name")
	private String nameQuestionGroup;
	@JsonProperty("times")
	private Integer times;
	@JsonProperty("questions")
	private Collection<QuestionDTO> questions;
	
	public QuestionGroupDTO( QuestionGroup questionGroup ){
		idQuestionGroup = questionGroup.getIdQuestionGroup();
		nameQuestionGroup = questionGroup.getNameQuestionGroup();
		times = questionGroup.getTimes();

		if ( questionGroup.getQuestions() != null ){
			questions = new ArrayList<QuestionDTO>();
			for ( Question q:questionGroup.getQuestions() )
				questions.add( new QuestionDTO(q) );
		}
	}

	public int getIdQuestionGroup() {
		return idQuestionGroup;
	}

	public void setIdQuestionGroup(int idQuestionGroup) {
		this.idQuestionGroup = idQuestionGroup;
	}

	public String getNameQuestionGroup() {
		return nameQuestionGroup;
	}

	public void setNameQuestionGroup(String nameQuestionGroup) {
		this.nameQuestionGroup = nameQuestionGroup;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Collection<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(Collection<QuestionDTO> questions) {
		this.questions = questions;
	}

}
