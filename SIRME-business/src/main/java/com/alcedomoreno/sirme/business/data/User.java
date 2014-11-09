package com.alcedomoreno.sirme.business.data;

import java.util.Collection;
import java.util.Date;

import com.alcedomoreno.sirme.business.transform.TransformUser;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class User implements BusinessObject,Cloneable{
	
	private int idUser;
	private String codeUser;
	private String nameUser;
	private String firstSurnameUser;
	private String secondSurnameUser;
	private String passwordUser;
	private boolean locked;
	private boolean enabled;	
	private Date addedDateUser;
	private Date expirationDateUser;	
	private Date expirationDateUserPassword;
	private Date lastAccess;
	
	private Collection<Profile> profiles;
	private Collection<Role> roles;
	
	private Collection<Customer> customersOfCommercial;
	
	private Profile profile;
	
	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getCodeUser() {
		return this.codeUser;
	}

	public void setCodeUser(String codeUser) {
		this.codeUser = codeUser;
	}

	public void setNameUser(String value) {
		this.nameUser = value;
	}
	
	public String getNameUser() {
		return nameUser;
	}
	
	public String getFirstSurnameUser() {
		return firstSurnameUser;
	}

	public void setFirstSurnameUser(String firstSurnameUser) {
		this.firstSurnameUser = firstSurnameUser;
	}

	public String getSecondSurnameUser() {
		return secondSurnameUser;
	}

	public void setSecondSurnameUser(String secondSurnameUser) {
		this.secondSurnameUser = secondSurnameUser;
	}

	public void setPasswordUser(String value) {
		this.passwordUser = value;
	}
	
	public String getPasswordUser() {
		return passwordUser;
	}	
	
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getAddedDateUser() {
		return addedDateUser;
	}

	public void setAddedDateUser(Date addedDateUser) {
		this.addedDateUser = addedDateUser;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public Date getExpirationDateUser() {
		return expirationDateUser;
	}

	public void setExpirationDateUser(Date expirationDateUser) {
		this.expirationDateUser = expirationDateUser;
	}

	public Date getExpirationDateUserPassword() {
		return expirationDateUserPassword;
	}

	public void setExpirationDateUserPassword(Date expirationDateUserPassword) {
		this.expirationDateUserPassword = expirationDateUserPassword;
	}
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	
	public Collection<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Collection<Profile> profiles) {
		this.profiles = profiles;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	public Collection<Customer> getCustomersOfCommercial() {
		return customersOfCommercial;
	}

	public void setCustomersOfCommercial(Collection<Customer> customersOfCommercial) {
		this.customersOfCommercial = customersOfCommercial;
	}

	public boolean isCommercial(){
		if ( roles!=null )
			for ( Role r:roles )
				if ( Role.CODE_COMMERCIAL.equals(r.getCodeRole() ) )
					return true;
		return false;
	}

	@Override
	public Class<? extends Transformator> getTransformator() {
		
		return TransformUser.class;
	}
	
	public User getLowerNullSafeCopy(){
		nameUser 	= (nameUser==null?"":nameUser.trim().toLowerCase());
		firstSurnameUser = (firstSurnameUser==null?"":firstSurnameUser.trim().toLowerCase());
		secondSurnameUser = (secondSurnameUser==null?"":secondSurnameUser.trim().toLowerCase());
		codeUser = (codeUser==null?"":codeUser.trim().toLowerCase());
		return this;
	}
	

}
