package com.sirme.transform;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.sirme.bussiness.Address;
import com.sirme.bussiness.Customer;
import com.sirme.bussiness.IBusinessObject;
import com.sirme.bussiness.Photo;
import com.sirme.bussiness.Team;
import com.sirme.bussiness.TypeWork;
import com.sirme.bussiness.Work;
import com.sirme.data.AddressData;
import com.sirme.data.IDataObject;
import com.sirme.data.PhotoData;
import com.sirme.data.TeamData;
import com.sirme.data.WorkData;

public class WorkTransform extends DefaultTransformator implements ITransformator{
	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
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
		if ( data.getPhotos() != null )
			business.setPhotos( (Collection<Photo>) TransformFactory.getTransformator(Photo.class).toBusinessObject( data.getPhotos() ) );

		try{ business.setStatus( String.valueOf( data.getStatus() ) ); } catch(Exception e){}

		business.setAddress( (Address) TransformFactory.getTransformator(Address.class).toBusinessObject( data.getAddress() ) );
		business.setCustomer( (Customer) TransformFactory.getTransformator(Customer.class).toBusinessObject( data.getAddress().getCustomer() ) );
		
		if ( data.getTeam() != null )
			business.setTeam( (Team) TransformFactory.getTransformator(Team.class).toBusinessObject( data.getTeam() ) );
		
		business.setDateCreated( data.getDateCreated() );
		
		business.setTypeWork( new TypeWork( data.getTypeWork() ) );

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
		Work business = (Work) businessObject;
		WorkData data = new WorkData();

		data.setDate( business.getDate() );
		data.setMemo( business.getMemo() );
		try{ data.setStatus( Integer.valueOf( business.getStatus() ) ); } catch(Exception e){}

		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(  business.getDate() );

		data.setData( business.getData() );
		
		data.setIdWork( business.getIdWork() );
		data.setAlbaran( business.getAlbaran() );
		data.setYear( calendar.get(Calendar.YEAR) );

		data.setDateCreated( business.getDateCreated() );
		
		data.setSignpath( business.getSignpath() );
		data.setSignName( business.getSignName() );
		if ( business.getPhotos() != null ){
			Collection<Photo> photos = business.getPhotos();
			Set<PhotoData> photosData = new HashSet<PhotoData>();
			
			for ( Photo ph:photos ){
				PhotoData pd = (PhotoData) TransformFactory.getTransformator(Photo.class).toDataObject( ph );
				pd.setWork( data );
				photosData.add( pd );
			}
			data.setPhotos( photosData );
		}

		data.setAddress(  (AddressData) TransformFactory.getTransformator(Address.class).toDataObject( business.getAddress() ) );
		if ( business.getTeam() != null)
			data.setTeam( (TeamData) TransformFactory.getTransformator(Team.class).toDataObject( business.getTeam() ) );

		data.setTypeWork( business.getTypeWork().getIdTypeWork() );
		
		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
