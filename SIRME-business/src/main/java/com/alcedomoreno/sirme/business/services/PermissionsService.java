package com.alcedomoreno.sirme.business.services;

import java.util.Collection;

import com.alcedomoreno.sirme.business.data.Permission;

public interface PermissionsService{

	public Collection<Permission> getAll();
	
	public Permission get(String codePermission);

	Collection<Permission> get(int idApplication);

}
