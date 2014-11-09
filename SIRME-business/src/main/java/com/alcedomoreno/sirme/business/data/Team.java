package com.alcedomoreno.sirme.business.data;

import java.util.Collection;


import com.alcedomoreno.sirme.business.transform.TeamTransform;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class Team implements BusinessObject,Cloneable{

	private int idTeam;
	private String nameTeam;
	private String passWord;
	private String phoneNumber;
	private boolean enabled;
	private boolean canUploadPhotos;
	private Collection<User> users;

	public int getIdTeam() {
		return idTeam;
	}
	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}
	public String getNameTeam() {
		return nameTeam;
	}
	public void setNameTeam(String nameTeam) {
		this.nameTeam = nameTeam;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Collection<User> getUsers() {
		return users;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	
	public String getNameTec(){
		StringBuilder sb = new StringBuilder();
		if ( users != null )
			for ( User u:users )
				sb.append( u.getNameUser() ).append(" ").append( u.getFirstSurnameUser() ).append(" ").append( u.getSecondSurnameUser() ).append(", ");
		return sb.toString().substring(0, sb.length()-2);
	}
	

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString(){
		return idTeam + "," + nameTeam + "," + phoneNumber + "," + enabled;
	}

	@Override
	public Class<? extends Transformator> getTransformator() {
		return TeamTransform.class;
	}
	public boolean isCanUploadPhotos() {
		return canUploadPhotos;
	}
	public void setCanUploadPhotos(boolean canUploadPhotos) {
		this.canUploadPhotos = canUploadPhotos;
	}
}
