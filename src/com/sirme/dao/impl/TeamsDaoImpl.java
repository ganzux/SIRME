package com.sirme.dao.impl;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sirme.dao.ITeamsDao;
import com.sirme.data.TeamData;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Repository( SpringConstants.TEAM_DAO)
public class TeamsDaoImpl extends HibernateDaoSupport implements ITeamsDao{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( CustomersDaoImpl.class );
	private static final String CLASS_NAME = "TeamsDaoImpl";

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public TeamsDaoImpl(){
		MyLogger.info(log, CLASS_NAME, "TeamsDaoImpl", "New Instance");
	}
	
	@Autowired  
	public TeamsDaoImpl(SessionFactory sessionFactory) {
		MyLogger.info( log , CLASS_NAME, "TeamsDaoImpl", "New Instance");
	    super.setSessionFactory(sessionFactory);
	    
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////

	@Override
	public Collection<TeamData> getAll() {
		MyLogger.info( log , CLASS_NAME, "getAll", "INIT");
		Collection<TeamData> collection = getHibernateTemplate().loadAll( TeamData.class );
		MyLogger.info( log , CLASS_NAME, "getAll", "END");
		return collection;
	}

	@Override
	public TeamData get( String teamName,String passWord ){
		MyLogger.info( log , CLASS_NAME, "get", "INIT", teamName);

		TeamData team = (TeamData)  getSessionFactory().getCurrentSession().createCriteria(TeamData.class)
				.add( Restrictions.eq("nameTeam", teamName) )
				.add( Restrictions.eq("passWord", passWord) )
                .uniqueResult();
		
		MyLogger.info( log , CLASS_NAME, "get", "END", teamName);
		return team;
	}

	@Override
	public void save(TeamData cd){
		MyLogger.info( log , CLASS_NAME, "save", "INIT");
		getHibernateTemplate().save( cd );
		MyLogger.info( log , CLASS_NAME, "save", "END");
	}
	
	@Override
	public void update(TeamData cd){
		MyLogger.info( log , CLASS_NAME, "save", "INIT");
		getHibernateTemplate().update( cd );
		MyLogger.info( log , CLASS_NAME, "save", "END");
	}

	@Override
	public void delete(TeamData cd){
		MyLogger.info( log , CLASS_NAME, "delete", "INIT");
//		Query query = getSessionFactory().getCurrentSession().createQuery("delete TeamData where idTeam = :id");
//		query.setParameter("id", idTeam);
//		int result = query.executeUpdate();
		getHibernateTemplate().delete( cd );
		getHibernateTemplate().flush();
		MyLogger.info( log , CLASS_NAME, "delete", "END");
//		return result;
	}
	
	@Override
	public void updatePass(int idTeam,String pass){
		MyLogger.info(log, CLASS_NAME, "updatePass", "team="+idTeam, "START");	

		String hql = "UPDATE TeamData set passWord = :pass WHERE idTeam = :id";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery( hql );
		query.setParameter("pass", pass);
		query.setParameter("id", idTeam);
		int result = query.executeUpdate();
		getHibernateTemplate().getSessionFactory().getCurrentSession().clear();

		MyLogger.info(log, CLASS_NAME, "updatePass", "team"+idTeam, "END", result);
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
