package com.sirme.basicsecurity.business.impl;
import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sirme.basicsecurity.SpringSecurityConstants;
import com.sirme.basicsecurity.business.IProfilesService;
import com.sirme.basicsecurity.business.data.Profile;
import com.sirme.basicsecurity.dao.IProfilesDao;
import com.sirme.basicsecurity.data.ProfileData;
import com.sirme.transform.TransformFactory;
import com.sirme.util.MyLogger;

@Service( SpringSecurityConstants.PROFILE_SERVICE )
public class ProfilesService implements IProfilesService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger(ProfilesService.class);
	private static final String CLASS_NAME = "ProfilesService";
	
	@Resource(name=SpringSecurityConstants.PROFILE_DAO)
	IProfilesDao profilesDao;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////

	@Override
	public void delete(int idProfile) {
		
		MyLogger.info(log, CLASS_NAME, "deleteProfile", "user=" + idProfile, "START");

		profilesDao.delete(idProfile);

		MyLogger.info(log, CLASS_NAME, "deleteProfile", "user=" + idProfile, "END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Profile> getAll() {
		
		MyLogger.info(log, "", "UsersService", "getAllUsers", "", "START");
		Collection<ProfileData> data = profilesDao.getAll();
		
		if(data == null){
			MyLogger.info(log, "", "UsersService", "getAllUsers", "null", "END");
			return null;
		}
		
		Collection<Profile> business = (Collection<Profile>) 
		TransformFactory.getTransformator(Profile.class).toBusinessObject(data);
		
		MyLogger.info(log, "", "UsersService", "getAllUsers", "", "END");
		
		return business;
	}

	@Override
	public Profile getByCode(String codeProfile) {
		MyLogger.info(log, CLASS_NAME, "getByCodeProfile", "codeProfile="+codeProfile, "START");
		ProfileData data = profilesDao.getByCode(codeProfile);
		if(data == null){
			MyLogger.info(log, CLASS_NAME, "getByCodeProfile", "codeProfile=null", "END");
			return null;
		}
		Profile business = (Profile) TransformFactory.getTransformator(Profile.class).toBusinessObject(data);
		MyLogger.info(log, CLASS_NAME, "getByCodeProfile", "codeProfile="+codeProfile, "END");
		return business;
	}

	@Override
	public Profile get(int idProfile) {
		MyLogger.info(log, CLASS_NAME, "getProfile", "idProfile="+idProfile, "START");
		ProfileData data = profilesDao.get(idProfile);
		if(data == null){
			MyLogger.info(log, CLASS_NAME, "getProfile", "idProfile=null", "END");
			return null;
		}
		Profile business = (Profile) TransformFactory.getTransformator(Profile.class).toBusinessObject(data);
		MyLogger.info(log, CLASS_NAME, "getProfile", "idProfile="+idProfile, "END");
		return business;
	}
	
	@Override
	public void save(Profile profile) {
		
		MyLogger.info(log, CLASS_NAME, "saveProfile", "profile=" + profile.getIdProfile(), "START");

		ProfileData data = (ProfileData) TransformFactory.getTransformator( Profile.class).toDataObject(profile);
		profilesDao.save(data);

		MyLogger.info(log, CLASS_NAME, "saveProfile", "profile=" +profile.getIdProfile(), "END");

	}	

	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////
}
