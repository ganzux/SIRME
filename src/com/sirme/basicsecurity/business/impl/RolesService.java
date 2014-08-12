package com.sirme.basicsecurity.business.impl;
import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sirme.basicsecurity.SpringSecurityConstants;
import com.sirme.basicsecurity.business.IRolesService;
import com.sirme.basicsecurity.business.data.Role;
import com.sirme.basicsecurity.dao.IRolesDao;
import com.sirme.basicsecurity.data.RoleData;
import com.sirme.transform.TransformFactory;
import com.sirme.util.MyLogger;

@Service( SpringSecurityConstants.ROLE_SERVICE )
public class RolesService implements IRolesService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger(RolesService.class);
	private static final String CLASS_NAME = "RolesService";
	
	@Resource(name=SpringSecurityConstants.ROLE_DAO)
	IRolesDao rolesDao;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////

	
	@Override
	public void delete(int idRole) {
		
		MyLogger.info(log, CLASS_NAME, "deleteRole", "user=" + idRole, "START");

		rolesDao.delete(idRole);

		MyLogger.info(log, CLASS_NAME, "deleteRole", "user="+ idRole, "END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Role> getAll() {
		
		MyLogger.info(log, "", "UsersService", "getAllUsers", "", "START");
		Collection<RoleData> data = rolesDao.getAll();
		
		if(data == null){
			MyLogger.info(log, "", "UsersService", "getAllUsers", "null", "END");
			return null;
		}
		
		Collection<Role> business = (Collection<Role>) 
		TransformFactory.getTransformator(Role.class).toBusinessObject(data);
		
		MyLogger.info(log, "", "UsersService", "getAllUsers", "", "END");
		
		return business;
	}

	@Override
	public Role getByCode(String codeRole) {
		MyLogger.info(log, CLASS_NAME, "getByCodeRole", "codeRole="+codeRole, "START");
		RoleData data = rolesDao.getByCode(codeRole);
		if(data == null){
			MyLogger.info(log, CLASS_NAME, "getByCodeRole", "codeRole=null", "END");
			return null;
		}
		Role business = (Role) TransformFactory.getTransformator(Role.class).toBusinessObject(data);
		MyLogger.info(log, CLASS_NAME, "getByCodeRole", "codeRole="+codeRole, "END");
		return business;
	}

	@Override
	public Role getWithPermissions(int idRole) {
		MyLogger.info(log, CLASS_NAME, "getRoleWithPermissions", "idRole="+idRole, "START");
		RoleData data = rolesDao.getWithPermissions(idRole);
		if(data == null){
			MyLogger.info(log, CLASS_NAME, "getRoleWithPermissions", "idRole=null", "END");
			return null;
		}
		Role business = (Role) TransformFactory.getTransformator(Role.class).toBusinessObject(data);
		MyLogger.info(log, CLASS_NAME, "getRoleWithPermissions", "idRole="+idRole, "END");
		return business;
	}

	@Override
	public Role get(int idRole) {
		MyLogger.info(log, CLASS_NAME, "getRole", "idRole="+idRole, "START");
		RoleData data = rolesDao.get(idRole);
		if(data == null){
			MyLogger.info(log, CLASS_NAME, "getRole", "idRole=null", "END");
			return null;
		}
		Role business = (Role) TransformFactory.getTransformator(Role.class).toBusinessObject(data);
		MyLogger.info(log, CLASS_NAME, "getRole", "idRole="+idRole, "END");
		return business;
	}
	
	@Override
	public void save(Role Role) {
		
		MyLogger.info(log, CLASS_NAME, "saveRole", "Role="+ Role.getIdRole(), "START");

		RoleData data = (RoleData) TransformFactory.getTransformator( Role.class).toDataObject(Role);
		rolesDao.save(data);

		MyLogger.info(log, CLASS_NAME, "saveRole", "Role="+Role.getIdRole(), "END");

	}

	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////
}
