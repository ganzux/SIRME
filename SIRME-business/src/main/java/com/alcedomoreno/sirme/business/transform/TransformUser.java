package com.alcedomoreno.sirme.business.transform;

import java.util.Set;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Profile;
import com.alcedomoreno.sirme.business.data.Role;
import com.alcedomoreno.sirme.business.data.User;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.ProfileData;
import com.alcedomoreno.sirme.core.data.RoleData;
import com.alcedomoreno.sirme.core.data.UserData;

public class TransformUser extends DefaultTransformator implements Transformator{

	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		User business = new User();
		UserData data = (UserData) dataObject;
		business.setIdUser(data.getIdUser());
		business.setCodeUser(data.getCodeUser());
		business.setNameUser(data.getNameUser());
		business.setFirstSurnameUser(data.getFirstSurnameUser());
		business.setSecondSurnameUser(data.getSecondSurnameUser());
		business.setPasswordUser(data.getPasswordUser());
		business.setLocked(data.isLocked());
		business.setEnabled(data.isEnabled());
		business.setAddedDateUser(data.getAddedDateUser());
		business.setExpirationDateUser(data.getExpirationDateUser());
		business.setExpirationDateUserPassword(data.getExpirationDateUserPassword());
		business.setLastAccess( data.getLastAccess() );

		/*if( data.getProfiles() != null )
			business.setProfiles( (Collection<Profile>) TransformFactory.getTransformator(Profile.class).toBusinessObject(data.getProfiles()));
		if( data.getRoles() != null )
			business.setRoles( (Collection<Role>) TransformFactory.getTransformator(Role.class).toBusinessObject(data.getRoles()));*/
			

		return business;
	}


	@SuppressWarnings("unchecked")
	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
		UserData data = new UserData();
		User business = (User) businessObject;
		data.setIdUser(business.getIdUser());
		data.setCodeUser(business.getCodeUser());
		data.setNameUser(business.getNameUser());
		data.setFirstSurnameUser(business.getFirstSurnameUser());
		data.setSecondSurnameUser(business.getSecondSurnameUser());
		data.setPasswordUser(business.getPasswordUser());
		data.setLocked(business.isLocked());
		data.setEnabled(business.isEnabled());
		data.setAddedDateUser(business.getAddedDateUser());
		data.setExpirationDateUser(business.getExpirationDateUser());
		data.setExpirationDateUserPassword(business.getExpirationDateUserPassword());
		data.setLastAccess( business.getLastAccess() );
		
		if( business.getProfiles() != null )
			data.setProfiles( (Set<ProfileData>) TransformFactory.getTransformator(Profile.class).toDataObjectSet( business.getProfiles() ) );
		
		if( business.getRoles() != null )
			data.setRoles( (Set<RoleData>) TransformFactory.getTransformator(Role.class).toDataObjectSet( business.getRoles() ) );

		return data;
	}


	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}

}
