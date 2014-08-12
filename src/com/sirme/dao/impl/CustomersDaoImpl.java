package com.sirme.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sirme.dao.ICustomersDao;
import com.sirme.data.AddressData;
import com.sirme.data.CustomerData;
import com.sirme.data.ManagerData;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Repository( SpringConstants.CUSTOMER_DAO)
public class CustomersDaoImpl extends HibernateDaoSupport implements ICustomersDao{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( CustomersDaoImpl.class );
	private static final String CLASS_NAME = "CustomersDaoImpl";

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public CustomersDaoImpl(){
		MyLogger.info(log, CLASS_NAME, "CustomersDaoImpl", "New Instance");
	}
	
	@Autowired  
	public CustomersDaoImpl(SessionFactory sessionFactory) {
		MyLogger.info( log , CLASS_NAME, "CustomersDaoImpl", "New Instance");
	    super.setSessionFactory(sessionFactory);
	    
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////

	@Override
	public Collection<CustomerData> getAllCustomers() {
		MyLogger.info( log , CLASS_NAME, "getAllCustomers", "INIT");
		Collection<CustomerData> collection = getHibernateTemplate().loadAll( CustomerData.class );
		MyLogger.info( log , CLASS_NAME, "getAllCustomers", "END");
		return collection;
	}
	
	@Override
	public AddressData getAddressById( int idAddress ) {
		MyLogger.info( log , CLASS_NAME, "getAddressById", "INIT", idAddress);
		AddressData data = (AddressData)  getSessionFactory().getCurrentSession().createCriteria(AddressData.class)
				.add( Restrictions.eq("idaddress", idAddress) )
                .uniqueResult();
		MyLogger.info( log , CLASS_NAME, "getAddressById", "END", idAddress);
		return data;
	}
	
	@Override
	public Collection<ManagerData> getAllManagers() {
		MyLogger.info( log , CLASS_NAME, "getAllManagers", "INIT");
		Collection<ManagerData> collection = getHibernateTemplate().loadAll( ManagerData.class );
		MyLogger.info( log , CLASS_NAME, "getAllManagers", "END");
		return collection;
	}

	@Override
	public CustomerData getCustomer(CustomerData cd){
		MyLogger.info( log , CLASS_NAME, "getCustomer", "INIT");
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CustomerData.class)
				.setFetchMode("contacts", FetchMode.SELECT)
				.setFetchMode("manager", FetchMode.JOIN)
				.setFetchMode("commercial", FetchMode.JOIN);
		
		if (  cd.getIdCustomer() != 0 )
			criteria.add( Restrictions.idEq(cd.getIdCustomer()) );
		else if ( cd.getCodeCustomer() != null && cd.getCodeCustomer() != 0)
			criteria.add( Restrictions.eq("codeCustomer",Integer.valueOf(cd.getCodeCustomer())) );
		else if ( cd.getCifCustomer() != null && !cd.getCifCustomer().isEmpty() )
			criteria.add( Restrictions.eq("cifCustomer",cd.getCifCustomer()) );
		else
			criteria.add( Restrictions.idEq( 0 ) );
			

		List<CustomerData> css = (List<CustomerData>)criteria.list();
		CustomerData c = null;
		
		if ( css != null && !css.isEmpty() )
			c = css.get( 0 );

		MyLogger.info( log , CLASS_NAME, "getCustomer", "END");
		return c;
	}

	@Override
	public ManagerData getManager(int idManager){
		MyLogger.info( log , CLASS_NAME, "getManager", "INIT");
		
		ManagerData c = (ManagerData)  getSessionFactory().getCurrentSession().createCriteria(ManagerData.class)
				.setFetchMode("customers", FetchMode.JOIN)
                .add( Restrictions.idEq(idManager) )
                .uniqueResult();

		MyLogger.info( log , CLASS_NAME, "getManager", "END");
		return c;
	}

	@Override
	public int save(ManagerData cd){
		MyLogger.info( log , CLASS_NAME, "save", "INIT");
		getHibernateTemplate().save( cd );
		MyLogger.info( log , CLASS_NAME, "save", "END");
		return cd.getIdManager();
	}
	
	@Override
	public void save(CustomerData cd){
		MyLogger.info( log , CLASS_NAME, "save", "INIT");
		getHibernateTemplate().save( cd );
		MyLogger.info( log , CLASS_NAME, "save", "END");
	}
	
