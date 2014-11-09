package com.alcedomoreno.sirme.business.services;
import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.business.data.Profile;
import com.alcedomoreno.sirme.business.transform.TransformFactory;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.dao.ProfilesDao;
import com.alcedomoreno.sirme.core.data.ProfileData;
import com.alcedomoreno.sirme.core.util.DAOConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Transactional(readOnly=true)
@Service( ServiceConstants.PROFILE_SERVICE )
public class ProfilesServiceImpl implements ProfilesService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger(ProfilesServiceImpl.class);
	private static final String CLASS_NAME = "ProfilesServiceImpl";
	
	@Resource(name=DAOConstants.PROFILE_DAO)
	ProfilesDao profilesDao;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      M�todos P�blicos                     //
	///////////////////////////////////////////////////////////////

	@Transactional(readOnly=false)
	@Override
	public void delete(int idProfile) {
		
		MyLogger.info(log, CLASS_NAME, "deleteProfile", "user=" + idProfile, "START");

		profilesDao.delete(idProfile);

		MyLogger.info(log, CLASS_NAME, "deleteProfile", "user=" + idProfile, "END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Profile> getAll() {
		
		MyLogger.info(log, "", "UsersServiceImpl", "getAllUsers", "", "START");
		Collection<ProfileData> data = profilesDao.getAll();
		
		if(data == null){
			MyLogger.info(log, "", "UsersServiceImpl", "getAllUsers", "null", "END");
			return null;
		}
		
		Collection<Profile> business = (Collection<Profile>) 
		TransformFactory.getTransformator(Profile.class).toBusinessObject(data);
		
		MyLogger.info(log, "", "UsersServiceImpl", "getAllUsers", "", "END");
		
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

	@Transactional(readOnly=false)
	@Override
	public void save(Profile profile) {
		
		MyLogger.info(log, CLASS_NAME, "saveProfile", "profile=" + profile.getIdProfile(), "START");

		ProfileData data = (ProfileData) TransformFactory.getTransformator( Profile.class).toDataObject(profile);
		profilesDao.save(data);

		MyLogger.info(log, CLASS_NAME, "saveProfile", "profile=" +profile.getIdProfile(), "END");

	}	

	///////////////////////////////////////////////////////////////
	//                 Fin de los M�todos P�blicos               //
	///////////////////////////////////////////////////////////////
}
