package com.sirme.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
@Entity
@Proxy(lazy=true)
@Table(name="managercontact")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ManagerContactData implements Serializable,IDataObject {
	
	private static final long serialVersionUID = 1L;
	
	public ManagerContactData() {}
	
	public ManagerContactData(int id) {
		this.idManagerContact = id;
	}
	
	@Column(name="idManagerContact", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idManagerContact;
	
	@Column(name="[nameContact]", nullable=false, unique=true, length=255)	
	private String nameContact;

	@Column(name="[dataContact]", nullable=false, unique=true, length=255)	
	private String dataContact;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idManager",nullable=true)
	private ManagerData manager;



	public String getNameContact() {
		return nameContact;
	}
	public void setNameContact(String nameContact) {
		this.nameContact = nameContact;
	}
	public String getDataContact() {
		return dataContact;
	}
	public void setDataContact(String dataContact) {
		this.dataContact = dataContact;
	}
	public int getIdManagerContact() {
		return idManagerContact;
	}
	public void setIdManagerContact(int idManagerContact) {
		this.idManagerContact = idManagerContact;
	}
	public ManagerData getManager() {
		return manager;
	}
	public void setManager(ManagerData manager) {
		this.manager = manager;
	}
}