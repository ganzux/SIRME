package com.sirme.bussiness;

import com.sirme.services.rest.dto.ReplyTypeDTO;
import com.sirme.transform.ITransformator;
import com.sirme.transform.ReplyTypeTransform;

public class ReplyType implements IBusinessObject,Cloneable{

	
	private int idReplyType;
	private String nameReplyType;
	
	
	
	public ReplyType(ReplyTypeDTO replyType) {
		idReplyType = replyType.getIdReplyType();
		nameReplyType = replyType.getNameReplyType();
	}



	public ReplyType() {
		super();
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



	@Override
	public Class<? extends ITransformator> getTransformator() {
		return ReplyTypeTransform.class;
	}
}
