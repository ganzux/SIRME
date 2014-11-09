package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.alcedomoreno.sirme.core.data.ProfileData;
import com.alcedomoreno.sirme.core.util.DAOConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Repository( DAOConstants.PROFILE_DAO )
public class ProfilesDaoImpl extends HibernateDaoSupport implements ProfilesDao{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger( ProfilesDaoImpl.class );
	private static final String CLASS_NAME = "ProfilesDaoImpl";
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public ProfilesDaoImpl(){
		MyLogger.info(log, CLASS_NAME, "ProfilesDaoImpl", "New Instance");
	}
	
	@Autowired  
	public ProfilesDaoImpl(SessionFactory sessionFactory) {
		MyLogger.info( log , CLASS_NAME, "ProfilesDaoImpl", "New Instance");
		super.setSessionFactory(sessionFactory);
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      M�todos P�blicos                     //
	///////////////////////////////////////////////////////////////
	
	@Override
	public void delete(int idProfile) {
		
		MyLogger.info(log, CLASS_NAME, "delete", "profile="+idProfile, "START");	
		
		ProfileData data = getHibernateTemplate().get(ProfileData.class, idProfile);		
		getHibernateTemplate().delete(data);
		
		MyLogger.info(log, CLASS_NAME, "delete", "user="+idProfile, "END");
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ProfileData getByCode(String codeProfile) {
		MyLogger.info(log, CLASS_NAME, "getByCode", "codeProfile="+codeProfile, "START");
		StringBuilder sbHql = new StringBuilder();
			sbHql.append("from ProfileData where codeProfile = '");
			sbHql.append(codeProfile);
			sbHql.append("'");
			
		List<ProfileData> list = getHibernateTemplate().find(sbHql.toString());
		ProfileData result = null;
		if (list.size() > 0) {
			result = list.get(0);
		}
		MyLogger.info(log, CLASS_NAME, "getByCode", "codeProfile="+codeProfile, "END");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProfileData get(int idProfile) {
		MyLogger.info(log, CLASS_NAME, "get", "idProfile="+idProfile, "START");
		
		String query = "from ProfileData pro "+		
		"where pro.idProfile = ?";

			
		List<ProfileData> list = getHibernateTemplate().find(query, new Object[]{idProfile});
		ProfileData result = null;
		if (list.size() > 0) {
			result = list.get(0);
		}
		MyLogger.info(log, CLASS_NAME, "get", "idProfile="+idProfile, "END");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ProfileData> getAll() {
		
		MyLogger.info(log, CLASS_NAME, "getAll", "", "START");

		String query = "from ProfileData profile"+    
        " where profile.idProfile <> 1";   
		
		Collection<ProfileData> data = getHibernateTemplate().find(query);
		
		MyLogger.info(log, CLASS_NAME, "getAll", "", "END");
		
		return data;

	}

	@Override
	public void save(ProfileData profile) {
		
		MyLogger.info(log, CLASS_NAME, "save", "profile="+profile.getIdProfile(), "START");		
				
			getHibernateTemplate().getSessionFactory().getCurrentSession().clear();
			getHibernateTemplate().saveOrUpdate(profile);
			getHibernateTemplate().getSessionFactory().getCurrentSession().flush();

		MyLogger.info(log, CLASS_NAME, "save", "profile="+profile.getIdProfile(), "END");
		
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
