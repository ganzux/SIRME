package com.alcedomoreno.sirme.core.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;
@Entity
@Proxy(lazy=true)
@Table(name="replygroup")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ReplyGroupData implements Serializable,DataObject {
	
	private static final long serialVersionUID = 1L;
	
	public ReplyGroupData() {}
	
	public ReplyGroupData(int id) {
		this.idReplyGroup = id;
	}
	
	@Column(name="idReplyGroup", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idReplyGroup;
	
	@Column(name="nameReplyGroup", nullable=false, unique=false, length=100)	
	private String nameReplyGroup;

	@OneToMany(mappedBy="replyGroup", targetEntity=ReplyData.class)	
	@Cascade({CascadeType.SAVE_UPDATE,CascadeType.DELETE})	
	@LazyCollection(LazyCollectionOption.FALSE)	
	private Set<ReplyData> replies = new HashSet<ReplyData>();
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idQuestionGroup",nullable=false)
	private QuestionGroupData questionGroup;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idWork",nullable=false)
	private WorkData work;
	
	@ManyToOne(fetch=FetchType.LAZY)
	// TODO: true or false??????????????
    @JoinColumn(name="idReport",nullable=false)
	private ReportData report;

	public int getIdReplyGroup() {
		return idReplyGroup;
	}
	public void setIdReplyGroup(int idReplyGroup) {
		this.idReplyGroup = idReplyGroup;
	}
	public Set<ReplyData> getReplies() {
		return replies;
	}
	public void setReplies(Set<ReplyData> replies) {
		this.replies = replies;
	}
	public QuestionGroupData getQuestionGroup() {
		return questionGroup;
	}
	public void setQuestionGroup(QuestionGroupData questionGroup) {
		this.questionGroup = questionGroup;
	}
	public String getNameReplyGroup() {
		return nameReplyGroup;
	}
	public void setNameReplyGroup(String nameReplyGroup) {
		this.nameReplyGroup = nameReplyGroup;
	}
	public WorkData getWork() {
		return work;
	}
	public void setWork(WorkData work) {
		this.work = work;
	}

	public ReportData getReport() {
		return report;
	}

	public void setReport(ReportData report) {
		this.report = report;
	}	
}