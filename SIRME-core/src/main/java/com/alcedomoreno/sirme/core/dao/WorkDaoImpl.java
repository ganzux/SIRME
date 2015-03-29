package com.alcedomoreno.sirme.core.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.alcedomoreno.sirme.core.data.ReportData;
import com.alcedomoreno.sirme.core.data.WorkData;
import com.alcedomoreno.sirme.core.util.DAOConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;
import com.alcedomoreno.sirme.core.util.TypeWork;

@Repository(DAOConstants.WORK_DAO)
public class WorkDaoImpl extends HibernateDaoSupport implements WorkDao{

	///////////////////////////////////////////////////////////////
	//                         Atributtes                        //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( WorkDaoImpl.class );
	private static final String CLASS_NAME = "WorkDaoImpl";

	///////////////////////////////////////////////////////////////
	//                      End of Atributtes                    //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                        Constructors                       //
	///////////////////////////////////////////////////////////////
	
	public WorkDaoImpl(){
		MyLogger.info(log, CLASS_NAME, "WorkDaoImpl", "New Instance");
	}
	
	@Autowired  
	public WorkDaoImpl(SessionFactory sessionFactory) {
		MyLogger.info( log , CLASS_NAME, "WorkDaoImpl", "New Instance");
	    super.setSessionFactory(sessionFactory);
	    
	}
	
	///////////////////////////////////////////////////////////////
	//                     End of Constructors                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Public Methods                      //
	///////////////////////////////////////////////////////////////

	@Override
	public Collection<WorkData> getAll() {
		return getAll(null);
	}
	
	@Override
	public Collection<WorkData> getAll(List<Integer> selectedYears) {
		MyLogger.info( log , CLASS_NAME, "getAll", "INIT");

		Collection<WorkData> collection = new ArrayList<WorkData>();
		
		if (selectedYears == null || selectedYears.isEmpty()){
			collection = getHibernateTemplate().loadAll(WorkData.class);
		} else {
			collection = getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(WorkData.class)
			        .add( Restrictions.in("year", selectedYears) ));
		}

