package com.sirme.data;

import java.io.Serializable;
import java.util.Collection;

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
@Table(name="address")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class AddressData implements Serializable,IDataObject {
	
	private static final long serialVersionUID = 1L;
	
	public AddressData() {}
	
	public AddressData(int id) {
		this.idaddress = id;
	}
	
	@Column(name="idaddress", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idaddress;
	
	@Column(name="[address]", nullable=false, unique=false, length=300)	
	private String address;
	
	@Column(name="[location]", nullable=true, unique=false, length=120)	
	private String location;

	@Column(name="[prov]", nullable=true, unique=true, length=50)	
	private String prov;
	
	@Column(name="[pobl]", nullable=true, unique=false, length=300)	
	private String pobl;
	
	@Column(name="[postalCode]", nullable=true, unique=false, length=5)	
	private Integer postalCode;
	
	@Column(name="[other]", nullable=true, unique=false, length=300)	
	private String other;
	
	@Column(name="[monthMask]", nullable=true, unique=false, length=20)	
	private String monthMask;
	
	@Column(name="[amount]", nullable=true, unique=false, length=20)	
	private String amount;
	
	@ManyToOne(targetEntity=CustomerData.class,fetch=FetchType.LAZY)
	@Cascade({CascadeType.LOCK})
	@JoinColumns({ @JoinColumn(name="idCustomer", referencedColumnName="idCustomer") })	
	private CustomerData customer;
	
	@OneToMany(mappedBy="address",fetch=FetchType.LAZY)
	@Cascade({CascadeType.LOCK})
    private Collection<WorkData> works;

	public int getIdaddress() {
		return idaddress;
	}
	public void setIdaddress(int idaddress) {
		this.idaddress = idaddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public CustomerData getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerData customer) {
		this.customer = customer;
	}
	public String getPobl() {
		return pobl;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setPobl(String pobl) {
		this.pobl = pobl;
	}
	public Integer getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMonthMask() {
		return monthMask;
	}
	public void setMonthMask(String monthMask) {
		this.monthMask = monthMask;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public Collection<WorkData> getWorks() {
		return works;
	}
	public void setWorks(Collection<WorkData> works) {
		this.works = works;
	}

}