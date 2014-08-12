package com.sirme.basicsecurity.dao;

import java.util.Collection;

import com.sirme.basicsecurity.data.PermissionData;

public interface IPermissionsDao {

	public Collection<PermissionData> getAll();
	
	public PermissionData get(String codePermission);

	Collection<PermissionData> get(int idApplication);

}
