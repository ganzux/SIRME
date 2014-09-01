package com.sirme.bussiness;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sirme.services.rest.dto.ReplyDTO;
import com.sirme.services.rest.dto.ReplyGroupDTO;
import com.sirme.transform.ITransformator;
import com.sirme.transform.ReplyGroupTransform;

public class ReplyGroup implements IBusinessObject,Cloneable{


	private int idReplyGroup;
	private String nameReplyGroup;

	@JsonIgnore
	private Work work;
	
	private QuestionGroup questionGroup;
	
	private List<Reply> replies;

	
	public ReplyGroup(ReplyGroupDTO rg) {
		idReplyGroup = rg.getIdReplyGroup();
		nameReplyGroup = rg.getNameReplyGroup();
		replies = new ArrayList<Reply>();
		
		if ( rg.getReplies() != null && !rg.getReplies().isEmpty() )
			for ( ReplyDTO reply:rg.getReplies() )
				replies.add( new Reply(reply) );
	}
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
	public Class<? extends ITransformator> getTransformator() {
		return ReplyGroupTransform.class;
	}


}