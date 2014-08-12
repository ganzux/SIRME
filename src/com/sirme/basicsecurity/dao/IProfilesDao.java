package com.sirme.basicsecurity.dao;


import java.util.Collection;

import com.sirme.basicsecurity.data.ProfileData;

public interface IProfilesDao{
    
	public ProfileData getByCode(String codeProfile);

	public ProfileData get(int idProfile);
			
	public Collection<ProfileData> getAll();
 	
	public void save(ProfileData profile); 	  	  	
 	
	public void delete(int idProfile);
}
