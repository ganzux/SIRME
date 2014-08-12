package com.sirme.basicsecurity.transform;

import com.sirme.basicsecurity.business.data.Profile;
import com.sirme.basicsecurity.data.ProfileData;
import com.sirme.bussiness.IBusinessObject;
import com.sirme.data.IDataObject;
import com.sirme.transform.DefaultTransformator;
import com.sirme.transform.ITransformator;

public class TransformProfile extends DefaultTransformator implements ITransformator{

	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		Profile business = new Profile();
		ProfileData data = (ProfileData) dataObject;
		
		business.setIdProfile(data.getIdProfile());
		business.setCodeProfile(data.getCodeProfile());
		business.setDescriptionProfile(data.getDescriptionProfile());
		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		ProfileData data = new ProfileData();
		Profile business = (Profile) businessObject;
		
		data.setIdProfile(business.getIdProfile());
		data.setCodeProfile(business.getCodeProfile());
		data.setDescriptionProfile(business.getDescriptionProfile());

		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}

}
