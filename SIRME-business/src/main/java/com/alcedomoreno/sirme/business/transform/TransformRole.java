package com.alcedomoreno.sirme.business.transform;

import java.util.Collection;
import java.util.Set;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Permission;
import com.alcedomoreno.sirme.business.data.Role;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.PermissionData;
import com.alcedomoreno.sirme.core.data.RoleData;

public class TransformRole extends DefaultTransformator implements Transformator{

	@SuppressWarnings("unchecked")
	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		Role business = new Role();
		RoleData data = (RoleData) dataObject;
		
		business.setIdRole( data.getIdRole() );
		business.setCodeRole( data.getCodeRole() );
		business.setDescriptionRole( data.getDescriptionRole() );
		business.setURLSuccessLogin( data.getURLSuccessLogin() );		
		if( data.getPermissions() != null )
			business.setPermissions( (Collection<Permission>) TransformFactory.getTransformator(Permission.class).toBusinessObject(data.getPermissions()));
		return business;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		RoleData data = new RoleData();
		Role business = (Role) businessObject;
		
		data.setIdRole( business.getIdRole() );
		data.setCodeRole( business.getCodeRole() );
		data.setDescriptionRole( business.getDescriptionRole() );
		data.setURLSuccessLogin( business.getURLSuccessLogin() );	
		
		if( business.getPermissions() != null )
			data.setPermissions((Set<PermissionData>) TransformFactory.getTransformator(Permission.class).toDataObjectSet(business.getPermissions()));

		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}

}
