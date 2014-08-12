package com.sirme.basicsecurity.business.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sirme.basicsecurity.SpringSecurityConstants;
import com.sirme.basicsecurity.business.IApplicationsService;
import com.sirme.basicsecurity.business.data.Application;
import com.sirme.basicsecurity.dao.IApplicationsDao;
import com.sirme.basicsecurity.data.ApplicationData;
import com.sirme.transform.TransformFactory;

@Service( SpringSecurityConstants.APPLICATION_SERVICE )
public class ApplicationsService implements IApplicationsService {

	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger(UsersService.class);
	private static final String CLASS_NAME = "UsersService";

	@Resource(name=SpringSecurityConstants.APPLICATION_DAO)
	IApplicationsDao applicationsDao;
	
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
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////
}
