package com.alcedomoreno.sirme.business.transform;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Profile;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.ProfileData;

public class TransformProfile extends DefaultTransformator implements Transformator{

	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		Profile business = new Profile();
		ProfileData data = (ProfileData) dataObject;
		
		business.setIdProfile(data.getIdProfile());
		business.setCodeProfile(data.getCodeProfile());
		business.setDescriptionProfile(data.getDescriptionProfile());
		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		ProfileData data = new ProfileData();
		Profile business = (Profile) businessObject;
		
		data.setIdProfile(business.getIdProfile());
		data.setCodeProfile(business.getCodeProfile());
		data.setDescriptionProfile(business.getDescriptionProfile());

		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}

}
