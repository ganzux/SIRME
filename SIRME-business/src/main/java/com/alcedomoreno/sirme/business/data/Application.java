package com.alcedomoreno.sirme.business.data;



import java.util.Collection;

import com.alcedomoreno.sirme.business.transform.TransformApplication;
import com.alcedomoreno.sirme.business.transform.Transformator;


public class Application implements BusinessObject,Cloneable {
	
	public static final byte ROOT_LEVEL = 0;
	public static final byte MAX_LEVEL = 3;
	
	private int idApplication;
	private String codeApplication;
	private String nameApplication;
	private byte levelApplication;
	
	private Collection<Permission> permissions;
	
	private Application mainApplication;
	private Collection<Application> childApplications;

	public int getIdApplication() {
		return this.idApplication;
	}

	public void setIdApplication(int id) {
		this.idApplication = id;
	}

	public String getCodeApplication() {
		return this.codeApplication;
	}

	public void setCodeApplication(String code) {
		this.codeApplication = code;
	}
	
	public String getNameApplication() {
		return this.nameApplication;
	}

	public void setNameApplication(String name) {
		this.nameApplication = name;
	}
	
	public byte getLevelApplication() {
		return this.levelApplication;
	}
	
	public void setLevelApplication(byte value) {
		this.levelApplication = value;
	}
	
	public Application getMainApplication() {
		return this.mainApplication;
	}
	
	public void setMainApplication(Application app) {
		this.mainApplication = app;
	}
	
	public Collection<Application> getChildApplications() {
		return this.childApplications;
	}

	public void setChildApplications(Collection<Application> apps) {
		this.childApplications = apps;
	}
	
	public Collection<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(Collection<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public Class<? extends Transformator> getTransformator() {
		
		return TransformApplication.class;
	}
	
}
