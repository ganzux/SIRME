package com.sirme.basicsecurity.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sirme.basicsecurity.SpringSecurityConstants;
import com.sirme.basicsecurity.dao.IPermissionsDao;
import com.sirme.basicsecurity.data.PermissionData;
import com.sirme.util.MyLogger;

@Repository( SpringSecurityConstants.PERMISSION_DAO )
public class PermissionsDao extends HibernateDaoSupport implements IPermissionsDao{
	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger( PermissionsDao.class );
	private static final String CLASS_NAME = "PermissionsDao";
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public PermissionsDao(){
		MyLogger.info(log, CLASS_NAME, "PermissionsDao", "New Instance");
	}
	
	@Autowired  
	public PermissionsDao(SessionFactory sessionFactory) {
		MyLogger.info( log , CLASS_NAME, "PermissionsDao", "New Instance");
		super.setSessionFactory(sessionFactory);
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////
	@Override
	public Collection<PermissionData> getAll(){
		Collection<PermissionData> permissions = null;
		permissions = getHibernateTemplate().loadAll( PermissionData.class );
		return permissions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PermissionData get(String codePermission) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from PermissionData where codePermission = '");
		sbHql.append(codePermission);
		sbHql.append("'");

		List<PermissionData> list = getHibernateTemplate().find( sbHql.toString() );
		PermissionData result = null;
		if (list.size() > 0)
			result = list.get(0);

		return result;
	}

	@Override
	public Collection<PermissionData> get(int idApplication) {
		MyLogger.info(log, CLASS_NAME, "getAllPermissions", "idApplication:[" + idApplication + "]", "START");
		
		DetachedCriteria criteriaHql = DetachedCriteria.forClass(PermissionData.class, "permission")
										.add(Restrictions.eq("permission.application.idApplication", idApplication))
										.addOrder( Order.asc("permission.descriptionPermission"));
		@SuppressWarnings("unchecked")
		List<PermissionData> list = getHibernateTemplate().findByCriteria(criteriaHql);

		return list;	
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
