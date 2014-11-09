package com.alcedomoreno.sirme.business.data;

import com.alcedomoreno.sirme.business.transform.ContactTransform;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class Contact implements BusinessObject,Cloneable{

	private int idContact;
	private String nameContact;
	private String dataContact;
	private Customer customer;
	
	


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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public Class<? extends Transformator> getTransformator() {
		return ContactTransform.class;
	}
	
}
