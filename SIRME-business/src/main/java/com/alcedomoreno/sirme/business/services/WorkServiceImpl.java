package com.alcedomoreno.sirme.business.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.business.data.Address;
import com.alcedomoreno.sirme.business.data.ReplyGroup;
import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.data.Team;
import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.business.transform.TransformFactory;
import com.alcedomoreno.sirme.business.util.FileUtil;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.business.util.TransactionException;
import com.alcedomoreno.sirme.business.util.ValidationException;
import com.alcedomoreno.sirme.core.dao.QuestionsDao;
import com.alcedomoreno.sirme.core.dao.WorkDao;
import com.alcedomoreno.sirme.core.data.AddressData;
import com.alcedomoreno.sirme.core.data.ReplyData;
import com.alcedomoreno.sirme.core.data.ReplyGroupData;
import com.alcedomoreno.sirme.core.data.ReportData;
import com.alcedomoreno.sirme.core.data.TeamData;
import com.alcedomoreno.sirme.core.data.WorkData;
import com.alcedomoreno.sirme.core.util.DAOConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Transactional(readOnly=true)
@Service( ServiceConstants.WORK_SERVICE)
public class WorkServiceImpl implements WorkService {

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( WorkServiceImpl.class );
	private static final String CLASS_NAME = "WorkServiceImpl";

	@Resource( name=DAOConstants.QUESTION_DAO )
	protected QuestionsDao questionsDao;

	@Resource( name=DAOConstants.WORK_DAO )
	protected WorkDao workDao;
	
	@Resource
	private ConfigService cfg;
	
	@Resource( name=ServiceConstants.QUESTION_SERVICE)
	protected QuestionService questionService;

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
	public Collection<Work> getAll(String[] selectedYears) {
		MyLogger.info(log, CLASS_NAME, "getAll", "IN");

		Collection<Work> dummies = null;

		List<Integer> years = null;
		try{
			years = new ArrayList<Integer>();
			for (String yearStr : selectedYears){
				years.add(Integer.valueOf(yearStr));
			}
		} catch(Exception e){
		}

		dummies = (Collection<Work>) TransformFactory.getTransformator(Work.class).toBusinessObject(workDao.getAll(years));
		
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
	public Collection<Report> getAllKindReports(){
		MyLogger.info(log, CLASS_NAME, "getAllKindReports", "IN");

		Collection<Report> reportsFromWork = (Collection<Report>) TransformFactory.getTransformator(Report.class).toBusinessObject( workDao.getAllReportsType() );
		
		MyLogger.info(log, CLASS_NAME, "getAllKindReports", "OUT");
		return reportsFromWork;
	}

	@Transactional(readOnly=false)
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

	@Transactional(readOnly=false)
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

	@Transactional(readOnly=false)
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

	@Transactional(readOnly=false)
	@Override
	public void update(Work work) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "update", "IN", work);

		validateWork( work );
		
		try{
			WorkData workData = getWorkNewContacts( work );

			// Eliminamos todas las respuestas antiguas
			questionsDao.deleteRepliesFromWork( work.getIdWork() );
			questionsDao.deleteReplyGroupsFromWork( work.getIdWork() );

			// Y se guardan las respuestas nuevas
			if (workData.getReplyGroups() != null) {
				for (ReplyGroupData rgd : workData.getReplyGroups()){
					rgd.setIdReplyGroup(0);
					if (rgd.getReplies() != null) {
						for (ReplyData reply : rgd.getReplies()){
							reply.setIdReply(0);
							reply.setReplyGroup(rgd);
							if (reply.getReplyGroup() == null) {
								reply.setReplyGroup(rgd);
							}
						}
					}
					rgd.setWork(workData);
				}
			}
			workDao.update(workData);
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "update", "OUT", work);
	}

	@Transactional(readOnly=false)
	@Override
	public void updateRest(Work work) throws ValidationException, TransactionException{
		MyLogger.info(log, CLASS_NAME, "updateRest", "IN", work);

		validateWork( work );
		
		try{
			WorkData workData = getWorkNewContacts( work );

			// Eliminamos todas las respuestas antiguas
			questionsDao.deleteRepliesFromWork(work.getIdWork());
			questionsDao.deleteReplyGroupsFromWork(work.getIdWork());

			// Y se guardan las respuestas nuevas
			if (workData.getReplyGroups() != null) {
				for (ReplyGroupData rgd : workData.getReplyGroups()){
					rgd.setIdReplyGroup(0);
					if (rgd.getReplies() != null) {
						for (ReplyData reply : rgd.getReplies()){
							reply.setIdReply(0);
							reply.setReplyGroup(rgd);
							if (reply.getReplyGroup() == null) {
								reply.setReplyGroup(rgd);
							}
						}
					}
					rgd.setWork(workData);
				}
			}
			workDao.update(workData);
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "updateRest", "OUT", work);
	}
	
	@Transactional(readOnly=false)
	@Override
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
	private WorkData getWorkNewContacts(Work work){
		WorkData cd = (WorkData)TransformFactory.getTransformator(Work.class).toDataObject( work );
		cd.setAddress((AddressData) TransformFactory.getTransformator(Address.class).toDataObject(work.getAddress()));
		
		if (work.getTeam() != null)
			cd.setTeam( (TeamData) TransformFactory.getTransformator(Team.class).toDataObject( work.getTeam() ) );

		Set<ReplyGroupData> repliesData = new HashSet<ReplyGroupData>();
		
		if (work.getReports() != null) {
			for (Report r : work.getReports()) {
				if (r.getReplyGroups() != null) {
					for (ReplyGroup replyGroup : r.getReplyGroups()) {
						replyGroup.setWork(work);
						ReplyGroupData replieGroupData = (ReplyGroupData) TransformFactory.getTransformator(ReplyGroup.class).toDataObject(replyGroup);
						replieGroupData.setReport((ReportData) TransformFactory.getTransformator(Report.class).toDataObject(r));
						repliesData.add(replieGroupData);
					}
				}
			}
		}
		cd.setReplyGroups( repliesData );

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

