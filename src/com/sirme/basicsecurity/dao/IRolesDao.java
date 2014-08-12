package com.sirme.basicsecurity.dao;


import java.util.Collection;

import com.sirme.basicsecurity.data.RoleData;

public interface IRolesDao{
    
	public RoleData getByCode(String codeRole);
	
	public RoleData getWithPermissions(int idRole);
	
	public RoleData get(int idRole);
			
	public Collection<RoleData> getAll();
 	
	public void save(RoleData profile); 	  	  	
 	
	public void delete(int idRole);
}
