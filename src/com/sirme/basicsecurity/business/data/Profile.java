package com.sirme.basicsecurity.business.data;

import com.sirme.basicsecurity.transform.TransformProfile;
import com.sirme.bussiness.IBusinessObject;
import com.sirme.transform.ITransformator;

public class Profile implements IBusinessObject,Cloneable{

	private int idProfile;
	private String codeProfile;
	private String descriptionProfile;	

	
	public int getIdProfile() {
		return idProfile;
	}


	public void setIdProfile(int idProfile) {
		this.idProfile = idProfile;
	}


	public String getCodeProfile() {
		return codeProfile;
	}


	public void setCodeProfile(String codeProfile) {
		this.codeProfile = codeProfile;
	}


	public String getDescriptionProfile() {
		return descriptionProfile;
	}


	public void setDescriptionProfile(String descriptionProfile) {
		this.descriptionProfile = descriptionProfile;
	}


	@Override
	public Class<? extends ITransformator> getTransformator() {		
		return TransformProfile.class;
	}

}
