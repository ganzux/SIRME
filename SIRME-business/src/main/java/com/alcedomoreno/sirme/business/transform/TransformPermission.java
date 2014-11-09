package com.alcedomoreno.sirme.business.transform;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Permission;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.PermissionData;

public class TransformPermission extends DefaultTransformator implements Transformator{

	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		Permission business = new Permission();
		PermissionData data = (PermissionData) dataObject;
		
		business.setIdPermission(data.getIdPermission());
		business.setCodePermission(data.getCodePermission());
		business.setDescriptionPermission(data.getDescriptionPermission());

		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		PermissionData data = new PermissionData();
		Permission business = (Permission) businessObject;
		
		data.setIdPermission(business.getIdPermission());
		data.setCodePermission(business.getCodePermission());
		data.setDescriptionPermission(business.getDescriptionPermission());
		
		return data;
	}
	
	public void copy(BusinessObject source, BusinessObject target) {
		
		Permission dest = (Permission) target;
		Permission src = (Permission) source;
		
		dest.setIdPermission(src.getIdPermission());	
		dest.setDescriptionPermission(src.getDescriptionPermission());
		dest.setCodePermission(src.getCodePermission());
		
	}

}
