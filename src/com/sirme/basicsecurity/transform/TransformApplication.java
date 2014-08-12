package com.sirme.basicsecurity.transform;



import com.sirme.basicsecurity.business.data.Application;
import com.sirme.basicsecurity.data.ApplicationData;
import com.sirme.bussiness.IBusinessObject;
import com.sirme.data.IDataObject;
import com.sirme.transform.DefaultTransformator;
import com.sirme.transform.ITransformator;



public class TransformApplication extends DefaultTransformator implements ITransformator {
	
	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		Application business = new Application();
		ApplicationData data = (ApplicationData) dataObject;
		
		business.setIdApplication(data.getIdApplication());	
		business.setCodeApplication(data.getCodeApplication());
		business.setNameApplication(data.getNameApplication());
		business.setLevelApplication(data.getLevelApplication());
		
		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
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
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
