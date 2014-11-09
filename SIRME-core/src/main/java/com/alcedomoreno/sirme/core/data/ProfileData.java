package com.alcedomoreno.sirme.core.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity
@org.hibernate.annotations.Proxy(lazy=true)
@Table(name="profiles")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ProfileData implements DataObject{


	
	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof ProfileData))
			return false;
		ProfileData userData = (ProfileData) aObj;
		if (getIdProfile() != userData.getIdProfile())
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		hashcode = hashcode + getIdProfile();
		return hashcode;
	}
	
	@Column(name="idProfile", nullable=false, unique=true)	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idProfile;
	
	@Column(name="codeProfile", nullable=false, unique=true, length=50)
	private String codeProfile;
	
	@Column(name="descriptionProfile", nullable=true, length=255)
	private String descriptionProfile;

	

	public void setIdProfile(int value) {
		this.idProfile = value;
	}
	
	public int getIdProfile() {
		return this.idProfile;
	}
	
	public int getORMID() {
		return getIdProfile();
	}
		
	public void setCodeProfile(String value) {
		this.codeProfile = value;
	}
	
	public String getCodeProfile() {
		return this.codeProfile;
	}

	public String getAuthority() {
		return this.codeProfile;
	}
	
	public void setDescriptionProfile(String value) {
		this.descriptionProfile = value;
	}
	
	public String getDescriptionProfile() {
		return this.descriptionProfile;
	}


	public String toString() {
		return String.valueOf(getIdProfile());
	}
	
}
