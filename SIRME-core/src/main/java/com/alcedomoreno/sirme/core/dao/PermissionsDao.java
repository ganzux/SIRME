package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;

import com.alcedomoreno.sirme.core.data.PermissionData;

public interface PermissionsDao {

	public Collection<PermissionData> getAll();
	
	public PermissionData get(String codePermission);

	Collection<PermissionData> get(int idApplication);

}
