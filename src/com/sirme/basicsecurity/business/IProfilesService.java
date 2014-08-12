package com.sirme.basicsecurity.business;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.basicsecurity.business.data.Profile;


public interface IProfilesService {
	
	@Transactional(readOnly=true)
	public Profile getByCode(String codeProfile);

	@Transactional(readOnly=true)
	public Profile get(int idProfile);
	
	@Transactional(readOnly=true)
	public Collection<Profile> getAll();
	
	@Transactional(readOnly=false)
	public void save(Profile profile); 	  	  	
	
	@Transactional(readOnly=false)
	public void delete(int idProfile);
}
