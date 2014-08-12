package com.sirme.transform;

import com.sirme.bussiness.Contact;
import com.sirme.bussiness.Customer;
import com.sirme.bussiness.IBusinessObject;
import com.sirme.data.ContactData;
import com.sirme.data.CustomerData;
import com.sirme.data.IDataObject;

public class ContactTransform extends DefaultTransformator implements ITransformator{

	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		Contact business = new Contact();
		ContactData data = (ContactData) dataObject;

		business.setIdContact( data.getIdContact() );
		business.setDataContact( data.getDataContact() );
		business.setNameContact( data.getNameContact() );
		business.setCustomer( (Customer)TransformFactory.getTransformator(Customer.class).toBusinessObject( data.getCustomer() ) );

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
		Contact business = (Contact) businessObject;
		ContactData data = new ContactData();

		data.setIdContact( business.getIdContact() );
		data.setDataContact( business.getDataContact() );
		data.setNameContact( business.getNameContact() );
		if( business.getCustomer() != null  )
			data.setCustomer( (CustomerData)TransformFactory.getTransformator(Customer.class).toDataObject( business.getCustomer() ) );

		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
