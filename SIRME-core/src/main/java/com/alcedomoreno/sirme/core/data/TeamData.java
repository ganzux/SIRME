package com.alcedomoreno.sirme.core.data;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy=true)
@Table(name="team")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class TeamData implements Serializable,DataObject {
	
	private static final long serialVersionUID = 1L;
	
	public TeamData() {}
	
	public TeamData(int id) {
		this.idTeam = id;
	}
	
	@Column(name="idTeam", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idTeam;
	
	@Column(name="[nameTeam]", nullable=false, unique=true, length=50)	
	private String nameTeam;

	@Column(name="[phoneNumber]", nullable=false, unique=true, length=20)	
	private String phoneNumber;
	
	@Column(name="[passWord]", nullable=false, unique=true, length=30)	
	private String passWord;
	
	@Column(name = "enabled", nullable = false, length = 1)
	private boolean enabled;
	
	@Column(name = "canUploadPhotos", nullable = false, length = 1)
	private boolean canUploadPhotos;
	
	@ManyToMany(targetEntity=UserData.class)
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinTable(name = "team_user", joinColumns = @JoinColumn(name = "idTeam"), inverseJoinColumns = @JoinColumn(name = "idUser"))
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.FALSE)	
	public Set<UserData> users = new LinkedHashSet<UserData>();

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}

	public String getNameTeam() {
		return nameTeam;
	}

	public void setNameTeam(String nameTeam) {
		this.nameTeam = nameTeam;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Set<UserData> getUsers() {
		return users;
	}

	public void setUsers(Set<UserData> users) {
		this.users = users;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isCanUploadPhotos() {
		return canUploadPhotos;
	}

	public void setCanUploadPhotos(boolean canUploadPhotos) {
		this.canUploadPhotos = canUploadPhotos;
	}

	
	
}