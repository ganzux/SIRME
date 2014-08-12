package com.sirme.basicsecurity.dao.impl;

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

import com.sirme.basicsecurity.SpringSecurityConstants;
import com.sirme.basicsecurity.business.data.Application;
import com.sirme.basicsecurity.dao.IApplicationsDao;
import com.sirme.basicsecurity.data.ApplicationData;
import com.sirme.util.MyLogger;

@Repository( SpringSecurityConstants.APPLICATION_DAO )
public class ApplicationsDao extends HibernateDaoSupport implements IApplicationsDao{
	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger( ApplicationsDao.class );
	private static final String CLASS_NAME = "ApplicationsDao";

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public ApplicationsDao(){
		MyLogger.info(log, CLASS_NAME, "ApplicationsDao", "New Instance");
	}
	
	@Autowired  
	public ApplicationsDao(SessionFactory sessionFactory) {
		MyLogger.info( log , CLASS_NAME, "ApplicationsDao", "New Instance");
	    super.setSessionFactory(sessionFactory);
	    
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationData getRootApplication() {	
		MyLogger.info(log, CLASS_NAME,"getRootApplication()","","START");
		
		DetachedCriteria criteria = 
			DetachedCriteria.forClass(ApplicationData.class)
				.add(Restrictions.eq("levelApplication", Application.ROOT_LEVEL));
		
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
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////
	
	
	///////////////////////////////////////////////////////////////
	//                      Métodos Privados                     //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Privados               //
	///////////////////////////////////////////////////////////////
}