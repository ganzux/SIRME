package com.sirme.transform;

import com.sirme.bussiness.IBusinessObject;
import com.sirme.bussiness.Manager;
import com.sirme.bussiness.ManagerContact;
import com.sirme.data.IDataObject;
import com.sirme.data.ManagerContactData;
import com.sirme.data.ManagerData;

public class ContactManagerTransform extends DefaultTransformator implements ITransformator{

	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		ManagerContact business = new ManagerContact();
		ManagerContactData data = (ManagerContactData) dataObject;

		business.setIdContact( data.getIdManagerContact() );
		business.setDataContact( data.getDataContact() );
		business.setNameContact( data.getNameContact() );
		business.setManager( (Manager)TransformFactory.getTransformator(Manager.class).toBusinessObject( data.getManager() ) );

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
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
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
