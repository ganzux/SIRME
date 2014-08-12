package com.sirme.services.rest.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import com.sirme.bussiness.Question;

public class QuestionDTO {

	@JsonProperty("id")
	private int idQuestion;
	@JsonProperty("question")
	private String question;
	@JsonProperty("order")
	private Integer order;
	@JsonProperty("replyType")
	private ReplyTypeDTO replyType;
	
	public QuestionDTO( Question question ){
		idQuestion = question.getIdQuestion();
		this.question = question.getQuestion();
		order = question.getOrder();
		replyType = new ReplyTypeDTO( question.getReplyType() );
	}

	public int getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public ReplyTypeDTO getReplyType() {
		return replyType;
	}

	public void setReplyType(ReplyTypeDTO replyType) {
		this.replyType = replyType;
	}

}
