package com.sirme.transform;

import com.sirme.bussiness.Address;
import com.sirme.bussiness.Customer;
import com.sirme.bussiness.IBusinessObject;
import com.sirme.data.AddressData;
import com.sirme.data.CustomerData;
import com.sirme.data.IDataObject;


public class AddressTransform extends DefaultTransformator implements ITransformator{

	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		Address business = new Address();
		AddressData data = (AddressData) dataObject;

		business.setIdAddress( data.getIdaddress() );
		business.setMainAddress( data.getAddress() );
		business.setMainPobl( data.getPobl() );
		business.setMainProv( data.getProv() );
		business.setMainPostalCode( String.valueOf( data.getPostalCode() ) );
		business.setOther( data.getOther() );
		business.setCustomer( (Customer)TransformFactory.getTransformator(Customer.class).toBusinessObject( data.getCustomer() ) );
		business.setLocation( data.getLocation() );
		business.setMonths( data.getMonthMask() );
		business.setAmount( data.getAmount() );

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
		Address business = (Address) businessObject;
		AddressData data = new AddressData();

		data.setIdaddress( business.getIdAddress() );
		data.setAddress( business.getMainAddress() );
		data.setPobl( business.getMainPobl() );
		data.setProv( business.getMainProv() );
		data.setLocation( business.getLocation() );
		data.setPostalCode( business.getMainPostalCode()==null || business.getMainPostalCode().isEmpty() || business.getMainPostalCode().equalsIgnoreCase("null")?null:Integer.valueOf( business.getMainPostalCode() ) );
		data.setOther( business.getOther() );
		data.setAmount( business.getAmount() );
		data.setMonthMask( Address.transformIntoMonth( business.getMonthsBinary() ) );
		if( business.getCustomer() != null  )
			data.setCustomer( (CustomerData)TransformFactory.getTransformator(Customer.class).toDataObject( business.getCustomer() ) );
		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
