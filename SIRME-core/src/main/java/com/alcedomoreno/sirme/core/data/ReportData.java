package com.alcedomoreno.sirme.core.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Table(name="report")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ReportData implements Serializable,DataObject {
	
	private static final long serialVersionUID = 1L;
	
	public ReportData() {}
	
	public ReportData(int id) {
		this.idReport = id;
	}
	
	@Column(name="idReport", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idReport;
	
	@Column(name="nameReport", nullable=false, unique=true, length=50)	
	private String nameReport;
	
	@Column(name="titleReport", nullable=false, unique=true, length=250)	
	private String titleReport;
	
	@Column(name="fileReport", nullable=false, unique=true, length=20)	
	private String fileReport;

	@OneToMany(mappedBy="report", targetEntity=QuestionGroupData.class)	
	@Cascade({CascadeType.LOCK})	
	@LazyCollection(LazyCollectionOption.FALSE)	
	private Set<QuestionGroupData> questionGroups = new HashSet<QuestionGroupData>();
	
	public int getIdReport() {
		return idReport;
	}
	public void setIdReport(int idReport) {
		this.idReport = idReport;
	}
	public String getNameReport() {
		return nameReport;
	}
	public void setNameReport(String nameReport) {
		this.nameReport = nameReport;
	}

	public Set<QuestionGroupData> getQuestionGroups() {
		return questionGroups;
	}

	public String getFileReport() {
		return fileReport;
	}

	public void setFileReport(String fileReport) {
		this.fileReport = fileReport;
	}

	public String getTitleReport() {
		return titleReport;
	}

	public void setTitleReport(String titleReport) {
		this.titleReport = titleReport;
	}

	public void setQuestionGroups(Set<QuestionGroupData> questionGroups) {
		this.questionGroups = questionGroups;
	}

}
