package com.alcedomoreno.sirme.business.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import com.alcedomoreno.sirme.business.data.ReplyType;

public class ReplyTypeDTO {

	@JsonProperty("id")
	private int idReplyType;
	@JsonProperty("name")
	private String nameReplyType;
	
	public ReplyTypeDTO(){
		super();
	}
	
	public ReplyTypeDTO( ReplyType replyType ){
		idReplyType = replyType.getIdReplyType();
		nameReplyType = replyType.getNameReplyType();
	}

	public int getIdReplyType() {
		return idReplyType;
	}

	public void setIdReplyType(int idReplyType) {
		this.idReplyType = idReplyType;
	}

	public String getNameReplyType() {
		return nameReplyType;
	}

	public void setNameReplyType(String nameReplyType) {
		this.nameReplyType = nameReplyType;
	}

}
