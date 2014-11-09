package com.alcedomoreno.sirme.core.data;



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


@Entity
@org.hibernate.annotations.Proxy(lazy=true)
@Table(name="applications")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ApplicationData implements DataObject {
	
	public static final byte ROOT_LEVEL = 0;
	public static final byte MAX_LEVEL = 3;
	
	@Column(name="idApplication", nullable=false)	
	@Id	
	@GeneratedValue(generator="VC0A8010D133C5D06EC8078C6")	
	@org.hibernate.annotations.GenericGenerator(name="VC0A8010D133C5D06EC8078C6", strategy="native")	
	private int idApplication;
	
	@ManyToOne(targetEntity=ApplicationData.class,fetch=FetchType.LAZY)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK})	
	@JoinColumns({ @JoinColumn(name="idMainApplication", referencedColumnName="idApplication") })	
	private ApplicationData mainApplication;
	
	@Column(name="codeApplication", nullable=false, unique=true, length=10)	
	private String codeApplication;
	
	@Column(name="nameApplication", nullable=false, unique=true, length=50)	
	private String nameApplication;
	
	@Column(name="levelApplication", nullable=false)	
	private byte levelApplication;
	
	@OneToMany(mappedBy="mainApplication", targetEntity=ApplicationData.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
	private java.util.Set<ApplicationData> childApplications = new java.util.HashSet<ApplicationData>();
	
	@OneToMany(mappedBy="application", targetEntity=PermissionData.class)	
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})	
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
	private java.util.Set<PermissionData> permissions = new java.util.HashSet<PermissionData>();
	
	public void setIdApplication(int value) {
		this.idApplication = value;
	}
	
	public int getIdApplication() {
		return idApplication;
	}
	
	public int getORMID() {
		return getIdApplication();
	}
	
	public void setNameApplication(String value) {
		this.nameApplication = value;
	}
	
	public String getNameApplication() {
		return nameApplication;
	}
	
	public void setLevelApplication(byte value) {
		this.levelApplication = value;
	}
	
	public byte getLevelApplication() {
		return levelApplication;
	}
	
	public void setCodeApplication(String value) {
		this.codeApplication = value;
	}
	
	public String getCodeApplication() {
		return codeApplication;
	}
	
	public void setChildApplications(Set<ApplicationData> value) {
		this.childApplications = value;
	}
	
	public Set<ApplicationData> getChildApplications() {
		return childApplications;
	}
	
	
	public void setPermissions(Set<PermissionData> value) {
		this.permissions = value;
	}
	
	public Set<PermissionData> getPermissions() {
		return permissions;
	}
	
	
	public void setMainApplication(ApplicationData value) {
		this.mainApplication = value;
	}
	
	public ApplicationData getMainApplication() {
		return mainApplication;
	}
	
	public String toString() {
		return String.valueOf(getIdApplication());
	}
	
}
