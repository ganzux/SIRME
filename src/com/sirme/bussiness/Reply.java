package com.sirme.bussiness;

import com.sirme.transform.ITransformator;
import com.sirme.transform.ReplyTransform;

public class Reply implements IBusinessObject,Cloneable{


	private int idReply;
	private Question question;
	private String reply;
	private ReplyGroup replyGroup;

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
	public Class<? extends ITransformator> getTransformator() {
		return ReplyTransform.class;
	}

}