package com.sirme.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sirme.dao.IOtherDao;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Repository( SpringConstants.OTHER_DAO)
public class OtherDaoImpl extends HibernateDaoSupport implements IOtherDao{

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
	//                      Métodos Públicos                     //
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
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                      Métodos Privados                     //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Privados               //
	///////////////////////////////////////////////////////////////
}
