package com.alcedomoreno.sirme.business.transform;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Contact;
import com.alcedomoreno.sirme.business.data.Customer;
import com.alcedomoreno.sirme.core.data.ContactData;
import com.alcedomoreno.sirme.core.data.CustomerData;
import com.alcedomoreno.sirme.core.data.DataObject;

public class ContactTransform extends DefaultTransformator implements Transformator{

	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
		Contact business = new Contact();
		ContactData data = (ContactData) dataObject;

		business.setIdContact( data.getIdContact() );
		business.setDataContact( data.getDataContact() );
		business.setNameContact( data.getNameContact() );
		business.setCustomer( (Customer)TransformFactory.getTransformator(Customer.class).toBusinessObject( data.getCustomer() ) );

		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
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
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
