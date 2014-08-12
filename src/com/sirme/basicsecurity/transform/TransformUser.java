package com.sirme.basicsecurity.transform;

import java.util.Set;

import com.sirme.basicsecurity.business.data.Profile;
import com.sirme.basicsecurity.business.data.Role;
import com.sirme.basicsecurity.business.data.User;
import com.sirme.basicsecurity.data.ProfileData;
import com.sirme.basicsecurity.data.RoleData;
import com.sirme.basicsecurity.data.UserData;
import com.sirme.bussiness.IBusinessObject;
import com.sirme.data.IDataObject;
import com.sirme.transform.DefaultTransformator;
import com.sirme.transform.ITransformator;
import com.sirme.transform.TransformFactory;

public class TransformUser extends DefaultTransformator implements ITransformator{

	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
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
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
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
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}

}
