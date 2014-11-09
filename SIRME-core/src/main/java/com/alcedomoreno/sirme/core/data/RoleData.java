package com.alcedomoreno.sirme.core.data;

import java.io.Serializable;
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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;


@Entity
@org.hibernate.annotations.Proxy(lazy=true)
@Table(name="roles")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class RoleData implements GrantedAuthority, Serializable, DataObject{

	private static final long serialVersionUID = 1L;

	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof RoleData))
			return false;
		RoleData userData = (RoleData) aObj;
		if (getIdRole() != userData.getIdRole())
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		hashcode = hashcode + getIdRole();
		return hashcode;
	}
	
	@Column(name="idRole", nullable=false, unique=true)	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idRole;
	
	@Column(name="codeRole", nullable=false, unique=true, length=50)
	private String codeRole;
	
	@Column(name="descriptionRole", nullable=true, length=255)
	private String descriptionRole;
	
	@Column(name="URLSuccessLogin", nullable=true, length=255)
	private String URLSuccessLogin;
	
	
	@ManyToMany(targetEntity=PermissionData.class)
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.LOCK, org.hibernate.annotations.CascadeType.LOCK})	
	@JoinTable(name = "roles_permissions", joinColumns = @JoinColumn(name = "idRole"), inverseJoinColumns = @JoinColumn(name = "idPermission"))
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.TRUE)	
	public Set<PermissionData> permissions = new java.util.HashSet<PermissionData>();
	
	public int getORMID() {
		return getIdRole();
	}
		
	
	
	public String getURLSuccessLogin() {
		return URLSuccessLogin;
	}

	public void setURLSuccessLogin(String uRLSuccessLogin) {
		URLSuccessLogin = uRLSuccessLogin;
	}

	public Set<PermissionData> getPermissions() {
		return this.permissions;
	}
	
	public void setPermissions(Set<PermissionData> permissions) {
		this.permissions = permissions;
	}

	public int getIdRole() {
		return idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	public String getCodeRole() {
		return codeRole;
	}

	public void setCodeRole(String codeRole) {
		this.codeRole = codeRole;
	}

	public String getDescriptionRole() {
		return descriptionRole;
	}

	public void setDescriptionRole(String descriptionRole) {
		this.descriptionRole = descriptionRole;
	}

	@Override
	public String getAuthority() {
		return this.codeRole;
	}
	
	
	
}
