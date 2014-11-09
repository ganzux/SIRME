package com.alcedomoreno.sirme.core.dao;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.alcedomoreno.sirme.core.util.MyLogger;
import com.alcedomoreno.sirme.core.util.DAOConstants;

@Repository( DAOConstants.OTHER_DAO)
public class OtherDaoImpl extends HibernateDaoSupport implements OtherDao{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( OtherDaoImpl.class );
	private static final String CLASS_NAME = "OtherDaoImpl";

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public OtherDaoImpl(){
		MyLogger.info(log, CLASS_NAME, "OtherDaoImpl", "New Instance");
	}
	
	@Autowired  
	public OtherDaoImpl(SessionFactory sessionFactory) {
		MyLogger.info( log , CLASS_NAME, "OtherDaoImpl", "New Instance");
	    super.setSessionFactory(sessionFactory);
	    
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      M�todos P�blicos                     //
	///////////////////////////////////////////////////////////////

	
	@Override
	public int launchQuery(String query){
		MyLogger.info( log , CLASS_NAME, "launchQuery", "INIT");
		
		SQLQuery sqlQuery = getSessionFactory().getCurrentSession().createSQLQuery( query );
		int result = sqlQuery.executeUpdate();
		//getSessionFactory().getCurrentSession().getTransaction().commit();

		MyLogger.info( log , CLASS_NAME, "launchQuery", "END");
		
		return result;
	}
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los M�todos P�blicos               //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                      M�todos Privados                     //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los M�todos Privados               //
	///////////////////////////////////////////////////////////////
}
