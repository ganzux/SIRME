package com.alcedomoreno.sirme.business.transform;

import java.util.Collection;
import java.util.HashSet;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Team;
import com.alcedomoreno.sirme.business.data.User;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.TeamData;

public class TeamTransform extends DefaultTransformator implements Transformator{

	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
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

	@SuppressWarnings("rawtypes")
	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
		Team business = (Team) businessObject;
		TeamData data = new TeamData();

		data.setIdTeam( business.getIdTeam() );
		data.setNameTeam( business.getNameTeam() );
		data.setPassWord( business.getPassWord() );
		data.setPhoneNumber( business.getPhoneNumber() );
		data.setEnabled( business.isEnabled() );
		data.setCanUploadPhotos( business.isCanUploadPhotos() );
		// TODO error raro
		//data.setUsers( new HashSet( (Collection<User>)TransformFactory.getTransformator(User.class).toDataObject( business.getUsers() ) ) );

		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
