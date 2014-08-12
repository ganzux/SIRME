package com.sirme.basicsecurity.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sirme.basicsecurity.SpringSecurityConstants;
import com.sirme.basicsecurity.dao.IRolesDao;
import com.sirme.basicsecurity.data.RoleData;
import com.sirme.util.MyLogger;

@Repository( SpringSecurityConstants.ROLE_DAO )
public class RolesDao extends HibernateDaoSupport implements IRolesDao{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger( RolesDao.class );
	private static final String CLASS_NAME = "RolesDao";
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public RolesDao(){
		MyLogger.info(log, CLASS_NAME, "RolesDao", "New Instance");
	}
	
	@Autowired  
	public RolesDao(SessionFactory sessionFactory) {
		MyLogger.info( log , CLASS_NAME, "RolesDao", "New Instance");
		super.setSessionFactory(sessionFactory);
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
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
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////
	
	
	///////////////////////////////////////////////////////////////
	//                      Métodos Privados                     //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Privados               //
	///////////////////////////////////////////////////////////////
}
