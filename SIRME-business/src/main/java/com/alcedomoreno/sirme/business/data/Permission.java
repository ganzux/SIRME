package com.alcedomoreno.sirme.business.data;

import com.alcedomoreno.sirme.business.transform.TransformPermission;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class Permission implements BusinessObject,Cloneable{
	
	private int idPermission;
	private String codePermission;
	private String descriptionPermission;
	
	private Application application;
	
	public int getIdPermission() {
		return idPermission;
	}
	public void setIdPermission(int idPermission) {
		this.idPermission = idPermission;
	}
	public String getCodePermission() {
		return codePermission;
	}
	public void setCodePermission(String codePermission) {
		this.codePermission = codePermission;
	}
	public String getDescriptionPermission() {
		return descriptionPermission;
	}
	public void setDescriptionPermission(String descriptionPermission) {
		this.descriptionPermission = descriptionPermission;
	}	
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}

	@Override
	public Class<? extends Transformator> getTransformator() {

		return TransformPermission.class;
	}
	
	
	

}
