package com.sirme.bussiness;

import com.sirme.transform.ContactTransform;
import com.sirme.transform.ITransformator;

public class Contact implements IBusinessObject,Cloneable{

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
	public Class<? extends ITransformator> getTransformator() {
		return ContactTransform.class;
	}
	
}
