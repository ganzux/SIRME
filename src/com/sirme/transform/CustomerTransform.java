package com.sirme.transform;

import com.sirme.bussiness.Customer;
import com.sirme.bussiness.IBusinessObject;
import com.sirme.bussiness.TypeCustomer;
import com.sirme.data.CustomerData;
import com.sirme.data.IDataObject;

public class CustomerTransform extends DefaultTransformator implements ITransformator{

	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		Customer business = new Customer();
		CustomerData data = (CustomerData) dataObject;

		business.setIdCustomer( data.getIdCustomer() );
		business.setCifCustomer( data.getCifCustomer() );
		try{ business.setCodeCustomer( String.valueOf(data.getCodeCustomer()) );}catch(Exception e){}
		business.setNameCustomer( data.getNameCustomer() );
		business.setMainAddress( data.getMainAddress() );
		business.setMainMail( data.getMainMail() );
		business.setMainPhone( data.getMainPhone() );
		
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
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
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
		try{
			data.setTypeCustomer( business.getTypeCustomer().getIdTypeCustomer() );
		} catch ( Exception e ){
			data.setTypeCustomer( 0 );
		}
		data.setMainPobl( business.getMainPobl() );
		data.setMainProv( business.getMainProv() );
		try {data.setMainPostalCode( Integer.valueOf( business.getMainPostalCode() ) );}catch(Exception e){}

		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
