package com.alcedomoreno.sirme.business.transform;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Photo;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.PhotoData;

public class PhotoTransform extends DefaultTransformator implements Transformator{
	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
		Photo business = new Photo();
		PhotoData data = (PhotoData) dataObject;

		business.setIdPhoto( data.getIdPhoto() );
		business.setDateCreated( data.getDateCreated() );
		business.setComments( data.getComments());
		business.setPath( data.getPath() );

		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
		Photo business = (Photo) businessObject;
		PhotoData data = new PhotoData();

		data.setIdPhoto( business.getIdPhoto() );
		data.setDateCreated( business.getDateCreated() );
		data.setComments( business.getComments());
		data.setPath( business.getPath() );

		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
