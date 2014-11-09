package com.alcedomoreno.sirme.business.data;

import com.alcedomoreno.sirme.business.transform.ContactManagerTransform;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class ManagerContact implements BusinessObject,Cloneable{

	private int idContact;
	private String nameContact;
	private String dataContact;
	private Manager manager;
	
	


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


	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	@Override
	public Class<? extends Transformator> getTransformator() {
		return ContactManagerTransform.class;
	}
	
}
