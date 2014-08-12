package com.sirme.services.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sirme.beans.ApplicationBean;
import com.sirme.bussiness.Address;
import com.sirme.bussiness.ReplyGroup;
import com.sirme.bussiness.Report;
import com.sirme.bussiness.Team;
import com.sirme.bussiness.Work;
import com.sirme.dao.IQuestionsDao;
import com.sirme.dao.IWorkDao;
import com.sirme.data.AddressData;
import com.sirme.data.ReplyData;
import com.sirme.data.ReplyGroupData;
import com.sirme.data.TeamData;
import com.sirme.data.WorkData;
import com.sirme.services.IQuestionService;
import com.sirme.services.IWorkService;
import com.sirme.transform.TransformFactory;
import com.sirme.util.BeanNameUtil;
import com.sirme.util.ConfigService;
import com.sirme.util.FileUtil;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;

@Service( SpringConstants.WORK_SERVICE)
public class WorkServiceImpl implements IWorkService {

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( WorkServiceImpl.class );
	private static final String CLASS_NAME = "WorkServiceImpl";

	@Resource( name=BeanNameUtil.APP_BEAN)
	private ApplicationBean applicationBean;

	@Resource( name=SpringConstants.QUESTION_DAO )
	protected IQuestionsDao questionsDao;

	@Resource( name=SpringConstants.WORK_DAO )
	protected IWorkDao workDao;
	
	@Resource
	private ConfigService cfg;
	
	@Resource( name=SpringConstants.QUESTION_SERVICE)
	protected IQuestionService questionService;

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public WorkServiceImpl(){
		MyLogger.info(log, CLASS_NAME, "WorkServiceImpl", "New Instance");
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////

	@Override
	public Collection<Work> getAll() {
		MyLogger.info(log, CLASS_NAME, "getAll", "IN");
		
		Collection<Work> dummies = null;
		dummies = (Collection<Work>) TransformFactory.getTransformator(Work.class).toBusinessObject( workDao.getAll() );
		
		MyLogger.info(log, CLASS_NAME, "getAll", "OUT");

		return dummies;
	}
	
	@Override
	public Collection<Work> get( Address address ){
		MyLogger.info(log, CLASS_NAME, "get", "IN", address);
		
		Collection<Work> dummies = new ArrayList<Work>();
		Collection<WorkData> datas = workDao.getFromAddress( address.getIdAddress() );
		
		if ( datas != null )
			for ( WorkData wd: datas)
				dummies.add( questionService.getWork( wd.getIdWork() ) );

		MyLogger.info(log, CLASS_NAME, "get", "OUT", address);

		return dummies;
	}
	
	@Override
	public Collection<Work> getOpenAdvicesOrWorksFromTeam( int idTeam, Date date, boolean work ){
		MyLogger.info(log, CLASS_NAME, "getAllAdvicesFromTeam", "IN", idTeam);
		
		Collection<Work> dummies = new ArrayList<Work>();
		Collection<WorkData> datas = workDao.getOpenAdvicesOrWorksFromTeam(idTeam, date, work);
		
		if ( datas != null )
			for ( WorkData wd: datas)
				dummies.add( questionService.getWork( wd.getIdWork() ) );

		MyLogger.info(log, CLASS_NAME, "getAllAdvicesFromTeam", "OUT", idTeam);

		return dummies;
	}
	
	@Override
	public Work get( int idWork ) {
		MyLogger.info(log, CLASS_NAME, "get", "IN", idWork);
		
		Work work = null;
		work = (Work) TransformFactory.getTransformator(Work.class).toBusinessObject( workDao.get(idWork) );
		
		MyLogger.info(log, CLASS_NAME, "get", "OUT", idWork);

		return work;
	}

	@Override
	public int getNextAlbaran(int year){
		MyLogger.info(log, CLASS_NAME, "getNextAlbaran", "IN", year);
		
		int max = ( workDao.getMaxAlbaranByYear( year ) )+1;
		
		MyLogger.info(log, CLASS_NAME, "getNextAlbaran", "OUT", year);

		return max;
	}

	@Override
	public Collection<Report> getReportsFormWork( int idWork ){
		MyLogger.info(log, CLASS_NAME, "getReportsFormWork", "IN", idWork);

		Collection<ReplyData> replies = workDao.getRepliesFromWork( idWork );
		
		Collection<Report> reportsFromWork = new ArrayList<Report>();

		Map<Integer,Report> repliesMap = new HashMap<Integer,Report>();
			for ( ReplyData reply:replies ){

				/*int idReport = reply.getReport().getIdReport();

				if ( repliesMap.get( idReport ) == null ){
					Report report = (Report) TransformFactory.getTransformator(Report.class).toBusinessObject( reply.getReport() );
					report.setReplies( new ArrayList<Reply>() );
					repliesMap.put( idReport, report );
				}
				repliesMap.get( idReport ).getReplies().add( (Reply) TransformFactory.getTransformator(Reply.class).toBusinessObject( reply ) );
*/
			}
		
		
		MyLogger.info(log, CLASS_NAME, "getReportsFormWork", "OUT", idWork);
		return new ArrayList<Report>( repliesMap.values() );
	}
	
	public Collection<Report> getAllKindReports(){
		MyLogger.info(log, CLASS_NAME, "getAllKindReports", "IN");

		Collection<Report> reportsFromWork = (Collection<Report>) TransformFactory.getTransformator(Report.class).toBusinessObject( workDao.getAllReportsType() );
		
		MyLogger.info(log, CLASS_NAME, "getAllKindReports", "OUT");
		return reportsFromWork;
	}

	@Override
	public synchronized int save(Work work) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "save", "IN", work);

