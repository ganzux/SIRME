package com.alcedomoreno.sirme.business.services;
import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.business.data.Role;
import com.alcedomoreno.sirme.business.transform.TransformFactory;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.dao.RolesDao;
import com.alcedomoreno.sirme.core.data.RoleData;
import com.alcedomoreno.sirme.core.util.DAOConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Transactional(readOnly=true)
@Service( ServiceConstants.ROLE_SERVICE )
public class RolesServiceImpl implements RolesService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static Logger log = LoggerFactory.getLogger(RolesServiceImpl.class);
	private static final String CLASS_NAME = "RolesServiceImpl";
	
	@Resource(name=DAOConstants.ROLE_DAO)
	RolesDao rolesDao;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      M�todos P�blicos                     //
	///////////////////////////////////////////////////////////////

	@Transactional(readOnly=false)
	@Override
	public void delete(int idRole) {
		
		MyLogger.info(log, CLASS_NAME, "deleteRole", "user=" + idRole, "START");

		rolesDao.delete(idRole);

		MyLogger.info(log, CLASS_NAME, "deleteRole", "user="+ idRole, "END");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Role> getAll() {
		
		MyLogger.info(log, "", "UsersServiceImpl", "getAllUsers", "", "START");
		Collection<RoleData> data = rolesDao.getAll();
		
		if(data == null){
			MyLogger.info(log, "", "UsersServiceImpl", "getAllUsers", "null", "END");
			return null;
		}
		
		Collection<Role> business = (Collection<Role>) 
		TransformFactory.getTransformator(Role.class).toBusinessObject(data);
		
		MyLogger.info(log, "", "UsersServiceImpl", "getAllUsers", "", "END");
		
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

	@Transactional(readOnly=false)
	@Override
	public void save(Role Role) {
		
		MyLogger.info(log, CLASS_NAME, "saveRole", "Role="+ Role.getIdRole(), "START");

		RoleData data = (RoleData) TransformFactory.getTransformator( Role.class).toDataObject(Role);
		rolesDao.save(data);

		MyLogger.info(log, CLASS_NAME, "saveRole", "Role="+Role.getIdRole(), "END");

	}

	///////////////////////////////////////////////////////////////
	//                 Fin de los M�todos P�blicos               //
	///////////////////////////////////////////////////////////////
}
