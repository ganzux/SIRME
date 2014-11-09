package com.alcedomoreno.sirme.core.dao;


import java.util.Collection;

import com.alcedomoreno.sirme.core.data.RoleData;

public interface RolesDao{
    
	public RoleData getByCode(String codeRole);
	
	public RoleData getWithPermissions(int idRole);
	
	public RoleData get(int idRole);
			
	public Collection<RoleData> getAll();
 	
	public void save(RoleData profile); 	  	  	
 	
	public void delete(int idRole);
}
