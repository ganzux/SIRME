package com.alcedomoreno.sirme.core.data;

import java.io.Serializable;
import java.util.Date;

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
import org.hibernate.annotations.Type;

@Entity
@Proxy(lazy=true)
@Table(name="photos")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class PhotoData implements Serializable,DataObject {

private static final long serialVersionUID = 1L;
	
	public PhotoData() {}
	
	public PhotoData(int id) {
		this.idPhoto = id;
	}
	
	@Column(name="idPhoto", nullable=false)
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idPhoto;

	@Column(name="dateCreated")
	@Type(type="timestamp")
	private Date dateCreated;
	
	@ManyToOne(targetEntity=WorkData.class,fetch=FetchType.EAGER)
	@Cascade({CascadeType.LOCK})
	@JoinColumns({ @JoinColumn(name="idWork", referencedColumnName="idWork") })	
	private WorkData work;
	
	@Column(name="path", nullable=false, unique=true)	
	private String path;
	
	@Column(name="comments", nullable=true, unique=false)	
	private String comments;
	


	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public int getIdPhoto() {
		return idPhoto;
	}
	public void setIdPhoto(int idPhoto) {
		this.idPhoto = idPhoto;
	}
	public WorkData getWork() {
		return work;
	}
	public void setWork(WorkData work) {
		this.work = work;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}