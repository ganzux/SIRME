package com.alcedomoreno.sirme.business.dto;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.annotate.JsonProperty;

import com.alcedomoreno.sirme.business.data.Question;
import com.alcedomoreno.sirme.business.data.QuestionGroup;

public class QuestionGroupDTO implements Comparable<QuestionGroupDTO>{

	@JsonProperty("id")
	private int idQuestionGroup;
	@JsonProperty("name")
	private String nameQuestionGroup;
	@JsonProperty("times")
	private Integer times;
	@JsonProperty("questions")
	private List<QuestionDTO> questions;
	
	public QuestionGroupDTO(QuestionGroup questionGroup){
		idQuestionGroup = questionGroup.getIdQuestionGroup();
		nameQuestionGroup = questionGroup.getNameQuestionGroup();
		times = questionGroup.getTimes();

		if ( questionGroup.getQuestions() != null ){
			questions = new ArrayList<QuestionDTO>();
			for (Question q:questionGroup.getQuestions())
				questions.add(new QuestionDTO(q));
		}
	}
	
	public QuestionGroupDTO(){}

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

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

	@Override
	public int compareTo(QuestionGroupDTO o) {
		Collator esCollator = Collator.getInstance(new Locale("ES"));
	    esCollator.setStrength(Collator.PRIMARY);
	    return esCollator.compare(o.getNameQuestionGroup(), getNameQuestionGroup());
	}

}
