package com.sirme.services;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.bussiness.Address;
import com.sirme.bussiness.Customer;
import com.sirme.bussiness.Manager;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;

@Transactional(readOnly=true)
public interface ICustomerService {

	public Collection<Customer> getAllCustomers();
	
	public Collection<Manager> getAllManagers();

	public Customer get( Customer customer );
	
	public Address getAddressById( int idAddress );
	
	public Manager get( Manager customer );

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void save(Customer customer) throws ValidationException, TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void delete(Customer customer) throws TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void update(Customer customer) throws ValidationException, TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public int save(Manager manager) throws ValidationException, TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void delete(Manager manager) throws TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void update(Manager manager) throws ValidationException, TransactionException;
	
	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void save(Address address) throws ValidationException, TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void delete(Address address) throws TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void update(Address address) throws ValidationException, TransactionException;

}
