package com.sirme.transform;

import com.sirme.bussiness.IBusinessObject;
import com.sirme.bussiness.Manager;
import com.sirme.data.IDataObject;
import com.sirme.data.ManagerData;

public class ManagerTransform extends DefaultTransformator implements ITransformator{

	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		Manager business = new Manager();
		ManagerData data = (ManagerData) dataObject;

		business.setIdManager( data.getIdManager() );
		business.setNameManager( data.getNameManager() );
		business.setMailManager( data.getMailManager() );
		business.setPhoneManager( data.getPhoneManager() );

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
		Manager business = (Manager) businessObject;
		ManagerData data = new ManagerData();

		data.setIdManager( business.getIdManager() );
		data.setNameManager( business.getNameManager() );
		data.setMailManager( business.getMailManager() );
		data.setPhoneManager( business.getPhoneManager() );

		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
