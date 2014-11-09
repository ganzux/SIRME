package com.alcedomoreno.sirme.core.data;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@org.hibernate.annotations.Proxy(lazy=true)
@Table(name="users")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class UserData implements DataObject {


	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof UserData))
			return false;
		UserData userData = (UserData) aObj;
		if (getIdUser() != userData.getIdUser())
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		hashcode = hashcode + getIdUser();
		return hashcode;
	}
	
	@Column(name = "idUser", nullable = false, unique = true)
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idUser;

	@Column(name = "codeUser", nullable = false, unique = true, length = 10)
	private String codeUser;

	@Column(name = "nameUser", nullable = false, length = 50)
	private String nameUser;

	@Column(name = "firstSurnameUser", nullable = true, length = 100)
	private String firstSurnameUser;

	@Column(name = "secondSurnameUser", nullable = true, length = 100)
	private String secondSurnameUser;

	@Column(name = "passwordUser", nullable = false, length = 32)
	private String passwordUser;
	
	@Column(name = "phoneNumberUser", nullable = true, length = 11)
	private String phoneNumberUser;
	
	@Column(name = "emailUser", nullable = true, length = 100)
	private String emailUser;
	
	@Column(name = "addedDateUser", nullable = false)
	private Date addedDateUser;
	
	@Column(name = "expirationDateUser", nullable = true)
	private Date expirationDateUser;
	
	@Column(name = "expirationDateUserPassword", nullable = true)
	private Date expirationDateUserPassword;
	
	@Column(name = "locked", nullable = false, length = 1)
	private boolean locked;
	
	@Column(name = "enabled", nullable = false, length = 1)
	private boolean enabled;
	
	@Column(name = "lastAccess", nullable = true)
	private Date lastAccess;

	@OneToMany(targetEntity = ProfileData.class)
	@Cascade({ CascadeType.LOCK })
	@JoinTable(name = "users_profiles", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idProfile"))
	@LazyCollection(LazyCollectionOption.TRUE)
	public Set<ProfileData> profiles = new LinkedHashSet<ProfileData>();
	
	@OneToMany(targetEntity = RoleData.class)
	@Cascade({ CascadeType.LOCK })
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idRole"))
	@LazyCollection(LazyCollectionOption.TRUE)
	public Set<RoleData> roles = new LinkedHashSet<RoleData>();
	
	@OneToMany(mappedBy="commercial", targetEntity=CustomerData.class)	
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.LOCK})	
	@LazyCollection(LazyCollectionOption.TRUE)	
	private Set<CustomerData> customers = new HashSet<CustomerData>();
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getORMID() {
		return idUser;
	}
	public String getCodeUser() {
		return codeUser;
	}
	public void setCodeUser(String codeUser) {
		this.codeUser = codeUser;
	}
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
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
	public String getPasswordUser() {
		return passwordUser;
	}
	public void setPasswordUser(String passwordUser) {
		this.passwordUser = passwordUser;
	}
	public String getPhoneNumberUser() {
		return phoneNumberUser;
	}
	public Set<CustomerData> getCustomers() {
		return customers;
	}
	public void setCustomers(Set<CustomerData> customers) {
		this.customers = customers;
	}
	public void setPhoneNumberUser(String phoneNumberUser) {
		this.phoneNumberUser = phoneNumberUser;
	}
	public String getEmailUser() {
		return emailUser;
	}
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
	public Date getAddedDateUser() {
		return addedDateUser;
	}
	public void setAddedDateUser(Date addedDateUser) {
		this.addedDateUser = addedDateUser;
	}
	public Date getExpirationDateUser() {
		return expirationDateUser;
	}
	public void setExpirationDateUser(Date expirationDateUser) {
		this.expirationDateUser = expirationDateUser;
	}
	public void setExpirationDateUserPassword(Date expirationDateUserPassword) {
		this.expirationDateUserPassword = expirationDateUserPassword;
	}
	public Date getExpirationDateUserPassword() {
		return expirationDateUserPassword;
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
	public Collection<ProfileData> getProfiles() {
		return profiles;
	}
	public void setProfiles(Set<ProfileData> profiles) {
		this.profiles = profiles;
	}
	public Collection<RoleData> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleData> roles) {
		this.roles = roles;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}
}