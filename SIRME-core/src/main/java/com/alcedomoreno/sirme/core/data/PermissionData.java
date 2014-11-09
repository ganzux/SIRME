package com.alcedomoreno.sirme.core.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@org.hibernate.annotations.Proxy(lazy=true)
@Table(name="permissions")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class PermissionData implements DataObject{

	
	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof PermissionData))
			return false;
		PermissionData userData = (PermissionData) aObj;
		if (getIdPermission() != userData.getIdPermission())
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		hashcode = hashcode + getIdPermission();
		return hashcode;
	}
	
	@Column(name="idPermission", nullable=false, unique=true)	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idPermission;
	
	@Column(name="codePermission", nullable=false, unique=true, length=50)
	private String codePermission;
	
	@Column(name="descriptionPermission", nullable=true, length=255)
	private String descriptionPermission;
	
	@ManyToOne(targetEntity=ApplicationData.class,fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="idApplication", referencedColumnName="idApplication") })		
	private ApplicationData application;
	
	public void setIdPermission(int value) {
		this.idPermission = value;
	}
	
	public int getIdPermission() {
		return this.idPermission;
	}
		
	public void setCodePermission(String value) {
		this.codePermission = value;
	}
	
	public String getCodePermission() {
		return this.codePermission;
	}

	public String getAuthority() {
		return this.codePermission;
	}
	
	public void setDescriptionPermission(String value) {
		this.descriptionPermission = value;
	}
	
	public String getDescriptionPermission() {
		return this.descriptionPermission;
	}
	
	public void setApplication(ApplicationData value) {
		this.application = value;
	}
	
	public ApplicationData getApplication() {
		return application;
	}
	
	public String toString() {
		return String.valueOf(getCodePermission());
	}
	
}
