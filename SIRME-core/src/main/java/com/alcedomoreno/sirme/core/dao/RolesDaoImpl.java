package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.alcedomoreno.sirme.core.data.RoleData;
import com.alcedomoreno.sirme.core.util.DAOConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Repository( DAOConstants.ROLE_DAO )
public class RolesDaoImpl extends HibernateDaoSupport implements RolesDao{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger( RolesDaoImpl.class );
	private static final String CLASS_NAME = "RolesDaoImpl";
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public RolesDaoImpl(){
		MyLogger.info(log, CLASS_NAME, "RolesDaoImpl", "New Instance");
	}
	
	@Autowired  
	public RolesDaoImpl(SessionFactory sessionFactory) {
		MyLogger.info( log , CLASS_NAME, "RolesDaoImpl", "New Instance");
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
		
		RoleData data = getHibernateTemplate().get(RoleData.class, idProfile);		
		getHibernateTemplate().delete(data);
		
		MyLogger.info(log, CLASS_NAME, "delete", "user="+idProfile, "END");
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public RoleData getByCode(String codeProfile) {
		MyLogger.info(log, CLASS_NAME, "getByCode", "codeProfile="+codeProfile, "START");
		StringBuilder sbHql = new StringBuilder();
			sbHql.append("from RoleData where codeRole = '");
			sbHql.append(codeProfile);
			sbHql.append("'");
			
		List<RoleData> list = getHibernateTemplate().find(sbHql.toString());
		RoleData result = null;
		if (list.size() > 0) {
			result = list.get(0);
		}
		MyLogger.info(log, CLASS_NAME, "getByCode", "codeProfile="+codeProfile, "END");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RoleData getWithPermissions(int idProfile) {
		MyLogger.info(log, CLASS_NAME, "getWithPermissions", "idProfile="+idProfile, "START");
		
		String query = "from RoleData pro "+
		"left outer join fetch pro.permissions per "+
		"inner join fetch per.application app "+
		"where pro.idProfile = ?";

			
		List<RoleData> list = getHibernateTemplate().find(query, new Object[]{idProfile});
		RoleData result = null;
		if (list.size() > 0) {
			result = list.get(0);
		}
		MyLogger.info(log, CLASS_NAME, "getWithPermissions", "idProfile="+idProfile, "END");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RoleData get(int idProfile) {
		MyLogger.info(log, CLASS_NAME, "getProfile", "idProfile="+idProfile, "START");
		
		String query = "from RoleData pro "+		
		"where pro.idProfile = ?";

			
		List<RoleData> list = getHibernateTemplate().find(query, new Object[]{idProfile});
		RoleData result = null;
		if (list.size() > 0) {
			result = list.get(0);
		}
		MyLogger.info(log, CLASS_NAME, "getByCode", "idProfile="+idProfile, "END");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<RoleData> getAll() {
		
		MyLogger.info(log, CLASS_NAME, "getAll", "", "START");

		String query = "from RoleData role";   
		
		Collection<RoleData> data = getHibernateTemplate().find(query);
		
		MyLogger.info(log, CLASS_NAME, "getAll", "", "END");
		
		return data;

	}

	@Override
	public void save(RoleData profile) {
		
		MyLogger.info(log, CLASS_NAME, "save", "profile="+profile.getIdRole(), "START");		
				
			getHibernateTemplate().getSessionFactory().getCurrentSession().clear();
			getHibernateTemplate().saveOrUpdate(profile);
			getHibernateTemplate().getSessionFactory().getCurrentSession().flush();

		MyLogger.info(log, CLASS_NAME, "save", "profile="+profile.getIdRole(), "END");
		
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
