package com.sirme.basicsecurity.business.impl;
import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sirme.basicsecurity.SpringSecurityConstants;
import com.sirme.basicsecurity.business.IPermissionsService;
import com.sirme.basicsecurity.business.data.Permission;
import com.sirme.basicsecurity.dao.IPermissionsDao;
import com.sirme.basicsecurity.data.PermissionData;
import com.sirme.transform.TransformFactory;
import com.sirme.util.MyLogger;

@Service( SpringSecurityConstants.PERMISSION_SERVICE )
public class PermissionsService implements IPermissionsService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger(PermissionsService.class);
	private static final String CLASS_NAME = "PermissionsService";
	
	@Resource(name=SpringSecurityConstants.PERMISSION_DAO)
	IPermissionsDao permissionsDao;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
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
		MyLogger.info(log, "", PermissionsService.class.getName(), "getPermissions", "idApplication:[" + idApplication + "]", "START");

		Collection<PermissionData> persistentPerm = permissionsDao.get(idApplication);
		Collection<Permission> businessPerm = (Collection<Permission>)TransformFactory.getTransformator(Permission.class).toBusinessObject(persistentPerm);

		MyLogger.info(log, "", PermissionsService.class.getName(), "getAllPermissions", "idApplication:[" + idApplication + "]", "END");
		return businessPerm;
	}
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////
}
