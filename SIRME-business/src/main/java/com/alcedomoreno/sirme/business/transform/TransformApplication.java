package com.alcedomoreno.sirme.business.transform;



import com.alcedomoreno.sirme.business.data.Application;
import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.core.data.ApplicationData;
import com.alcedomoreno.sirme.core.data.DataObject;



public class TransformApplication extends DefaultTransformator implements Transformator {
	
	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
		Application business = new Application();
		ApplicationData data = (ApplicationData) dataObject;
		
		business.setIdApplication(data.getIdApplication());	
		business.setCodeApplication(data.getCodeApplication());
		business.setNameApplication(data.getNameApplication());
		business.setLevelApplication(data.getLevelApplication());
		
		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
		Application business = (Application) businessObject;
		ApplicationData data = null;
	
		data = new ApplicationData();
		
		data.setIdApplication(business.getIdApplication());	
		data.setNameApplication(business.getNameApplication());
		data.setCodeApplication(business.getCodeApplication());
		data.setLevelApplication(business.getLevelApplication());
		
		return data;
		
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
