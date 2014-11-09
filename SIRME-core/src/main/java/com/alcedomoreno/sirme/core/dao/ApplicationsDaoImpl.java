package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.alcedomoreno.sirme.core.data.ApplicationData;
import com.alcedomoreno.sirme.core.util.DAOConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;


@Repository( DAOConstants.APPLICATION_DAO )
public class ApplicationsDaoImpl extends HibernateDaoSupport implements ApplicationsDao{
	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger( ApplicationsDaoImpl.class );
	private static final String CLASS_NAME = "ApplicationsDaoImpl";

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public ApplicationsDaoImpl(){
		MyLogger.info(log, CLASS_NAME, "ApplicationsDaoImpl", "New Instance");
	}
	
	@Autowired  
	public ApplicationsDaoImpl(SessionFactory sessionFactory) {
		MyLogger.info( log , CLASS_NAME, "ApplicationsDaoImpl", "New Instance");
	    super.setSessionFactory(sessionFactory);
	    
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      M�todos P�blicos                     //
	///////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationData getRootApplication() {	
		MyLogger.info(log, CLASS_NAME,"getRootApplication()","","START");
		
		DetachedCriteria criteria = 
			DetachedCriteria.forClass(ApplicationData.class)
				.add(Restrictions.eq("levelApplication", ApplicationData.ROOT_LEVEL));
		
		ApplicationData application = (ApplicationData)DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

		MyLogger.info(log, CLASS_NAME,"getRootApplication()","","END");	
		
		return application;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ApplicationData> getChildApplications(int idApplication) {
		MyLogger.info(log, CLASS_NAME,"getChildApplications()","","START");
		
		DetachedCriteria criteria = 
			DetachedCriteria.forClass(ApplicationData.class)
				.add(Restrictions.eq("mainApplication.idApplication", idApplication));
		
		Collection<ApplicationData> application = getHibernateTemplate().findByCriteria(criteria);

		MyLogger.info(log, CLASS_NAME,"getChildApplications()","","END");	
		
		return application;
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