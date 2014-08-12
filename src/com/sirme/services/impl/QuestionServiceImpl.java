package com.sirme.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sirme.bussiness.Address;
import com.sirme.bussiness.Customer;
import com.sirme.bussiness.Question;
import com.sirme.bussiness.QuestionGroup;
import com.sirme.bussiness.ReplyGroup;
import com.sirme.bussiness.Report;
import com.sirme.bussiness.Work;
import com.sirme.dao.IQuestionsDao;
import com.sirme.data.QuestionGroupData;
import com.sirme.data.ReplyGroupData;
import com.sirme.data.ReportData;
import com.sirme.data.WorkData;
import com.sirme.services.IQuestionService;
import com.sirme.transform.TransformFactory;
import com.sirme.transform.comparators.ReplyGroupDataComparator;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Service( SpringConstants.QUESTION_SERVICE)
public class QuestionServiceImpl implements IQuestionService {

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( QuestionServiceImpl.class );
	private static final String CLASS_NAME = "QuestionServiceImpl";
	
	@Resource( name=SpringConstants.QUESTION_DAO )
	protected IQuestionsDao questionsDao;

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public QuestionServiceImpl(){
		MyLogger.info(log, CLASS_NAME, "QuestionServiceImpl", "New Instance");
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////

	@Override
	public Collection<Report> getAll() {
		MyLogger.info(log, CLASS_NAME, "getAll", "IN");
		
		Collection<Report> reports = null;
		reports = (Collection<Report>) TransformFactory.getTransformator(Report.class).toBusinessObject( questionsDao.getAll() );
		
		MyLogger.info(log, CLASS_NAME, "getAll", "OUT");

		return reports;
	}

	@Override
	public Collection<Report> getAllWithQuestions() {
		MyLogger.info(log, CLASS_NAME, "getAllWithQuestions", "IN");
		
		Collection<ReportData> reportsData = questionsDao.getAll();
		Collection<Report> reports = new ArrayList<Report>();
		
		for ( ReportData r:reportsData ){
			Report report = (Report) TransformFactory.getTransformator(Report.class).toBusinessObject( r );
			report.setQuestionGroups( new ArrayList<QuestionGroup>() );
			
			for ( QuestionGroupData qgd:r.getQuestionGroups() ){
				QuestionGroup qg = (QuestionGroup) TransformFactory.getTransformator(QuestionGroup.class).toBusinessObject( qgd );

				qg.setQuestions( (Collection<Question>) TransformFactory.getTransformator(Question.class).toBusinessObject( qgd.getQuestions() ) );

				report.getQuestionGroups().add( qg );
			}

			reports.add( report );
		}
		
		MyLogger.info(log, CLASS_NAME, "getAllWithQuestions", "OUT");

		return reports;
	}

	@Override
	public Work getWork( int idWork ) {
		MyLogger.info(log, CLASS_NAME, "getWork", "IN");
		
		WorkData wd = questionsDao.getWork( idWork );
		Work dumm = (Work) TransformFactory.getTransformator(Work.class).toBusinessObject( wd );
		
		List<ReplyGroupData> replieGroups = new ArrayList<ReplyGroupData>( wd.getReplyGroups() );
		Collections.sort( replieGroups, new ReplyGroupDataComparator());

		Map<Integer,Report> reportMap = new HashMap<Integer,Report>();
		for ( ReplyGroupData replyG:replieGroups ){

			Report r = (Report) TransformFactory.getTransformator(Report.class).toBusinessObject( replyG.getQuestionGroup().getReport() );
			int idReport = r.getIdReport();

			if ( reportMap.get( idReport ) == null ){
				r.setReplyGroups( new ArrayList<ReplyGroup>() );
				reportMap.put(idReport, r);
			}
			ReplyGroup rg = (ReplyGroup) TransformFactory.getTransformator(ReplyGroup.class).toBusinessObject( replyG );
			rg.setQuestionGroup( (QuestionGroup) TransformFactory.getTransformator(QuestionGroup.class).toBusinessObject( replyG.getQuestionGroup() ) );
			rg.getQuestionGroup().setReport( (Report) TransformFactory.getTransformator(Report.class).toBusinessObject( replyG.getQuestionGroup().getReport() ) );
			rg.setWork( (Work) TransformFactory.getTransformator(Work.class).toBusinessObject( replyG.getWork() ) );
			reportMap.get( idReport ).getReplyGroups().add( rg );
		}

		dumm.setCustomer( (Customer) TransformFactory.getTransformator(Customer.class).toBusinessObject( wd.getAddress().getCustomer() ) );
		dumm.getCustomer().setAddress( (Collection<Address>) TransformFactory.getTransformator(Address.class).toBusinessObject( wd.getAddress().getCustomer().getAddress() ) );

		dumm.setReports( new ArrayList(reportMap.values()) );
		
		MyLogger.info(log, CLASS_NAME, "getWork", "OUT");

		return dumm;
	}
	
	@Override
	public Work getLastWorkByAddress( int idAddress ){
		MyLogger.info(log, CLASS_NAME, "getLastWorkByAddress", "IN");
		
		WorkData wd = questionsDao.getWorkByAddress( idAddress );
		Work dumm = new Work();
		
		Map<Integer,Report> reportMap = new HashMap<Integer,Report>();
		
		if ( wd != null ){
			dumm = (Work) TransformFactory.getTransformator(Work.class).toBusinessObject( wd );
			Collection<ReplyGroupData> replieGroups = wd.getReplyGroups();
			
			if ( replieGroups != null ){
				for ( ReplyGroupData replyG:replieGroups ){
		
					Report r = (Report) TransformFactory.getTransformator(Report.class).toBusinessObject( replyG.getQuestionGroup().getReport() );
					int idReport = r.getIdReport();
		
					if ( reportMap.get( idReport ) == null ){
						r.setReplyGroups( new ArrayList<ReplyGroup>() );
						reportMap.put(idReport, r);
					}
					ReplyGroup rg = (ReplyGroup) TransformFactory.getTransformator(ReplyGroup.class).toBusinessObject( replyG );
					rg.setQuestionGroup( (QuestionGroup) TransformFactory.getTransformator(QuestionGroup.class).toBusinessObject( replyG.getQuestionGroup() ) );
					rg.getQuestionGroup().setReport( (Report) TransformFactory.getTransformator(Report.class).toBusinessObject( replyG.getQuestionGroup().getReport() ) );
					rg.setWork( (Work) TransformFactory.getTransformator(Work.class).toBusinessObject( replyG.getWork() ) );
					reportMap.get( idReport ).getReplyGroups().add( rg );
				}
			}
			
			dumm.setCustomer( (Customer) TransformFactory.getTransformator(Customer.class).toBusinessObject( wd.getAddress().getCustomer() ) );
			dumm.getCustomer().setAddress( (Collection<Address>) TransformFactory.getTransformator(Address.class).toBusinessObject( wd.getAddress().getCustomer().getAddress() ) );
		}
		
		dumm.setReports( new ArrayList(reportMap.values()) );
		
		MyLogger.info(log, CLASS_NAME, "getLastWorkByAddress", "OUT");

		return dumm;
	}

	@Override
	public void update( Work work ){
		MyLogger.info(log, CLASS_NAME, "update", "IN");
		WorkData wd = (WorkData) TransformFactory.getTransformator(Work.class).toDataObject( work );
		questionsDao.update( wd );
		MyLogger.info(log, CLASS_NAME, "update", "OUT");
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
