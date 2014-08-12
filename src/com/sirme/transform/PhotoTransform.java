package com.sirme.transform;

import com.sirme.bussiness.IBusinessObject;
import com.sirme.bussiness.Photo;
import com.sirme.bussiness.Work;
import com.sirme.data.IDataObject;
import com.sirme.data.PhotoData;
import com.sirme.data.WorkData;

public class PhotoTransform extends DefaultTransformator implements ITransformator{
	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		Photo business = new Photo();
		PhotoData data = (PhotoData) dataObject;

		business.setIdPhoto( data.getIdPhoto() );
		business.setDateCreated( data.getDateCreated() );
		business.setComments( data.getComments());
		business.setPath( data.getPath() );

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
		Photo business = (Photo) businessObject;
		PhotoData data = new PhotoData();

		data.setIdPhoto( business.getIdPhoto() );
		data.setDateCreated( business.getDateCreated() );
		data.setComments( business.getComments());
		data.setPath( business.getPath() );

		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
