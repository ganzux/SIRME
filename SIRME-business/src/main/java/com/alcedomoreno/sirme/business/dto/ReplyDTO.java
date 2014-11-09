package com.alcedomoreno.sirme.business.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import com.alcedomoreno.sirme.business.data.Reply;


public class ReplyDTO {

	@JsonProperty("id")
	private int idReply;
	@JsonProperty("question")
	private QuestionDTO question;
	@JsonProperty("reply")
	private String reply;

	public ReplyDTO(Reply reply2) {
		this.idReply = reply2.getIdReply();
		this.question = new QuestionDTO( reply2.getQuestion() );
		this.reply = reply2.getReply();
	}
	
	public ReplyDTO(){}

	public int getIdReply() {
		return idReply;
	}

	public void setIdReply(int idReply) {
		this.idReply = idReply;
	}

	public QuestionDTO getQuestion() {
		return question;
	}

	public void setQuestion(QuestionDTO question) {
		this.question = question;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

}
