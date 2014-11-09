package com.alcedomoreno.sirme.core.data;

import java.io.Serializable;
import java.util.Collection;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy=true)
@Table(name="customers")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class CustomerData implements Serializable,DataObject {
	
	private static final long serialVersionUID = 1L;
	
	public CustomerData() {}
	
	public CustomerData(int id) {
		this.idCustomer = id;
	}
	
	@Column(name="idCustomer", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idCustomer;
	
	@Column(name="[nameCustomer]", nullable=false, unique=false, length=50)	
	private String nameCustomer;

	@Column(name="[cifCustomer]", nullable=false, unique=true, length=20)	
	private String cifCustomer;
	
	@Column(name="[codeCustomer]", nullable=false, unique=true, length=20)	
	private Integer codeCustomer;
	
	@Column(name="[mainAddress]", nullable=true, unique=false, length=300)	
	private String mainAddress;
	
	@Column(name="[mainProv]", nullable=true, unique=false, length=50)	
	private String mainProv;
	
	@Column(name="[mainPobl]", nullable=true, unique=false, length=300)	
	private String mainPobl;
	
	@Column(name="[mainPostalCode]", nullable=true, unique=false, length=5)	
	private Integer mainPostalCode;
	
	@Column(name="[mainMail]", nullable=true, unique=false, length=100)	
	private String mainMail;
	
	@Column(name="[mainPhone]", nullable=true, unique=false, length=20)	
	private String mainPhone;
	
	@Column(name="[typeCustomer]", nullable=true, unique=false, length=1)	
	private Integer typeCustomer;

	@OneToMany(mappedBy="customer",fetch=FetchType.LAZY)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private Set<ContactData> contacts;
	
	@OneToMany(mappedBy="customer",fetch=FetchType.EAGER)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private Collection<AddressData> address;
	
	@ManyToOne(targetEntity=ManagerData.class,fetch=FetchType.LAZY)
	@Cascade({CascadeType.LOCK})
	@JoinColumns({ @JoinColumn(name="idManager", referencedColumnName="idManager") })	
	private ManagerData manager;
	
	@ManyToOne(targetEntity=UserData.class,fetch=FetchType.LAZY)
	@Cascade({CascadeType.LOCK})
	@JoinColumns({ @JoinColumn(name="idUser", referencedColumnName="idUser") })	
	private UserData commercial;
	
	@Column(name = "active", nullable = true, length = 1)
	private Boolean active;

	public int getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	public String getNameCustomer() {
		return nameCustomer;
	}
	public void setNameCustomer(String nameCustomer) {
		this.nameCustomer = nameCustomer;
	}
	public String getCifCustomer() {
		return cifCustomer;
	}
	public void setCifCustomer(String cifCustomer) {
		this.cifCustomer = cifCustomer;
	}
	public Integer getCodeCustomer() {
		return codeCustomer;
	}
	public void setCodeCustomer(Integer codeCustomer) {
		this.codeCustomer = codeCustomer;
	}
	public String getMainAddress() {
		return mainAddress;
	}
	public void setMainAddress(String mainAddress) {
		this.mainAddress = mainAddress;
	}
	public String getMainMail() {
		return mainMail;
	}
	public Set<ContactData> getContacts() {
		return contacts;
	}
	public void setContacts(Set<ContactData> contacts) {
		this.contacts = contacts;
	}
	public ManagerData getManager() {
		return manager;
	}
	public void setManager(ManagerData manager) {
		this.manager = manager;
	}
	public UserData getCommercial() {
		return commercial;
	}
	public void setCommercial(UserData commercial) {
		this.commercial = commercial;
	}
	public void setMainMail(String mainMail) {
		this.mainMail = mainMail;
	}
	public String getMainPhone() {
		return mainPhone;
	}
	public void setMainPhone(String mainPhone) {
		this.mainPhone = mainPhone;
	}
	public Integer getTypeCustomer() {
		return typeCustomer;
	}
	public void setTypeCustomer(Integer typeCustomer) {
		this.typeCustomer = typeCustomer;
	}
	public String getMainProv() {
		return mainProv;
	}
	public void setMainProv(String mainProv) {
		this.mainProv = mainProv;
	}
	public String getMainPobl() {
		return mainPobl;
	}
	public Collection<AddressData> getAddress() {
		return address;
	}
	public void setAddress(Collection<AddressData> address) {
		this.address = address;
	}
	public void setMainPobl(String mainPobl) {
		this.mainPobl = mainPobl;
	}
	public Integer getMainPostalCode() {
		return mainPostalCode;
	}
	public void setMainPostalCode(Integer mainPostalCode) {
		this.mainPostalCode = mainPostalCode;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
}