package com.sirme.basicsecurity.business;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.basicsecurity.business.data.Role;


public interface IRolesService {
	
	@Transactional(readOnly=true)
	public Role getByCode(String codeProfile);

	@Transactional(readOnly=true)
	public Role get(int idProfile);
	
	@Transactional(readOnly=true)
	public Collection<Role> getAll();
	
	@Transactional(readOnly=false)
	public void save(Role profile); 	  	  	
	
	@Transactional(readOnly=false)
	public void delete(int idProfile);
	
	@Transactional(readOnly=true)
	public Role getWithPermissions(int idRole);
}
