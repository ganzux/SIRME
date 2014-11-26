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
@Table(name="questiongroup")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class QuestionGroupData implements Serializable,DataObject {
	
	private static final long serialVersionUID = 1L;
	
	public QuestionGroupData() {}
	
	public QuestionGroupData(int id) {
		this.idQuestionGroup = id;
	}
	
	@Column(name="idQuestionGroup", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idQuestionGroup;
	
	@Column(name="times", nullable=true, unique=false)	
	private Integer times;
	
	@Column(name="nameQuestionGroup", nullable=false, unique=false)
	private String nameQuestionGroup;

	public String getNameQuestionGroup() {
		return nameQuestionGroup;
	}

	public void setNameQuestionGroup(String nameQuestionGroup) {
		this.nameQuestionGroup = nameQuestionGroup;
	}

	@OneToMany(mappedBy="questionGroup", targetEntity=QuestionData.class)	
	@Cascade({CascadeType.LOCK})	
	@LazyCollection(LazyCollectionOption.FALSE)	
	private Set<QuestionData> questions = new HashSet<QuestionData>();
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idReport",nullable=true)
	private ReportData report;

	public int getIdQuestionGroup() {
		return idQuestionGroup;
	}

	public void setIdQuestionGroup(int idQuestionGroup) {
		this.idQuestionGroup = idQuestionGroup;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Set<QuestionData> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<QuestionData> questions) {
		this.questions = questions;
	}

	public ReportData getReport() {
		return report;
	}

	public void setReport(ReportData report) {
		this.report = report;
	}

	
}
