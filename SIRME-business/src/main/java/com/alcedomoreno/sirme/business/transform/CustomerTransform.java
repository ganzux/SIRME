package com.alcedomoreno.sirme.business.transform;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Customer;
import com.alcedomoreno.sirme.business.data.TypeCustomer;
import com.alcedomoreno.sirme.core.data.CustomerData;
import com.alcedomoreno.sirme.core.data.DataObject;

public class CustomerTransform extends DefaultTransformator implements Transformator{

	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
		Customer business = new Customer();
		CustomerData data = (CustomerData) dataObject;

		business.setIdCustomer(data.getIdCustomer());
		business.setCifCustomer(data.getCifCustomer());
		try{ business.setCodeCustomer(String.valueOf(data.getCodeCustomer()));}catch(Exception e){}
		business.setNameCustomer(data.getNameCustomer());
		business.setMainAddress(data.getMainAddress());
		business.setMainMail(data.getMainMail());
		business.setMainPhone(data.getMainPhone());
		business.setDateCreated(data.getDateCreated());
		
		try {
			business.setTypeCustomer( new TypeCustomer(data.getTypeCustomer().intValue()) );
		} catch ( Exception e ){
			business.setTypeCustomer( new TypeCustomer() );
		}
		business.setMainPobl( data.getMainPobl() );
		business.setMainProv( data.getMainProv() );
		business.setMainPostalCode( String.valueOf( data.getMainPostalCode() ) );
		business.setActive( data.getActive() );

		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
		Customer business = (Customer) businessObject;
		CustomerData data = new CustomerData();

		data.setIdCustomer( business.getIdCustomer() );
		data.setCifCustomer( business.getCifCustomer() );
		try{ data.setCodeCustomer( Integer.valueOf(business.getCodeCustomer()) );}catch(Exception e){}
		data.setNameCustomer( business.getNameCustomer() );
		data.setMainAddress( business.getMainAddress() );
		data.setMainMail( business.getMainMail() );
		data.setMainPhone( business.getMainPhone() );
		data.setActive( business.getActive() );
		data.setDateCreated(business.getDateCreated());
		
		try{
			data.setTypeCustomer( business.getTypeCustomer().getIdTypeCustomer() );
		} catch ( Exception e ){
			data.setTypeCustomer( 0 );
		}
		data.setMainPobl(business.getMainPobl());
		data.setMainProv(business.getMainProv());
		try {data.setMainPostalCode(business.getMainPostalCode());}catch(Exception e){}

		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
