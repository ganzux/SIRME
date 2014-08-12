package com.sirme.bussiness;

import com.sirme.transform.ContactManagerTransform;
import com.sirme.transform.ITransformator;

public class ManagerContact implements IBusinessObject,Cloneable{

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
	public Class<? extends ITransformator> getTransformator() {
		return ContactManagerTransform.class;
	}
	
}
