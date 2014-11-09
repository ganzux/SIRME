package com.alcedomoreno.sirme.business.data;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.alcedomoreno.sirme.business.transform.ReplyGroupTransform;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class ReplyGroup implements BusinessObject,Cloneable{


	private int idReplyGroup;
	private String nameReplyGroup;

	@JsonIgnore
	private Work work;
	
	private QuestionGroup questionGroup;
	
	private List<Reply> replies;

	public ReplyGroup() {
		super();
	}
	public int getIdReplyGroup() {
		return idReplyGroup;
	}
	public void setIdReplyGroup(int idReplyGroup) {
		this.idReplyGroup = idReplyGroup;
	}
	public String getNameReplyGroup() {
		return nameReplyGroup;
	}
	public void setNameReplyGroup(String nameReplyGroup) {
		this.nameReplyGroup = nameReplyGroup;
	}
	public Work getWork() {
		return work;
	}
	public void setWork(Work work) {
		this.work = work;
	}
	public QuestionGroup getQuestionGroup() {
		return questionGroup;
	}
	public void setQuestionGroup(QuestionGroup questionGroup) {
		this.questionGroup = questionGroup;
	}
	public List<Reply> getReplies() {
		return replies;
	}
	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
	public void addReply(Reply reply) {
		if ( replies == null )
			replies = new ArrayList<Reply>();
		replies.add( reply );
	}
	@Override
	public Class<? extends Transformator> getTransformator() {
		return ReplyGroupTransform.class;
	}


}