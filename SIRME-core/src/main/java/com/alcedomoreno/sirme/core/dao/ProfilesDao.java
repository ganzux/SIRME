package com.alcedomoreno.sirme.core.dao;


import java.util.Collection;

import com.alcedomoreno.sirme.core.data.ProfileData;

public interface ProfilesDao{
    
	public ProfileData getByCode(String codeProfile);

	public ProfileData get(int idProfile);
			
	public Collection<ProfileData> getAll();
 	
	public void save(ProfileData profile); 	  	  	
 	
	public void delete(int idProfile);
}
