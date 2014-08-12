package com.sirme.basicsecurity.transform;

import com.sirme.basicsecurity.business.data.Permission;
import com.sirme.basicsecurity.data.PermissionData;
import com.sirme.bussiness.IBusinessObject;
import com.sirme.data.IDataObject;
import com.sirme.transform.DefaultTransformator;
import com.sirme.transform.ITransformator;

public class TransformPermission extends DefaultTransformator implements ITransformator{

	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		Permission business = new Permission();
		PermissionData data = (PermissionData) dataObject;
		
		business.setIdPermission(data.getIdPermission());
		business.setCodePermission(data.getCodePermission());
		business.setDescriptionPermission(data.getDescriptionPermission());

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		PermissionData data = new PermissionData();
		Permission business = (Permission) businessObject;
		
		data.setIdPermission(business.getIdPermission());
		data.setCodePermission(business.getCodePermission());
		data.setDescriptionPermission(business.getDescriptionPermission());
		
		return data;
	}
	
	public void copy(IBusinessObject source, IBusinessObject target) {
		
		Permission dest = (Permission) target;
		Permission src = (Permission) source;
		
		dest.setIdPermission(src.getIdPermission());	
		dest.setDescriptionPermission(src.getDescriptionPermission());
		dest.setCodePermission(src.getCodePermission());
		
	}

}
