package com.alcedomoreno.sirme.business.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.alcedomoreno.sirme.business.data.QuestionGroup;
import com.alcedomoreno.sirme.business.data.Reply;
import com.alcedomoreno.sirme.business.data.ReplyGroup;

public class ReplyGroupDTO {

	@JsonProperty("id")
	private int idReplyGroup;
	@JsonProperty("name")
	private String nameReplyGroup;
	@JsonProperty("questionGroup")
	private QuestionGroupDTO questionGroup;
	@JsonProperty("replies")
	private List<ReplyDTO> replies;

	public ReplyGroupDTO(ReplyGroup rg) {
		idReplyGroup = rg.getIdReplyGroup();
		nameReplyGroup = rg.getNameReplyGroup();
		replies = new ArrayList<ReplyDTO>();
		
		questionGroup = new QuestionGroupDTO(rg.getQuestionGroup());
		
		if ( rg.getReplies() != null && !rg.getReplies().isEmpty() )
			for ( Reply reply:rg.getReplies() )
				replies.add( new ReplyDTO(reply) );
	}
	
	public ReplyGroupDTO(){}

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

	public QuestionGroupDTO getQuestionGroup() {
		return questionGroup;
	}

	public void setQuestionGroup(QuestionGroupDTO questionGroup) {
		this.questionGroup = questionGroup;
	}

	public List<ReplyDTO> getReplies() {
		return replies;
	}

	public void setReplies(List<ReplyDTO> replies) {
		this.replies = replies;
	}

}
