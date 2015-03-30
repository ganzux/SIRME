package com.alcedomoreno.sirme.business.transform;

import com.alcedomoreno.sirme.business.data.Address;
import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Customer;
import com.alcedomoreno.sirme.core.data.AddressData;
import com.alcedomoreno.sirme.core.data.CustomerData;
import com.alcedomoreno.sirme.core.data.DataObject;


public class AddressBasicTransform extends DefaultTransformator implements Transformator{

	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
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
	public DataObject toDataObject(BusinessObject businessObject) {
		
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
		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
