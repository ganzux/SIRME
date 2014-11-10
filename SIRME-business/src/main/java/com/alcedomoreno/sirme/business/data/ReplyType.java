package com.alcedomoreno.sirme.business.data;

import com.alcedomoreno.sirme.business.transform.ReplyTypeTransform;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class ReplyType implements BusinessObject,Cloneable{

	
	private int idReplyType;
	private String nameReplyType;


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
	public Class<? extends Transformator> getTransformator() {
		return ReplyTypeTransform.class;
	}
	@Override
	public String toString() {
		return "ReplyType [idReplyType=" + idReplyType + ", nameReplyType="
				+ nameReplyType + "]";
	}
	
}
