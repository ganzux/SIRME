package com.alcedomoreno.sirme.core.data;

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
@Table(name="contact")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ContactData implements Serializable,DataObject {
	
	private static final long serialVersionUID = 1L;
	
	public ContactData() {}
	
	public ContactData(int id) {
		this.idContact = id;
	}
	
	@Column(name="idContact", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idContact;
	
	@Column(name="[nameContact]", nullable=false, unique=true, length=255)	
	private String nameContact;

	@Column(name="[dataContact]", nullable=false, unique=true, length=255)	
	private String dataContact;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idCustomer",nullable=true)
	private CustomerData customer;

	public int getIdContact() {
		return idContact;
	}

	public void setIdContact(int idContact) {
		this.idContact = idContact;
	}

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

	public CustomerData getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerData customer) {
		this.customer = customer;
	}
	
	
}