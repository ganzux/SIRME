package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;

import com.alcedomoreno.sirme.core.data.AddressData;
import com.alcedomoreno.sirme.core.data.CustomerData;
import com.alcedomoreno.sirme.core.data.ManagerData;

public interface CustomersDao {
	
	public Collection<CustomerData> getAllCustomers();
	
	public Collection<ManagerData> getAllManagers();
	
	public AddressData getAddressById( int idAddress );
	
	public CustomerData getCustomer(int idCustomer);
	
	public CustomerData getCustomer(String codeCustomer);
	
	public ManagerData getManager(int idManager);
	
	public void save(CustomerData cd);
	
	public int save(ManagerData cd);
	
	public void save(AddressData cd);

	public void update(CustomerData cd);

	public void update(AddressData cd);
	
	public void update(ManagerData cd);
	
	public int deleteCustomer(int idCustomer);

	public int deleteContacts(int idCustomer);
	
	public int deleteManagerContacts(int idManager);
	
	public int deleteAddresses(int idCustomer, Collection idAddress);
	
	public int deleteManager(int idManager);
	
	public int deleteAddress(int idCustomer);
	
	public int deleteAddress(AddressData addressData);

	CustomerData getCustomer(CustomerData cd);

}