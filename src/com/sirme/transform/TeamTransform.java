package com.sirme.transform;

import java.util.Collection;
import java.util.HashSet;

import com.sirme.basicsecurity.business.data.User;
import com.sirme.bussiness.IBusinessObject;
import com.sirme.bussiness.Team;
import com.sirme.data.IDataObject;
import com.sirme.data.TeamData;

public class TeamTransform extends DefaultTransformator implements ITransformator{

	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		Team business = new Team();
		TeamData data = (TeamData) dataObject;

		business.setIdTeam( data.getIdTeam() );
		business.setNameTeam( data.getNameTeam() );
		business.setPassWord( data.getPassWord() );
		business.setPhoneNumber( data.getPhoneNumber() );
		business.setEnabled( data.isEnabled() );
		business.setCanUploadPhotos( data.isCanUploadPhotos() );
		business.setUsers( (Collection<User>)TransformFactory.getTransformator(User.class).toBusinessObject( data.getUsers() ) );

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
		Team business = (Team) businessObject;
		TeamData data = new TeamData();

		data.setIdTeam( business.getIdTeam() );
		data.setNameTeam( business.getNameTeam() );
		data.setPassWord( business.getPassWord() );
		data.setPhoneNumber( business.getPhoneNumber() );
		data.setEnabled( business.isEnabled() );
		data.setCanUploadPhotos( business.isCanUploadPhotos() );
		data.setUsers( new HashSet( (Collection<User>)TransformFactory.getTransformator(User.class).toDataObject( business.getUsers() ) ) );

		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
