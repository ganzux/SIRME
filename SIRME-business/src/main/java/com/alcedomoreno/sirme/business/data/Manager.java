package com.alcedomoreno.sirme.business.data;

import java.util.ArrayList;
import java.util.Collection;

import com.alcedomoreno.sirme.business.transform.ManagerTransform;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class Manager implements BusinessObject,Cloneable{

	private int idManager;
	private String nameManager;
	private String phoneManager;
	private String mailManager;
	private Collection<Customer> customers;
	private Collection<ManagerContact> contacts;


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

	public void setMailManager(String mailManager) {
		this.mailManager = mailManager;
	}

	public Collection<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Collection<Customer> customers) {
		this.customers = customers;
	}
	
	public void addCustomer(Customer customer) {
		if ( getCustomers() == null  )
			setCustomers( new ArrayList<Customer>() );
		customers.add( customer );
	}
	public Collection<ManagerContact> getContacts() {
		return contacts;
	}

	public void setContacts(Collection<ManagerContact> contacts) {
		this.contacts = contacts;
	}
	public void addContact(String nameContact, String dataContact) {
		ManagerContact contact = new ManagerContact();
		
		contact.setDataContact( dataContact );
		contact.setIdContact( 0 );
		contact.setManager( this );
		contact.setNameContact( nameContact );

		if ( contacts == null )
			contacts = new ArrayList<ManagerContact>();
		contacts.add( contact );
	}

	@Override
	public Class<? extends Transformator> getTransformator() {
		return ManagerTransform.class;
	}
	
}
