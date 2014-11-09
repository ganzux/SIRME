package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.alcedomoreno.sirme.core.data.UserData;
import com.alcedomoreno.sirme.core.util.DAOConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Repository( DAOConstants.USER_DAO )
public class UsersDaoImpl extends HibernateDaoSupport implements UsersDao{
	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger( UsersDaoImpl.class );
	private static final String CLASS_NAME = "UsersDaoImpl";
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public UsersDaoImpl(){
		MyLogger.info(log, CLASS_NAME, "UsersDaoImpl", "New Instance");
	}
	
	@Autowired  
	public UsersDaoImpl(SessionFactory sessionFactory) {
		MyLogger.info( log , CLASS_NAME, "UsersDaoImpl", "New Instance");
		super.setSessionFactory(sessionFactory);
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      M�todos P�blicos                     //
	///////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public UserData getByCode(String codeUser) {
		MyLogger.info(log,CLASS_NAME, "getByCodeUser", "codeUser="+codeUser, "START");
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from UserData where codeUser = '");
		sbHql.append(codeUser);
		sbHql.append("'");
			
		List<UserData> list = getHibernateTemplate().find(sbHql.toString());
		UserData result = null;
		if (list.size() > 0) {
			result = list.get(0);
		}
		MyLogger.info(log, CLASS_NAME, "getByCodeUser", "codeUser="+codeUser, "END");
		return result;
	}

	public UserData getByCodeUserLoadAllData(String codeUser) {

		MyLogger.info(log, CLASS_NAME, "getByCodeUserLoadAllData", "codeUser="+codeUser, "START");

		Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createCriteria(UserData.class, "user")
				.add( Restrictions.eq("user.codeUser", codeUser) )
				.createAlias("user.profiles", "profile", Criteria.LEFT_JOIN)
				.createAlias("user.roles", "role", Criteria.LEFT_JOIN)
				.createAlias("role.permissions", "permission", Criteria.LEFT_JOIN)
				.setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY );
		
		UserData data = (UserData) DataAccessUtils.uniqueResult( criteria.list() );

		MyLogger.info(log, CLASS_NAME, "getByCodeUser", "codeUser="+codeUser, "END");
		return data;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<UserData> getAll(){
		MyLogger.info(log, CLASS_NAME, "getAll", "", "START");

		
		String query = "from UserData user";   
		
		Collection<UserData> data = getHibernateTemplate().find(query);
		
		
		MyLogger.info(log, CLASS_NAME, "getAll", "", "END");
		
		return data;
	}
	
	@Override
	public void delete(int idUser) {
		MyLogger.info(log, CLASS_NAME, "delete", "user="+idUser, "START");	
		
		UserData f = ( UserData ) getHibernateTemplate().getSessionFactory().getCurrentSession().get(UserData.class, idUser);
		f.getRoles().clear();
		f.getProfiles().clear();
		getHibernateTemplate().getSessionFactory().getCurrentSession().merge( f );
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
		getHibernateTemplate().getSessionFactory().getCurrentSession().clear();

		String hql = "DELETE UserData WHERE idUser = :id";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery( hql );
		query.setParameter("id", idUser);
		int result = query.executeUpdate();
		getHibernateTemplate().getSessionFactory().getCurrentSession().clear();

		MyLogger.info(log, CLASS_NAME, "delete", "user="+idUser, "END", result);
	}

	@Override
	public void save(UserData user) {
		
		MyLogger.info(log, CLASS_NAME, "save", "user="+user.getIdUser(), "START");	
		
		getHibernateTemplate().getSessionFactory().getCurrentSession().clear();
		 
		getHibernateTemplate().saveOrUpdate(user);
		
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
		
		MyLogger.info(log, CLASS_NAME, "save", "user="+user.getIdUser(), "END");
		
	}
	
	@Override
	public void update(UserData user) {
		
		MyLogger.info(log, CLASS_NAME, "update", "user="+user.getIdUser(), "START");	
		
		getHibernateTemplate().getSessionFactory().getCurrentSession().clear();
		 
		getHibernateTemplate().saveOrUpdate(user);
		
		getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
		
		MyLogger.info(log, CLASS_NAME, "update", "user="+user.getIdUser(), "END");
		
	}

	@Override
	public void updatePass(int idUser,String pass){
		MyLogger.info(log, CLASS_NAME, "updatePass", "user="+idUser, "START");	

		String hql = "UPDATE UserData set passWordUser = :pass WHERE idUser = :id";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery( hql );
		query.setParameter("pass", pass);
		query.setParameter("id", idUser);
		int result = query.executeUpdate();
		getHibernateTemplate().getSessionFactory().getCurrentSession().clear();

		MyLogger.info(log, CLASS_NAME, "updatePass", "user="+idUser, "END", result);
	}
	
	@Override
	public void updateLastDate(String codeUser){
		MyLogger.info(log, CLASS_NAME, "updateLastDate", "user="+codeUser, "START");	

		String hql = "UPDATE UserData set lastAccess = CURRENT_TIMESTAMP() WHERE codeUser = :id";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery( hql );
		query.setParameter("id", codeUser);
		int result = query.executeUpdate();
		getHibernateTemplate().getSessionFactory().getCurrentSession().clear();

		MyLogger.info(log, CLASS_NAME, "updateLastDate", "user="+codeUser, "END", result);
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
