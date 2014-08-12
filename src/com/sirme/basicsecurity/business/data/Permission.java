package com.sirme.basicsecurity.business.data;

import com.sirme.basicsecurity.transform.TransformPermission;
import com.sirme.bussiness.IBusinessObject;
import com.sirme.transform.ITransformator;

public class Permission implements IBusinessObject,Cloneable{
	
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
	public Class<? extends ITransformator> getTransformator() {

		return TransformPermission.class;
	}
	
	
	

}