		validateWork( work );
		int nextAlbaran = 0;
		
		try{
			WorkData cd = getWorkNewContacts( work );

			// Estado Abierto
			if ( cd.getStatus() == 0 )
				cd.setStatus( Work.STATUS_ABIERTO );

			// Siguiente número de Albarán
			nextAlbaran = getNextAlbaran( cd.getYear() );
			cd.setAlbaran( nextAlbaran );
			
			if ( cd.getReplyGroups()!=null )
				for ( ReplyGroupData rgd:cd.getReplyGroups() ){
					rgd.setIdReplyGroup( 0 );
					rgd.setWork( cd );
					if ( rgd.getReplies()!=null )
						for ( ReplyData reply: rgd.getReplies() ){
							reply.setIdReply( 0 );
							reply.setReplyGroup( rgd );
						}
				}
			workDao.save( cd );
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "save", "OUT", work);
		
		return nextAlbaran;
	}
	
	@Override
	public void changeStatus(int idWork, int newStatus) throws TransactionException{
		MyLogger.info(log, CLASS_NAME, "changeStatus", "IN", idWork, newStatus);
		
		try{
			workDao.updateStatus(idWork,newStatus);
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "changeStatus", "OUT", idWork, newStatus);
	}

	@Override
	public void delete(Work work) throws TransactionException{
		MyLogger.info(log, CLASS_NAME, "delete", "IN", work);

		try{
			// Eliminamos todas las respuestas antiguas
			questionsDao.deleteRepliesFromWork( work.getIdWork() );
			questionsDao.deleteReplyGroupsFromWork( work.getIdWork() );
			workDao.delete(  getWorkNewContacts( work ) );
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "delete", "OUT", work);
	}

	@Override
	public void update(Work work) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "update", "IN", work);

		validateWork( work );
		
		try{
			WorkData cd = getWorkNewContacts( work );

			// Eliminamos todas las respuestas antiguas
			questionsDao.deleteRepliesFromWork( work.getIdWork() );
			questionsDao.deleteReplyGroupsFromWork( work.getIdWork() );

			// Y se guardan las respuestas nuevas
			if ( cd.getReplyGroups()!=null )
				for ( ReplyGroupData rgd:cd.getReplyGroups() ){
					rgd.setIdReplyGroup( 0 );
					if ( rgd.getReplies()!=null )
						for ( ReplyData reply: rgd.getReplies() ){
							reply.setIdReply( 0 );
							reply.setReplyGroup( rgd );
						}
				}
			workDao.update( cd );
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "update", "OUT", work);
	}

	public void update(Work work, InputStream newSign, String fileName, long size) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "updateSign", "IN", work);

		try{
			String oldFilePath	= work.getSignpath();
			String oldSigner	= work.getSignName();
			
			if ( oldFilePath == null || oldFilePath.trim().isEmpty() ){
				oldFilePath = cfg.getPhotoDirectory() + System.currentTimeMillis() + fileName;
			}

			workDao.updateSign(work.getIdWork(),oldFilePath, oldSigner);
			
			if ( size > 0 )
				FileUtil.getInstance().saveFile(newSign, oldFilePath);
		
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "updateSign", "OUT", work);
	}
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                      Métodos Privados                     //
	///////////////////////////////////////////////////////////////
	private WorkData getWorkNewContacts( Work work){
		WorkData cd = (WorkData)TransformFactory.getTransformator(Work.class).toDataObject( work );
//		cd.setCustomer( (CustomerData) TransformFactory.getTransformator(Customer.class).toDataObject( work.getCustomer() ) );
		cd.setAddress(  (AddressData) TransformFactory.getTransformator(Address.class).toDataObject( work.getAddress() ) );
		
		if ( work.getTeam() != null )
			cd.setTeam( (TeamData) TransformFactory.getTransformator(Team.class).toDataObject( work.getTeam() ) );
		
		Collection<ReplyGroup> replies = new ArrayList<ReplyGroup>();
		if (  work.getReports() !=null )
			for ( Report r: work.getReports() )
				if ( r.getReplyGroups() != null )
					replies.addAll( r.getReplyGroups() );
		cd.setReplyGroups( (Set<ReplyGroupData>) new HashSet( TransformFactory.getTransformator(ReplyGroup.class).toDataObject( replies ) ) );

		return cd;
	}
	
	private void validateWork(Work work) throws ValidationException{
		if ( work == null || work.getDate() == null  )
			throw new ValidationException("La fecha es obligatoria");
//		if ( work.getTeam() == null  )
//			throw new ValidationException("El Equipo de Trabajo es Obligatorio");
		if ( work.getCustomer() == null  )
			throw new ValidationException("El Cliente es Obligatorio");
		if ( work.getAddress() == null || work.getAddress().getIdAddress() == 0  )
			throw new ValidationException("La dirección de Servicio del Cliente es Obligatoria");
	}
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Privados               //
	///////////////////////////////////////////////////////////////
}
