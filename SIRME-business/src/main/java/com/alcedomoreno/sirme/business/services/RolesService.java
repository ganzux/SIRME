package com.alcedomoreno.sirme.business.services;

import java.util.Collection;

import com.alcedomoreno.sirme.business.data.Role;


public interface RolesService {

	public Role getByCode(String codeProfile);
	public Role get(int idProfile);
	public Collection<Role> getAll();
	public void save(Role profile); 	  	  	
	public void delete(int idProfile);
	public Role getWithPermissions(int idRole);
}
