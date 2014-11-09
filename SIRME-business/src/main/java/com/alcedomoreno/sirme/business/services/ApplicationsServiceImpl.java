package com.alcedomoreno.sirme.business.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.business.data.Application;
import com.alcedomoreno.sirme.business.data.Question;
import com.alcedomoreno.sirme.business.data.QuestionGroup;
import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.transform.TransformFactory;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.dao.ApplicationsDao;
import com.alcedomoreno.sirme.core.dao.QuestionsDao;
import com.alcedomoreno.sirme.core.data.ApplicationData;
import com.alcedomoreno.sirme.core.util.DAOConstants;

@Transactional(readOnly=true)
@Service( ServiceConstants.APPLICATION_SERVICE )
public class ApplicationsServiceImpl implements ApplicationsService {

	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);
	private static final String CLASS_NAME = "ApplicationsServiceImpl";

	@Resource(name=DAOConstants.APPLICATION_DAO)
	ApplicationsDao applicationsDao;
	
	@Resource( name=ServiceConstants.QUESTION_SERVICE )
	protected QuestionService questionsService;
	
	private static Map<Integer,QuestionGroup> appQuestionGroup;
	private static Map<Integer,Question> appQuestion;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////
	
	@Override
	public Application getFullApplicationTree() {
		return getApplicationTree(null, Application.MAX_LEVEL);
	}

	@Override
	public Application getRootApplication() {
		return getApplicationTree(null, Application.ROOT_LEVEL);
	}

	@Override
	public boolean existsQuestion(int idQuestion){
		initMaps();
		return appQuestion.get(idQuestion) != null;
	}

	@Override
	public boolean existsQuestionGriup(int idQuestionGruop){
		initMaps();
		return appQuestionGroup.get(idQuestionGruop) != null;
	}
	
	private void initMaps(){
		if (appQuestion == null || appQuestionGroup == null){
			appQuestion = new HashMap<Integer, Question>();
			appQuestionGroup = new HashMap<Integer, QuestionGroup>();
			
			for (Report r : questionsService.getAllWithQuestions()) {
				for (QuestionGroup qg : r.getQuestionGroups()) {
					appQuestionGroup.put(qg.getIdQuestionGroup(), qg);
					for (Question q : qg.getQuestions()) {
						appQuestion.put(q.getIdQuestion(), q);
					}
				}
			}
		}
	}

	private Application getApplicationTree(Application application, byte loadLevel) {
		
		Application app = application;
		if (application == null) {
			ApplicationData data = applicationsDao.getRootApplication();
			if (data == null) {
				return null;
			}
			app = (Application) TransformFactory.getTransformator(Application.class).toBusinessObject(data);
		} 
		
		byte level = 0; 
		
		Collection<ApplicationData> dataList = null;
		Collection<Application> childList = null;
		if (level < loadLevel) {
			dataList = applicationsDao.getChildApplications(app.getIdApplication());
			
			if (dataList == null || dataList.isEmpty()) {
				return app;
			}
			
			childList = new ArrayList<Application>();
			Application child = null;
			for (ApplicationData childData : dataList) {
				 child = (Application)TransformFactory.getTransformator(Application.class).toBusinessObject(childData);
				 
				 child = getApplicationTree(child, new Integer(loadLevel - 1).byteValue());
			
				 child.setMainApplication(app);
				 
				 childList.add(child);
			}
			
			app.setChildApplications(childList);
			
			level++;
		}
		
		return app;
	}
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los M�todos P�blicos               //
	///////////////////////////////////////////////////////////////
}
