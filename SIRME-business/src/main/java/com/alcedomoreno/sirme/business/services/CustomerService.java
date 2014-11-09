package com.alcedomoreno.sirme.business.services;

import java.util.Collection;

import com.alcedomoreno.sirme.business.data.Address;
import com.alcedomoreno.sirme.business.data.Customer;
import com.alcedomoreno.sirme.business.data.Manager;
import com.alcedomoreno.sirme.business.util.TransactionException;
import com.alcedomoreno.sirme.business.util.ValidationException;


public interface CustomerService {

	public Collection<Customer> getAllCustomers();
	public Collection<Manager> getAllManagers();
	public Customer get( Customer customer );
	public Address getAddressById( int idAddress );
	public Manager get( Manager customer );
	public void save(Customer customer) throws ValidationException, TransactionException;
	public void delete(Customer customer) throws TransactionException;
	public void update(Customer customer) throws ValidationException, TransactionException;
	public int save(Manager manager) throws ValidationException, TransactionException;
	public void delete(Manager manager) throws TransactionException;
	public void update(Manager manager) throws ValidationException, TransactionException;
	public void save(Address address) throws ValidationException, TransactionException;
	public void delete(Address address) throws TransactionException;
	public void update(Address address) throws ValidationException, TransactionException;

}
