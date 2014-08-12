package com.sirme.services.rest.dto;

import java.util.Collection;

import org.codehaus.jackson.annotate.JsonProperty;

public class QuestionAnswerDTO {

	@JsonProperty
	private int idQuestionAnswer;
	@JsonProperty
	private String question;
	@JsonProperty
	private String lastAnswer;
	@JsonProperty
	private int typeAnswer;
	@JsonProperty
	private Collection<String> usualAnswer;
	
	
	
	
	public int getIdQuestionAnswer() {
		return idQuestionAnswer;
	}
	public void setIdQuestionAnswer(int idQuestionAnswer) {
		this.idQuestionAnswer = idQuestionAnswer;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getLastAnswer() {
		return lastAnswer;
	}
	public void setLastAnswer(String lastAnswer) {
		this.lastAnswer = lastAnswer;
	}
	public int getTypeAnswer() {
		return typeAnswer;
	}
	public void setTypeAnswer(int typeAnswer) {
		this.typeAnswer = typeAnswer;
	}
	public Collection<String> getUsualAnswer() {
		return usualAnswer;
	}
	public void setUsualAnswer(Collection<String> usualAnswer) {
		this.usualAnswer = usualAnswer;
	}
	
	

	
}
