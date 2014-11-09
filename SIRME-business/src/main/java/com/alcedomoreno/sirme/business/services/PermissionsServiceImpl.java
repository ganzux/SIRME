package com.alcedomoreno.sirme.business.services;
import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.business.data.Permission;
import com.alcedomoreno.sirme.business.transform.TransformFactory;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.dao.PermissionsDao;
import com.alcedomoreno.sirme.core.data.PermissionData;
import com.alcedomoreno.sirme.core.util.DAOConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Transactional(readOnly=true)
@Service( ServiceConstants.PERMISSION_SERVICE )
public class PermissionsServiceImpl implements PermissionsService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger(PermissionsServiceImpl.class);
	private static final String CLASS_NAME = "PermissionsServiceImpl";
	
	@Resource(name=DAOConstants.PERMISSION_DAO)
	PermissionsDao permissionsDao;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      M�todos P�blicos                     //
	///////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	public Collection<Permission> getAll(){
		MyLogger.info(log, CLASS_NAME, "getAllPermissions", "", "START");
		Collection<PermissionData> data = permissionsDao.getAll();
		
		if(data == null){
			MyLogger.info(log, CLASS_NAME, "getAllPermissions", "", "END");
			return null;
		}
			
		Collection<Permission> business = (Collection<Permission>) 
		TransformFactory.getTransformator(Permission.class).toBusinessObject(data);
		MyLogger.info(log, CLASS_NAME, "getAllPermissions", "", "END");
		
		return business;
		
	}
	
	public Permission get(String codePermission){
		MyLogger.info(log, CLASS_NAME, "getPermission", "codePermission="+codePermission, "START");
		PermissionData data = permissionsDao.get(codePermission);
		if(data == null){
			MyLogger.info(log, CLASS_NAME, "getPermission", "codePermission=null", "END");
			return null;
		}
		Permission business = (Permission) TransformFactory.getTransformator(Permission.class).toBusinessObject(data);
		MyLogger.info(log, CLASS_NAME, "getPermission", "codePermission="+codePermission, "END");
		return business;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Permission> get(int idApplication) {
		MyLogger.info(log, "", PermissionsServiceImpl.class.getName(), "getPermissions", "idApplication:[" + idApplication + "]", "START");

		Collection<PermissionData> persistentPerm = permissionsDao.get(idApplication);
		Collection<Permission> businessPerm = (Collection<Permission>)TransformFactory.getTransformator(Permission.class).toBusinessObject(persistentPerm);

		MyLogger.info(log, "", PermissionsServiceImpl.class.getName(), "getAllPermissions", "idApplication:[" + idApplication + "]", "END");
		return businessPerm;
	}
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los M�todos P�blicos               //
	///////////////////////////////////////////////////////////////
}