		MyLogger.info( log , CLASS_NAME, "getAll", "END");
		return collection;
	}

	@Override
	public Collection<WorkData> getFromAddress(int idAddress){
		MyLogger.info( log , CLASS_NAME, "getFromAddress", "INIT", idAddress);

		StringBuilder querys = new StringBuilder(" from WorkData w ");
		querys.append(" WHERE w.address.idaddress = :idw ");
        Query query = getSessionFactory().getCurrentSession().createQuery( querys.toString() );

		query.setParameter("idw", idAddress);
		Collection<WorkData> workList = query.list();

		MyLogger.info( log , CLASS_NAME, "getFromAddress", "END", idAddress);
		return workList;
	}

	@Override
	public Collection<WorkData> getOpenAdvicesOrWorksFromTeam(int idTeam, Date date, boolean work){
		MyLogger.info( log , CLASS_NAME, "getAdvicesFromTeam", "INIT", idTeam);

		Calendar dateCalendar = Calendar.getInstance();
		
		if (date != null){
			dateCalendar.setTime(date);
		} else {
			dateCalendar.setTime(new Date());
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		Date startDate = date;

		try {
			startDate = format.parse(dateCalendar.get(Calendar.YEAR) + "-" +
											(dateCalendar.get(Calendar.MONTH)+1) + "-" +
											dateCalendar.get(Calendar.DAY_OF_MONTH) + " 23:59:59");
		} catch (ParseException e) {
			MyLogger.error( log , CLASS_NAME, "getOpenAdvicesOrWorksFromTeam", "null date", e.getMessage());
		}  

		StringBuilder querys = new StringBuilder(" from WorkData w ");
		querys.append(" WHERE w.team.idTeam = :idw ");
		querys.append(" AND w.typeWork = :tw ");
		querys.append(" AND w.status = :sta ");
		querys.append(" AND w.date <= :dty ");
        Query query = getSessionFactory().getCurrentSession().createQuery( querys.toString() );

        query.setParameter("idw", idTeam);

        query.setParameter("tw", TypeWork.getIdTypeWork( work?TypeWork.TYPE_AVISO:TypeWork.TYPE_PARTE ));

        query.setParameter("sta", com.alcedomoreno.sirme.core.util.Work.STATUS_ABIERTO );
        query.setParameter("dty", startDate );

		Collection<WorkData> workList = query.list();

		MyLogger.info( log , CLASS_NAME, "getAdvicesFromTeam", "END", idTeam);
		return workList;
	}

	@Override
	public Collection<ReportData> getAllReportsType(){
		MyLogger.info( log , CLASS_NAME, "getAllReportsType", "INIT");
		Collection<ReportData> collection = getHibernateTemplate().loadAll( ReportData.class );
		MyLogger.info( log , CLASS_NAME, "getAllReportsType", "END");
		return collection;
	}
	
	@Override
	public int getMaxAlbaranByYear(int year){
		MyLogger.info( log , CLASS_NAME, "getMaxAlbaranByYear", "INIT", year);
		int max = 0;

		Criteria criteria = getSessionFactory().getCurrentSession()
			    .createCriteria( WorkData.class )
			    .setProjection( Projections.max("albaran") )
			    .add( org.hibernate.criterion.Expression.eq("year",year) );

		if ( criteria.uniqueResult() != null )
			max = (Integer) criteria.uniqueResult();
		else
			max = 0;

		MyLogger.info( log , CLASS_NAME, "getMaxAlbaranByYear", "END", max);
		return max;
	}

	@Override
	public WorkData get(int idWork){
		MyLogger.info( log , CLASS_NAME, "get", "INIT", idWork);
		
		WorkData c = (WorkData)  getSessionFactory().getCurrentSession().createCriteria(WorkData.class)
				.setFetchMode("team", FetchMode.JOIN)
				.setFetchMode("customer", FetchMode.JOIN)
                .add( Restrictions.eq("idWork",idWork) )
                .uniqueResult();

		MyLogger.info( log , CLASS_NAME, "get", "END", idWork);
		return c;
	}

	@Override
	public int save(WorkData cd){
		MyLogger.info( log , CLASS_NAME, "save", "INIT", cd);
		getHibernateTemplate().save( cd );
		MyLogger.info( log , CLASS_NAME, "save", "END", cd);
		return cd.getIdWork();
	}
	
	@Override
	public void update(WorkData cd){
		MyLogger.info( log , CLASS_NAME, "save", "INIT", cd);
		getHibernateTemplate().update( cd );
		MyLogger.info( log , CLASS_NAME, "save", "END", cd);
	}

	@Override
	public void delete(WorkData cd){
		MyLogger.info( log , CLASS_NAME, "delete", "INIT", cd);
		Query query = getSessionFactory().getCurrentSession().createQuery("delete WorkData where idWork = :id");
		query.setParameter("id", cd.getIdWork());
		query.executeUpdate();
		MyLogger.info( log , CLASS_NAME, "delete", "END");
	}

	@Override
	public void updateStatus(int idWoirk, int newStatus){
		MyLogger.info(log, CLASS_NAME, "updateStatus", idWoirk,newStatus,"START");	

		String hql = "UPDATE WorkData set status = :newStatus WHERE idWork = :id";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery( hql );
		query.setParameter("newStatus", newStatus);
		query.setParameter("id", idWoirk);
		int result = query.executeUpdate();
		getHibernateTemplate().getSessionFactory().getCurrentSession().clear();

		MyLogger.info(log, CLASS_NAME, "updateStatus", idWoirk,newStatus, "END", result);
	}
	
	public void updateSign(int idWoirk, String pathSign, String signer){
		MyLogger.info(log, CLASS_NAME, "updateSign", idWoirk,pathSign,signer,"START");	

		String hql = "UPDATE WorkData set signpath = :signpath,signName = :signName WHERE idWork = :id";
		Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery( hql );
		query.setParameter("signpath", pathSign);
		query.setParameter("signName", signer);
		query.setParameter("id", idWoirk);
		int result = query.executeUpdate();
		getHibernateTemplate().getSessionFactory().getCurrentSession().clear();

		MyLogger.info(log, CLASS_NAME, "updateSign", idWoirk,pathSign,signer, "END", result);
	}
	
	///////////////////////////////////////////////////////////////
	//                    End Of Public Methods                  //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                       Private Methods                     //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                    End Of Private Methods                 //
	///////////////////////////////////////////////////////////////
}
