package com.sirme.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.sirme.dao.IQuestionsDao;
import com.sirme.data.ReportData;
import com.sirme.data.WorkData;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Repository( SpringConstants.QUESTION_DAO)
public class QuestionDaoImpl extends HibernateDaoSupport implements IQuestionsDao{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( QuestionDaoImpl.class );
	private static final String CLASS_NAME = "QuestionDaoImpl";

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public QuestionDaoImpl(){
		MyLogger.info(log, CLASS_NAME, "QuestionDaoImpl", "New Instance");
	}
	
	@Autowired  
	public QuestionDaoImpl(SessionFactory sessionFactory) {
		MyLogger.info( log , CLASS_NAME, "QuestionDaoImpl", "New Instance");
	    super.setSessionFactory(sessionFactory);
	    
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////

	@Override
	public Collection<ReportData> getAll() {
		MyLogger.info( log , CLASS_NAME, "getAll", "INIT");
		Collection<ReportData> collection = getHibernateTemplate().loadAll( ReportData.class );
		MyLogger.info( log , CLASS_NAME, "getAll", "END");
		return collection;
	}
	
	@Override
	public WorkData getWork( int idAddress ){
		MyLogger.info( log , CLASS_NAME, "getWork", "INIT");
		
		WorkData c = (WorkData)  getSessionFactory().getCurrentSession().createCriteria(WorkData.class)
				.setFetchMode("replyGroups", FetchMode.JOIN)
				.createAlias("replyGroups", "r", CriteriaSpecification.LEFT_JOIN)
				.setFetchMode("r.replies", FetchMode.JOIN)
//				.createAlias("replies", "re", CriteriaSpecification.LEFT_JOIN)
//				.setFetchMode("re.question", FetchMode.JOIN)
                .add( Restrictions.idEq(idAddress) )
                .uniqueResult();

		MyLogger.info( log , CLASS_NAME, "getWork", "END");
		return c;
	}

	@Override
	public WorkData getWorkByAddress( int idAddress ){
		MyLogger.info( log , CLASS_NAME, "getWorkByAddress", "INIT");
		
		WorkData c0 = null;
		
		@SuppressWarnings("unchecked")
		List<WorkData> c = (List<WorkData>)  getSessionFactory().getCurrentSession().createCriteria(WorkData.class)
				.setFetchMode("replyGroups", FetchMode.JOIN)
				.createAlias("replyGroups", "r", CriteriaSpecification.LEFT_JOIN)
				.setFetchMode("r.replies", FetchMode.JOIN)
                .add( Restrictions.eq("address.idaddress",idAddress) )
                .addOrder( Order.desc("dateCreated") )
                .list();
		
		if ( c != null && !c.isEmpty() )
			c0 = c.get( 0 );

		MyLogger.info( log , CLASS_NAME, "getWorkByAddress", "END");
		return c0;
	}
	
	@Override
	public void update( WorkData wd ){
		MyLogger.info( log , CLASS_NAME, "update", "INIT");
		
		getSessionFactory().getCurrentSession().update( wd );

		MyLogger.info( log , CLASS_NAME, "update", "END");
	}
	
	@Override
	public int deleteReplyGroupsFromWork( int idWork ){
		MyLogger.info( log , CLASS_NAME, "deleteReplyGroupsFromWork", "INIT");

		Query query = getSessionFactory().getCurrentSession().createQuery("delete ReplyGroupData where idWork = :id");
		query.setParameter("id", idWork);
		int result = query.executeUpdate();

		MyLogger.info( log , CLASS_NAME, "deleteReplyGroupsFromWork", "END");
		
		return result;
	}
	
	public int deleteRepliesFromWork( int idWork ){
		MyLogger.info( log , CLASS_NAME, "deleteRepliesFromWork", "INIT");

		Query query = getSessionFactory().getCurrentSession().createQuery("delete ReplyData where idReplyGroup in (select idReplyGroup from ReplyGroupData where idWork = :id) ");

		query.setParameter("id", idWork);
		int result = query.executeUpdate();

		MyLogger.info( log , CLASS_NAME, "deleteRepliesFromWork", "END");
		
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
