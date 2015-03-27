package com.alcedomoreno.sirme.core.dao;


import java.util.Collection;

import com.alcedomoreno.sirme.core.dao.common.Operations;
import com.alcedomoreno.sirme.core.data.RoleData;

public interface RolesDao extends Operations<RoleData> {
    
	public RoleData getByCode(String codeRole);
	
	public RoleData get(int idRole);
			
	public Collection<RoleData> getAll();
 	
	public void save(RoleData profile); 	  	  	
 	
	public int delete(int idRole);
}
