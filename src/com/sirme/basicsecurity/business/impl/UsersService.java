package com.sirme.basicsecurity.business.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sirme.basicsecurity.SpringSecurityConstants;
import com.sirme.basicsecurity.SuperUser;
import com.sirme.basicsecurity.business.IUsersService;
import com.sirme.basicsecurity.business.data.Profile;
import com.sirme.basicsecurity.business.data.Role;
import com.sirme.basicsecurity.business.data.User;
import com.sirme.basicsecurity.dao.IPermissionsDao;
import com.sirme.basicsecurity.dao.IUsersDao;
import com.sirme.basicsecurity.data.PermissionData;
import com.sirme.basicsecurity.data.ProfileData;
import com.sirme.basicsecurity.data.RoleData;
import com.sirme.basicsecurity.data.UserData;
import com.sirme.bussiness.Customer;
import com.sirme.transform.TransformFactory;
import com.sirme.util.MyLogger;
import com.sirme.util.StringUtil;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;

@Service( SpringSecurityConstants.USER_SERVICE )
public class UsersService implements Serializable,IUsersService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(UsersService.class);
	private static final String CLASS_NAME = "UsersService";
	
	@Resource(name=SpringSecurityConstants.USER_DAO)
	IUsersDao usersDao;
	
	@Resource( name=SpringSecurityConstants.PERMISSION_DAO )
	IPermissionsDao permissionDao;
	
	public static final String PROFILE_PREFFIX = "PROFILE";
	public static final String DEFAULT_PAGE_ADMIN = "/pages/pg_general.xhtml";
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////
	@Override
	public User getPlain(String codeUser) {
		MyLogger.info(log, CLASS_NAME, "getByCodeUser", "codeUser="+codeUser, "START");

		UserData data = usersDao.getByCode(codeUser);
		if(data == null){
			MyLogger.info(log, CLASS_NAME, "getByCodeUser", "codeUser="+codeUser+"return null", "END");
			return null;
		}
		User business = (User) TransformFactory.getTransformator(User.class).toBusinessObject(data);
		
		if( data.getProfiles() != null )
			business.setProfiles( (Collection<Profile>) TransformFactory.getTransformator(Profile.class).toBusinessObject(data.getProfiles()));
		if( data.getRoles() != null )
			business.setRoles( (Collection<Role>) TransformFactory.getTransformator(Role.class).toBusinessObject(data.getRoles()));
		if ( business.isCommercial() )
			if( data.getCustomers() != null )
				business.setCustomersOfCommercial( (Collection<Customer>) TransformFactory.getTransformator(Customer.class).toBusinessObject(data.getCustomers()));

		MyLogger.info(log, CLASS_NAME, "getByCodeUser", "codeUser="+codeUser, "END");

		return business;
	}
	
	@Override
	public org.springframework.security.core.userdetails.User get(String codeUser) {
		MyLogger.info(log, CLASS_NAME, "getByCodeUser", "codeUser="+codeUser, "START");

		UserData user = usersDao.getByCode( codeUser );
		user = setProfileToUser( user );
		
		if (user == null) {
			throw new UsernameNotFoundException("Usuario no encontrado : [" + codeUser + "]");
		}
		
		boolean isAccountExpired = user.getExpirationDateUser() != null && (user.getExpirationDateUser()).before(new Date());
		boolean isCredentialsExpired = user.getExpirationDateUserPassword() != null && (user.getExpirationDateUserPassword()).before(new Date());
		
		/** Añadimos sus perfiles asociados a la info de usuario **/
		Collection<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.addAll( user.getRoles() );
		
		/** Añadimos los permisos asociados a sus perfiles **/
		for(RoleData role : user.getRoles()){
			for( PermissionData permission: role.getPermissions() ){
				list.add(new GrantedAuthorityImpl("PERMISSION_" + permission.getCodePermission()));
			}
		}
		
		return new org.springframework.security.core.userdetails.User(
				user.getCodeUser(), 
				user.getPasswordUser(),
				user.isEnabled(),
				!isAccountExpired,
				!isCredentialsExpired,
				!user.isLocked(),
				list);
	}
	
	
	@Override
	public User getByCodeUserLoadAllData(String codeUser) {

		MyLogger.info(log, CLASS_NAME, "getByCodeUserLoadAllData", "codeUser="+codeUser, "START");

		UserData data = usersDao.getByCodeUserLoadAllData(codeUser);
		if(data == null){
			MyLogger.info(log, CLASS_NAME, "getByCodeUserLoadAllData", "codeUser="+codeUser+"return null", "END");
			return null;
		}
		User business = (User) TransformFactory.getTransformator(User.class).toBusinessObject(data);

		MyLogger.info(log, CLASS_NAME, "getByCodeUserLoadAllData", "codeUser="+codeUser, "END");

		return business;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> getAll(){
		MyLogger.info(log, CLASS_NAME, "getAllUsers", "", "START");

		Collection<UserData> data = usersDao.getAll();
		
		if(data == null){
			MyLogger.info(log, CLASS_NAME, "getAllUsers", "null", "END");
			return null;
		}
		
		Collection<User> business = (Collection<User>) TransformFactory.getTransformator(User.class).toBusinessObject(data);

		MyLogger.info(log, CLASS_NAME, "getAllUsers", "", "END");
		
		return business;
	}
	
	@Override
	public Collection<User> getAll( String type ){
		MyLogger.info(log, CLASS_NAME, "getAllUsers", "", "START");

		Collection<UserData> allUsers = usersDao.getAll();
		Collection<User> bussiness = new ArrayList<User>();
		
		for ( UserData u:allUsers ){
			ifrol:
			if ( u.getRoles()!=null )
				for ( RoleData role: u.getRoles() ){
					if ( type.equals(role.getCodeRole() ) ){
						bussiness.add( (User) TransformFactory.getTransformator(User.class).toBusinessObject(u) );
						break ifrol;
					}
				}
		}
		
		MyLogger.info(log, CLASS_NAME, "getAllUsers", "", "END");
		
		return bussiness;
	}
	
	@Override
	public void save(User user) throws ValidationException, TransactionException{

		MyLogger.info(log, CLASS_NAME, "saveUser", "user=" + user.getIdUser(), "START");
		
		validateUser( user );
		
		try{	
			UserData data = (UserData) TransformFactory.getTransformator( User.class ).toDataObject(user);
			
			data.setAddedDateUser( new Date() );
			data.setEnabled( true );
			data.setLocked( false );
			data.setPasswordUser( StringUtil.getMD5( user.getPasswordUser() ) );
			
			data.setProfiles(new HashSet<ProfileData>());
			Collection<RoleData> rolesData = (Collection<RoleData>) TransformFactory.getTransformator(Role.class).toDataObject( user.getRoles() );
			data.setRoles( new HashSet<RoleData>(rolesData) );
			usersDao.save(data);
	
			MyLogger.info(log, CLASS_NAME, "saveUser", "user=" + user.getIdUser(), "END");
		} catch (Exception e){
			throw new TransactionException( e );
		}
	}
	
	@Override
	public void update(User user) throws ValidationException, TransactionException{

		MyLogger.info(log, CLASS_NAME, "saveUser", "user=" + user.getIdUser(), "START");

		validateUser( user );

		try{	
			UserData data = (UserData) TransformFactory.getTransformator( User.class ).toDataObject(user);
			data.setProfiles(new HashSet<ProfileData>());
			Collection<RoleData> rolesData = (Collection<RoleData>) TransformFactory.getTransformator(Role.class).toDataObject( user.getRoles() );
			data.setRoles( new HashSet<RoleData>(rolesData) );
			usersDao.update(data);

			MyLogger.info(log, CLASS_NAME, "saveUser", "user=" + user.getIdUser(), "END");
		} catch (Exception e){
			throw new TransactionException( e );
		}
	}

	@Override
	public void delete(int idUser) throws TransactionException {
		
		try{
			MyLogger.info(log, CLASS_NAME, "deleteUser", "user="+ idUser, "START");
			usersDao.delete(idUser);
			MyLogger.info(log, CLASS_NAME, "deleteUser", "user="+ idUser, "END");
		} catch (Exception e){
			throw new TransactionException( e );
		}
		
	}
	
	@Override
	public void updatePassword(int idUser, String pass1,String pass2) throws ValidationException,TransactionException{
		MyLogger.info(log, CLASS_NAME, "updatePassword", "user="+ idUser, "START");
		
		validatePass(pass1,pass2);
		
		try{
			String hashedPass = StringUtil.getMD5( pass1 );
			usersDao.updatePass(idUser, hashedPass);
		} catch( Exception e){
			throw new TransactionException( e );
		}

		MyLogger.info(log, CLASS_NAME, "updatePassword", "user="+ idUser, "END");
	}
	
	public void updateDateAccess(String codeUser) throws ValidationException,TransactionException{
		MyLogger.info(log, CLASS_NAME, "updateDateAccess", "user="+ codeUser, "START");

		try{
			usersDao.updateLastDate(codeUser);
		} catch( Exception e){
			throw new TransactionException( e );
		}

		MyLogger.info(log, CLASS_NAME, "updateDateAccess", "user="+ codeUser, "END");
	}
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////

	private synchronized UserData setProfileToUser(UserData user){
		if ( user!=null && SuperUser.isSU( user.getIdUser() ) ){
			RoleData superPF = new RoleData();
			
			superPF.setCodeRole( PROFILE_PREFFIX );
			superPF.setIdRole( Integer.MAX_VALUE );
			superPF.setDescriptionRole( PROFILE_PREFFIX );
			superPF.setURLSuccessLogin( DEFAULT_PAGE_ADMIN );
			superPF.setPermissions( new HashSet<PermissionData>( permissionDao.getAll() ) );

			Set<RoleData> superRoles = new HashSet<RoleData>();
			superRoles.add( superPF );

			user.setRoles( superRoles );
		}
		return user;
	}
	
	private boolean validatePass(String pass1,String pass2) throws ValidationException{
		if ( pass1==null || pass1.trim().isEmpty() )
			throw new ValidationException( new Exception("Contraseña vacía") );
		if ( ! pass1.equals(pass2) )
			throw new ValidationException( new Exception("Las contraseñas no coinciden") );
		return true;
	}
	
	private void validateUser(User user) throws ValidationException{
		if ( user == null || user.getCodeUser() == null  )
			throw new ValidationException("El Código de Usuario es obligatorio");
		if ( user.getNameUser() == null  )
			throw new ValidationException("El Nombre de Usuario es Obligatorio");
		if ( user.getFirstSurnameUser() == null  )
			throw new ValidationException("El Apellido de Usuario es Obligatorio");
		if ( user.getPasswordUser() == null  )
			throw new ValidationException("La contraseña de Usuario es Obligatoria");
	}
}
