package com.alcedomoreno.sirme.business.transform;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.alcedomoreno.sirme.business.data.Address;
import com.alcedomoreno.sirme.business.data.AddressBasic;
import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Photo;
import com.alcedomoreno.sirme.business.data.Team;
import com.alcedomoreno.sirme.business.data.TypeWork;
import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.core.data.AddressData;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.PhotoData;
import com.alcedomoreno.sirme.core.data.TeamData;
import com.alcedomoreno.sirme.core.data.WorkData;

public class WorkBasicTransform extends DefaultTransformator implements Transformator{
	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
		Work business = new Work();
		WorkData data = (WorkData) dataObject;

		business.setIdWork( data.getIdWork() );
		business.setYear( data.getYear() );
		business.setAlbaran( data.getAlbaran() );
		business.setDate( data.getDate() );
		business.setData( data.getData() );
		business.setMemo( data.getMemo() );

		business.setSignpath( data.getSignpath() );
		business.setSignName( data.getSignName() );

		try{ business.setStatus( String.valueOf( data.getStatus() ) ); } catch(Exception e){}

		business.setAddress( (Address) TransformFactory.getTransformator(Address.class).toBusinessObject( data.getAddress() ) );
//		business.setCustomer( (Customer) TransformFactory.getTransformator(Customer.class).toBusinessObject( data.getAddress().getCustomer() ) );

		if ( data.getTeam() != null )
			business.setTeam( (Team) TransformFactory.getTransformator(Team.class).toBusinessObject( data.getTeam() ) );

		business.setDateCreated( data.getDateCreated() );

		business.setTypeWork( new TypeWork( data.getTypeWork() ) );

		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		return null;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
