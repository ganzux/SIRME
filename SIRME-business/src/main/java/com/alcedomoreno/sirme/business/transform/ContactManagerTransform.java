package com.alcedomoreno.sirme.business.transform;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Manager;
import com.alcedomoreno.sirme.business.data.ManagerContact;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.ManagerContactData;
import com.alcedomoreno.sirme.core.data.ManagerData;

public class ContactManagerTransform extends DefaultTransformator implements Transformator{

	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
		ManagerContact business = new ManagerContact();
		ManagerContactData data = (ManagerContactData) dataObject;

		business.setIdContact( data.getIdManagerContact() );
		business.setDataContact( data.getDataContact() );
		business.setNameContact( data.getNameContact() );
		business.setManager( (Manager)TransformFactory.getTransformator(Manager.class).toBusinessObject( data.getManager() ) );

		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
		ManagerContact business = (ManagerContact) businessObject;
		ManagerContactData data = new ManagerContactData();

		data.setIdManagerContact( business.getIdContact() );
		data.setDataContact( business.getDataContact() );
		data.setNameContact( business.getNameContact() );
		if( business.getManager() != null  )
			data.setManager( (ManagerData)TransformFactory.getTransformator(Manager.class).toDataObject( business.getManager() ) );

		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
