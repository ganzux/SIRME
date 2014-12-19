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
@Table(name="manager")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class ManagerData implements Serializable,DataObject {
	
	private static final long serialVersionUID = 1L;
	
	public ManagerData() {}
	
	public ManagerData(int id) {
		this.idManager = id;
	}
	
	@Column(name="idManager", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idManager;
	
	@Column(name="nameManager", nullable=false, unique=false, length=50)	
	private String nameManager;
	
	@Column(name="phoneManager", nullable=true, unique=false, length=20)	
	private String phoneManager;
	
	@Column(name="mailManager", nullable=true, unique=false, length=50)	
	private String mailManager;

	@OneToMany(mappedBy="manager", targetEntity=CustomerData.class)	
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.LOCK})	
	@LazyCollection(LazyCollectionOption.TRUE)	
	private Set<CustomerData> customers = new HashSet<CustomerData>();
	
	@OneToMany(mappedBy="manager",fetch=FetchType.LAZY)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private Set<ManagerContactData> contacts;
	
	
	
	public int getIdManager() {
		return idManager;
	}
	public void setIdManager(int idManager) {
		this.idManager = idManager;
	}
	public String getNameManager() {
		return nameManager;
	}
	public void setNameManager(String nameManager) {
		this.nameManager = nameManager;
	}
	public String getPhoneManager() {
		return phoneManager;
	}
	public void setPhoneManager(String phoneManager) {
		this.phoneManager = phoneManager;
	}
	public String getMailManager() {
		return mailManager;
	}
	public Set<CustomerData> getCustomers() {
		return customers;
	}
	public void setCustomers(Set<CustomerData> customers) {
		this.customers = customers;
	}
	public void setMailManager(String mailManager) {
		this.mailManager = mailManager;
	}
	public Set<ManagerContactData> getContacts() {
		return contacts;
	}
	public void setContacts(Set<ManagerContactData> contacts) {
		this.contacts = contacts;
	}
}