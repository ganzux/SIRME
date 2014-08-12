package com.sirme.basicsecurity.business.data;

import java.util.Collection;

import com.sirme.basicsecurity.transform.TransformRole;
import com.sirme.bussiness.IBusinessObject;
import com.sirme.transform.ITransformator;

public class Role implements IBusinessObject,Cloneable{

	public static final String CODE_COMMERCIAL = "CM";
	public static final String CODE_TECHNICS = "TCN";
	
	private int idRole;
	private String codeRole;
	private String descriptionRole;	
	private String URLSuccessLogin;
	private Collection<Permission> permissions; 
	
	public String getURLSuccessLogin() {
		return URLSuccessLogin;
	}


	public int getIdRole() {
		return idRole;
	}


	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}


	public String getCodeRole() {
		return codeRole;
	}


	public void setCodeRole(String codeRole) {
		this.codeRole = codeRole;
	}


	public String getDescriptionRole() {
		return descriptionRole;
	}


	public void setDescriptionRole(String descriptionRole) {
		this.descriptionRole = descriptionRole;
	}


	public void setURLSuccessLogin(String uRLSuccessLogin) {
		URLSuccessLogin = uRLSuccessLogin;
	}


	public Collection<Permission> getPermissions() {
		return permissions;
	}


	public void setPermissions(Collection<Permission> permissions) {
		this.permissions = permissions;
	}


	@Override
	public Class<? extends ITransformator> getTransformator() {		
		return TransformRole.class;
	}

}
