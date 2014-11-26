package com.alcedomoreno.sirme.core.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
@Entity
@Proxy(lazy=true)
@Table(name="replytype")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ReplyTypeData implements Serializable,DataObject {
	
	private static final long serialVersionUID = 1L;
	
	public ReplyTypeData() {}
	
	public ReplyTypeData(int id) {
		this.idReplyType = id;
	}
	
	@Column(name="idReplyType", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idReplyType;
	
	@Column(name="nameReplyType", nullable=false, unique=true, length=20)	
	private String nameReplyType;

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
