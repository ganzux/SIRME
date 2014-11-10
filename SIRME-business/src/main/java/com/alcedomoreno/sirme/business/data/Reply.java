package com.alcedomoreno.sirme.business.data;

import com.alcedomoreno.sirme.business.transform.ReplyTransform;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class Reply implements BusinessObject,Cloneable{


	private int idReply;
	private Question question;
	private String reply;
	private ReplyGroup replyGroup;

	public Reply() {
		super();
	}
	public int getIdReply() {
		return idReply;
	}
	public void setIdReply(int idReply) {
		this.idReply = idReply;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public ReplyGroup getReplyGroup() {
		return replyGroup;
	}
	public void setReplyGroup(ReplyGroup replyGroup) {
		this.replyGroup = replyGroup;
	}
	@Override
	public Class<? extends Transformator> getTransformator() {
		return ReplyTransform.class;
	}
	@Override
	public String toString() {
		return "Reply [idReply=" + idReply + ", question=" + question
				+ ", reply=" + reply + ", replyGroup=" + replyGroup + "]";
	}
	

}