	@Override
	public void save(AddressData cd){
		MyLogger.info( log , CLASS_NAME, "save", "INIT");
		getHibernateTemplate().save( cd );
		MyLogger.info( log , CLASS_NAME, "save", "END");
	}
	
	@Override
	public void update(ManagerData cd){
		MyLogger.info( log , CLASS_NAME, "update", "INIT");
		getHibernateTemplate().update( cd );
		MyLogger.info( log , CLASS_NAME, "update", "END");
	}
	
	@Override
	public void update(AddressData cd){
		MyLogger.info( log , CLASS_NAME, "update", "INIT");
		getHibernateTemplate().update( cd );
		MyLogger.info( log , CLASS_NAME, "update", "END");
	}
	
	@Override
	public void update(CustomerData cd){
		MyLogger.info( log , CLASS_NAME, "update", "INIT");
		getHibernateTemplate().update( cd );
		MyLogger.info( log , CLASS_NAME, "update", "END");
	}

	@Override
	public int deleteCustomer(int idCustomer){
		MyLogger.info( log , CLASS_NAME, "deleteCustomer", "INIT");
		Query query = getSessionFactory().getCurrentSession().createQuery("delete CustomerData where idCustomer = :id");
		query.setParameter("id", idCustomer);
		int result = query.executeUpdate();
		MyLogger.info( log , CLASS_NAME, "deleteCustomer", "END");
		return result;
	}
	
	@Override
	public int deleteContacts(int idCustomer){
		MyLogger.info( log , CLASS_NAME, "deleteContacts", "INIT");
		Query query = getSessionFactory().getCurrentSession().createQuery("delete ContactData where idCustomer = :id");
		query.setParameter("id", idCustomer);
		int result = query.executeUpdate();
		MyLogger.info( log , CLASS_NAME, "deleteContacts", "END");
		return result;
	}

	@Override
	public int deleteManagerContacts(int idManager){
		MyLogger.info( log , CLASS_NAME, "deleteManagerContacts", "INIT");
		Query query = getSessionFactory().getCurrentSession().createQuery("delete ManagerContactData where idManager = :id");
		query.setParameter("id", idManager);
		int result = query.executeUpdate();
		MyLogger.info( log , CLASS_NAME, "deleteManagerContacts", "END");
		return result;
	}
	
	@Override
	public int deleteAddresses(int idCustomer, Collection idAddress){
		MyLogger.info( log , CLASS_NAME, "deleteAddresses", "INIT");
		Query query = getSessionFactory().getCurrentSession().createQuery("delete AddressData where idCustomer = :id and idAddress not in (:id2)");
		query.setParameter("id", idCustomer);
		query.setParameterList("id2", idAddress);
		int result = query.executeUpdate();
		MyLogger.info( log , CLASS_NAME, "deleteAddresses", "END");
		return result;
	}
	
	@Override
	public int deleteManager(int idManager){
		MyLogger.info( log , CLASS_NAME, "deleteManager", "INIT");
		Query query = getSessionFactory().getCurrentSession().createQuery("delete ManagerData where idManager = :id");
		query.setParameter("id", idManager);
		int result = query.executeUpdate();
		MyLogger.info( log , CLASS_NAME, "deleteManager", "END");
		return result;
	}
	
	@Override
	public int deleteAddress(int idCustomer){
		MyLogger.info( log , CLASS_NAME, "deleteAddress", "INIT");
		Query query = getSessionFactory().getCurrentSession().createQuery("delete AddressData where idCustomer = :id");
		query.setParameter("id", idCustomer);
		int result = query.executeUpdate();
		MyLogger.info( log , CLASS_NAME, "deleteAddress", "END");
		return result;
	}
	
	@Override
	public int deleteAddress(AddressData addressData){
		MyLogger.info( log , CLASS_NAME, "deleteAddress", "INIT");
		Query query = getSessionFactory().getCurrentSession().createQuery("delete AddressData where idAddress = :id");
		query.setParameter("id", addressData.getIdaddress());
		int result = query.executeUpdate();
		MyLogger.info( log , CLASS_NAME, "deleteAddress", "END");
		return result;
	}
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                      Métodos Privados                     //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Privados               //
	///////////////////////////////////////////////////////////////
}
