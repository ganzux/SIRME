package com.alcedomoreno.sirme.business.transform;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Manager;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.ManagerData;

public class ManagerTransform extends DefaultTransformator implements Transformator{

	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
		Manager business = new Manager();
		ManagerData data = (ManagerData) dataObject;

		business.setIdManager( data.getIdManager() );
		business.setNameManager( data.getNameManager() );
		business.setMailManager( data.getMailManager() );
		business.setPhoneManager( data.getPhoneManager() );

		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
		Manager business = (Manager) businessObject;
		ManagerData data = new ManagerData();

		data.setIdManager( business.getIdManager() );
		data.setNameManager( business.getNameManager() );
		data.setMailManager( business.getMailManager() );
		data.setPhoneManager( business.getPhoneManager() );

		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
