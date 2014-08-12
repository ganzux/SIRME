package com.sirme.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sirme.basicsecurity.business.data.User;
import com.sirme.basicsecurity.data.UserData;
import com.sirme.bussiness.Address;
import com.sirme.bussiness.Contact;
import com.sirme.bussiness.Customer;
import com.sirme.bussiness.Manager;
import com.sirme.bussiness.ManagerContact;
import com.sirme.dao.ICustomersDao;
import com.sirme.data.AddressData;
import com.sirme.data.ContactData;
import com.sirme.data.CustomerData;
import com.sirme.data.ManagerContactData;
import com.sirme.data.ManagerData;
import com.sirme.services.ICustomerService;
import com.sirme.transform.TransformFactory;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;

@Service( SpringConstants.CUSTOMER_SERVICE)
public class CustomerServiceImpl implements ICustomerService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( CustomerServiceImpl.class );
	private static final String CLASS_NAME = "CustomerServiceImpl";
	
	@Resource( name=SpringConstants.CUSTOMER_DAO )
	protected ICustomersDao customersDao;

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public CustomerServiceImpl(){
		MyLogger.info(log, CLASS_NAME, "CustomerServiceImpl", "New Instance");
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////

	@Override
	public Collection<Customer> getAllCustomers() {
		MyLogger.info(log, CLASS_NAME, "getAllCustomers", "IN");
		
		Collection<CustomerData> datas = customersDao.getAllCustomers();
		Collection<Customer> dummies = new ArrayList<Customer>();
		
		for ( CustomerData cd: datas ){
			Customer c = (Customer) TransformFactory.getTransformator(Customer.class).toBusinessObject( cd );

			if ( cd.getAddress() != null )
				c.setAddress( (Collection<Address>) TransformFactory.getTransformator(Address.class).toBusinessObject( cd.getAddress() ) );

			dummies.add( c );
		}
		
		MyLogger.info(log, CLASS_NAME, "getAllCustomers", "OUT");

		return dummies;
	}
	
	public Address getAddressById( int idAddress ){
		MyLogger.info(log, CLASS_NAME, "getAddressById", "IN", idAddress);

		Address address = null;
		AddressData data = customersDao.getAddressById( idAddress );

		if ( data != null )
			address = (Address) TransformFactory.getTransformator(Address.class).toBusinessObject( data );

		MyLogger.info(log, CLASS_NAME, "getAddressById", "OUT", idAddress);

		return address;
	}
	
	@Override
	public Collection<Manager> getAllManagers() {
		MyLogger.info(log, CLASS_NAME, "getAllManagers", "IN");
		
		Collection<Manager> dummies = null;
		dummies = (Collection<Manager>) TransformFactory.getTransformator(Manager.class).toBusinessObject( customersDao.getAllManagers() );
		
		MyLogger.info(log, CLASS_NAME, "getAllManagers", "OUT");

		return dummies;
	}

	@Override
	public Customer get( Customer customer ){
		MyLogger.info(log, CLASS_NAME, "get", "IN", customer);
		
		CustomerData cd = customersDao.getCustomer( (CustomerData)TransformFactory.getTransformator(Customer.class).toDataObject( customer ) );

		Customer c = null;
		if ( cd != null ){
			c = (Customer) TransformFactory.getTransformator(Customer.class).toBusinessObject( cd );
			if( cd.getContacts() != null )
				c.setContacts( (Collection<Contact>) TransformFactory.getTransformator(Contact.class).toBusinessObject( cd.getContacts() ) );

			if( cd.getCommercial() != null )
				c.setCommercial( (User) TransformFactory.getTransformator(User.class).toBusinessObject( cd.getCommercial() ) );
			
			if( cd.getManager() != null )
				c.setManager( (Manager) TransformFactory.getTransformator(Manager.class).toBusinessObject( cd.getManager() ) );
			
			if ( cd.getAddress() != null )
				c.setAddress( (Collection<Address>) TransformFactory.getTransformator(Address.class).toBusinessObject( cd.getAddress() ) );
		}
		
		MyLogger.info(log, CLASS_NAME, "get", "OUT", customer);

		return c;
	}
	
	public Manager get( Manager manager ){
		MyLogger.info(log, CLASS_NAME, "get", "IN", manager);
		
		ManagerData cd = customersDao.getManager( manager.getIdManager() );
		Manager c = null;
		if ( cd != null ){
			c = (Manager) TransformFactory.getTransformator(Manager.class).toBusinessObject( cd );
			if( cd.getCustomers() != null )
				c.setCustomers( (Collection<Customer>) TransformFactory.getTransformator(Customer.class).toBusinessObject( cd.getCustomers() ) );
			if ( cd.getContacts() != null )
				c.setContacts( (Collection<ManagerContact>) TransformFactory.getTransformator(ManagerContact.class).toBusinessObject( cd.getContacts() ) );
		}
		
		MyLogger.info(log, CLASS_NAME, "get", "OUT", manager);

		return c;
	}

	@Override
	public void save(Customer customer) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "save", "IN", customer);

		validateCustomer( customer );
		
		try{
			CustomerData cd = getCustomerData( customer );
			customersDao.save( cd );
		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "save", ((org.hibernate.exception.ConstraintViolationException)e.getCause()).getCause().getMessage() );
			throw new TransactionException( e );
		}

		MyLogger.info(log, CLASS_NAME, "save", "OUT", customer);
	}

	@Override
	public void delete(Customer customer) throws TransactionException{
		MyLogger.info(log, CLASS_NAME, "delete", "IN", customer);

		try{
			customersDao.deleteContacts( customer.getIdCustomer() );
			customersDao.deleteAddress( customer.getIdCustomer() );
			customersDao.deleteCustomer( customer.getIdCustomer() );
		} catch (Exception e){
			throw new TransactionException(e);
		}
		MyLogger.info(log, CLASS_NAME, "delete", "OUT", customer);
	}
	
	public void update(Manager manager) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "delete", "IN", manager);

		validateManager( manager );

		try{
			ManagerData cd = getManagerData( manager );
			customersDao.deleteManagerContacts( cd.getIdManager() );
			customersDao.update( getManagerData( manager ) );
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "delete", "OUT", manager);
	}
	
	@Override
	public int save(Manager manager) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "save", "IN", manager);
		
		int id = 0;

		validateManager( manager );
		
		try{
			id = customersDao.save( getManagerData( manager ) );
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "save", "OUT", manager);
		
		return id;
	}

	@Override
	public void delete(Manager manager) throws TransactionException{
		MyLogger.info(log, CLASS_NAME, "delete", "IN", manager);

		try{
			customersDao.deleteManagerContacts( manager.getIdManager() );
			customersDao.deleteManager( manager.getIdManager() );
		} catch (Exception e){
			throw new TransactionException(e);
		}
		MyLogger.info(log, CLASS_NAME, "delete", "OUT", manager);
	}
	
	@Override
	public void update(Customer customer) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "delete", "IN", customer);

		validateCustomer( customer );

		try{
			CustomerData cd = getCustomerData( customer );
			customersDao.deleteContacts( cd.getIdCustomer() );
			
			Collection<Integer> actualIdAddress = new ArrayList<Integer>();
			if ( cd.getAddress()!=null ){
				for ( AddressData ad : cd.getAddress() )
					actualIdAddress.add( ad.getIdaddress() );
			} else
				actualIdAddress.add( 0 );
			customersDao.deleteAddresses( cd.getIdCustomer(),actualIdAddress );

			customersDao.update( cd );
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "delete", "OUT", customer);
	}
	
	@Override
	public void save(Address address) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "save", "IN", address);
		
		validateAddress(address);
		
		try{
			AddressData cd = (AddressData)TransformFactory.getTransformator(Address.class).toDataObject( address );
			cd.setCustomer( (CustomerData)TransformFactory.getTransformator(Customer.class).toDataObject( address.getCustomer() ) );

			customersDao.save( cd );
		} catch (Exception e){
			throw new TransactionException(e);
		}
		
		MyLogger.info(log, CLASS_NAME, "save", "OUT", address);
	}

	@Override
	public void delete(Address address) throws TransactionException{
		MyLogger.info(log, CLASS_NAME, "delete", "IN", address);

		try{
			customersDao.deleteAddress( (AddressData)TransformFactory.getTransformator(Address.class).toDataObject( address ) );
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "delete", "OUT", address);
	}

	@Override
	public void update(Address address) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "update", "IN", address);
		
		validateAddress(address);

		try{
			AddressData cd = (AddressData)TransformFactory.getTransformator(Address.class).toDataObject( address );
			cd.setCustomer( (CustomerData)TransformFactory.getTransformator(Customer.class).toDataObject( address.getCustomer() ) );

			customersDao.update( cd );
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "update", "OUT", address);
	}
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                      Métodos Privados                     //
	///////////////////////////////////////////////////////////////
	private CustomerData getCustomerData( Customer customer){
		CustomerData cd = (CustomerData)TransformFactory.getTransformator(Customer.class).toDataObject( customer );

		if( customer.getContacts()!=null && !customer.getContacts().isEmpty() ){
			Set<ContactData> contactsData = new HashSet<ContactData>();
			for ( Contact c:customer.getContacts()){
				ContactData contactD = (ContactData)TransformFactory.getTransformator(Contact.class).toDataObject( c );
				contactD.setCustomer( cd );
				contactD.setIdContact( 0 );
				contactsData.add( contactD );
			}
			cd.setContacts( contactsData );
		}
		
		if( customer.getAddress()!=null && !customer.getAddress().isEmpty() ){
			Collection<AddressData> addressData = new ArrayList<AddressData>();
			for ( Address a:customer.getAddress()){
				AddressData add = (AddressData)TransformFactory.getTransformator(Address.class).toDataObject( a );
				add.setCustomer( cd );
				addressData.add( add );
			}
			cd.setAddress( addressData );
		}
		
		if ( customer.getManager() != null )
			cd.setManager( (ManagerData)TransformFactory.getTransformator(Manager.class).toDataObject( customer.getManager() ) );
		
		if ( customer.getCommercial() != null )
			cd.setCommercial( (UserData)TransformFactory.getTransformator(User.class).toDataObject( customer.getCommercial() ) );

		return cd;
	}
	
	private ManagerData getManagerData( Manager manager){
		ManagerData cd = (ManagerData)TransformFactory.getTransformator(Manager.class).toDataObject( manager );

		if( manager.getContacts()!=null && !manager.getContacts().isEmpty() ){
			Set<ManagerContactData> contactsData = new HashSet<ManagerContactData>();
			for ( ManagerContact c:manager.getContacts()){
				ManagerContactData contactD = (ManagerContactData)TransformFactory.getTransformator(ManagerContact.class).toDataObject( c );
				contactD.setManager( cd );
				contactD.setIdManagerContact( 0 );
				contactsData.add( contactD );
			}
			cd.setContacts( contactsData );
		}
		
		return cd;
	}
	
	private void validateCustomer( Customer customer ) throws ValidationException{
		if ( customer==null || customer.getNameCustomer()==null || customer.getNameCustomer().trim().isEmpty() )
			throw new ValidationException("Nombre del cliente vacío");
		if ( customer.getCifCustomer()==null || customer.getCifCustomer().trim().isEmpty() )
			throw new ValidationException("CIF del cliente vacío");
		if ( customer.getCodeCustomer()==null || customer.getCodeCustomer().trim().isEmpty() )
			throw new ValidationException("Código de cliente vacío");
		
		if ( customer.getMainPostalCode()!=null && !customer.getMainPostalCode().trim().isEmpty() )
			try{ Integer.parseInt( customer.getMainPostalCode() ); }
			catch ( Exception e) { throw new ValidationException("Código Postal no válido"); }
		
		if ( customer.getServicePostalCode()!=null && !customer.getServicePostalCode().trim().isEmpty() )
			try{ Integer.parseInt( customer.getServicePostalCode() ); }
			catch ( Exception e) { throw new ValidationException("Código Postal del Servicio no válido"); }
		
		try{
			Integer.parseInt( customer.getCodeCustomer() );
		} catch ( Exception e ){
			throw new ValidationException("Código de cliente ha de ser numérico");
		}
	}
	
	private void validateManager( Manager manager ) throws ValidationException{
		if ( manager==null || manager.getNameManager()==null || manager.getNameManager().trim().isEmpty() )
			throw new ValidationException("Nombre del gestor vacío");
	}
	
	private void validateAddress( Address address ) throws ValidationException{
		if ( address == null || address.getMainAddress() == null || address.getMainAddress().trim().isEmpty() )
			throw new ValidationException("Campo Dirección Vacío");
		if ( address.getCustomer() == null )
			throw new ValidationException("Campo Cliente Vacío");
		if ( address.getMainPostalCode()!=null && !address.getMainPostalCode().trim().isEmpty() )
			try{ Integer.parseInt( address.getMainPostalCode() ); }
			catch ( Exception e) { throw new ValidationException("Código Postal no válido"); }
	}
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Privados               //
	///////////////////////////////////////////////////////////////
}
