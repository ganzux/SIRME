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
import javax.persistence.JoinColumns;
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
@Table(name="question")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class QuestionData implements Serializable,DataObject {
	
	private static final long serialVersionUID = 1L;
	
	public QuestionData() {}
	
	public QuestionData(int id) {
		this.idQuestion = id;
	}
	
	@Column(name="idQuestion", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idQuestion;
	
	@Column(name="question", nullable=false, unique=false, length=255)	
	private String question;
	
	@Column(name="[order]", nullable=true, unique=false, length=3)	
	private Integer order;
	
	@Column(name = "totalize", nullable = true, length = 1)
	private Boolean totalize;
	
	@ManyToOne(targetEntity=QuestionGroupData.class,fetch=FetchType.LAZY)
	@Cascade({CascadeType.LOCK})
	@JoinColumns({ @JoinColumn(name="idQuestionGroup", referencedColumnName="idQuestionGroup") })	
	private QuestionGroupData questionGroup;
	
	@ManyToOne(targetEntity=ReplyTypeData.class,fetch=FetchType.LAZY)
	@Cascade({CascadeType.LOCK})
	@JoinColumns({ @JoinColumn(name="idReplyType", referencedColumnName="idReplyType") })	
	private ReplyTypeData replyType;
	
	@OneToMany(mappedBy="question", targetEntity=ReplyData.class)	
	@Cascade({CascadeType.LOCK})	
	@LazyCollection(LazyCollectionOption.TRUE)	
	private Set<ReplyData> replies = new HashSet<ReplyData>();

	public int getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	public QuestionGroupData getQuestionGroup() {
		return questionGroup;
	}

	public void setQuestionGroup(QuestionGroupData questionGroup) {
		this.questionGroup = questionGroup;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public ReplyTypeData getReplyType() {
		return replyType;
	}

	public void setReplyType(ReplyTypeData replyType) {
		this.replyType = replyType;
	}

	public Set<ReplyData> getReplies() {
		return replies;
	}

	public void setReplies(Set<ReplyData> replies) {
		this.replies = replies;
	}

	public Boolean getTotalize() {
		return totalize;
	}

	public void setTotalize(Boolean totalize) {
		this.totalize = totalize;
	}

}
