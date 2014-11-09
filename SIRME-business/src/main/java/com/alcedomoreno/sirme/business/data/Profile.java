package com.alcedomoreno.sirme.business.data;

import com.alcedomoreno.sirme.business.transform.TransformProfile;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class Profile implements BusinessObject,Cloneable{

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
	public Class<? extends Transformator> getTransformator() {		
		return TransformProfile.class;
	}

}
