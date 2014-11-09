package com.alcedomoreno.sirme.core.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;


@Entity
@Proxy(lazy=true)
@Table(name="reply")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ReplyData implements Serializable,DataObject {

	
	private static final long serialVersionUID = 1L;
	
	public ReplyData() {}
	
	public ReplyData(int id) {
		this.idReply = id;
	}

	@Column(name="idReply", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idReply;

	@Column(name="[reply]", nullable=true, unique=false, length=255)	
	private String reply;
	
	@ManyToOne(targetEntity=QuestionData.class,fetch=FetchType.EAGER)
	@Cascade({CascadeType.LOCK})
	@JoinColumns({ @JoinColumn(name="idQuestion", referencedColumnName="idQuestion") })	
	private QuestionData question;
	
	@ManyToOne(targetEntity=ReplyGroupData.class,fetch=FetchType.EAGER)
	@Cascade({CascadeType.LOCK})
	@JoinColumns({ @JoinColumn(name="idReplyGroup", referencedColumnName="idReplyGroup") })	
	private ReplyGroupData replyGroup;
	
	public int getIdReply() {
		return idReply;
	}
	public void setIdReply(int idReply) {
		this.idReply = idReply;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}

	public QuestionData getQuestion() {
		return question;
	}

	public void setQuestion(QuestionData question) {
		this.question = question;
	}

	public ReplyGroupData getReplyGroup() {
		return replyGroup;
	}

	public void setReplyGroup(ReplyGroupData replyGroup) {
		this.replyGroup = replyGroup;
	}

}
