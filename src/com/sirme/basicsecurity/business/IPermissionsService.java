package com.sirme.basicsecurity.business;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.basicsecurity.business.data.Permission;

@Transactional(readOnly=true)
public interface IPermissionsService{

	public Collection<Permission> getAll();
	
	public Permission get(String codePermission);

	Collection<Permission> get(int idApplication);

}
