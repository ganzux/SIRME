package com.alcedomoreno.sirme.business.services;

import java.util.Collection;

import com.alcedomoreno.sirme.business.data.Profile;

public interface ProfilesService {

	public Profile getByCode(String codeProfile);
	public Profile get(int idProfile);
	public Collection<Profile> getAll();
	public void save(Profile profile); 	  	  	
	public void delete(int idProfile);
}
