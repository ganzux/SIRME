package com.sirme.beans;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.event.ActionEvent;

import org.primefaces.event.CellEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sirme.basicsecurity.SpringSecurityConstants;
import com.sirme.basicsecurity.business.IRolesService;
import com.sirme.basicsecurity.business.IUsersService;
import com.sirme.basicsecurity.business.data.Role;
import com.sirme.basicsecurity.business.data.User;
import com.sirme.util.BeanNameUtil;
import com.sirme.util.MyLogger;
import com.sirme.util.StringUtil;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;

@Component( BeanNameUtil.USERS_BEAN )
@Scope("session")
public class UsersBean extends ManagedBean {
	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger( UsersBean.class );
	private static final String CLASS_NAME = BeanNameUtil.USERS_BEAN;
	
	@Resource( name=BeanNameUtil.APP_BEAN)
	private ApplicationBean applicationBean;

	@Resource(name = SpringSecurityConstants.USER_SERVICE)
	protected IUsersService usersService;
	
	@Resource(name = SpringSecurityConstants.ROLE_SERVICE)
	protected IRolesService rolesService;

	@Resource( name = BeanNameUtil.STACK_BEAN )
	protected StackNavigationBean stackBean;

	private Collection<User> users;
	private Collection<User> filteredUsers;
	private User selectedUser;
	
	private String newPass1;
	private String newPass2;
	
	private Collection<Role> roles;
	private Role selectedRole;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	public UsersBean(){				
		MyLogger.info(log, CLASS_NAME, "UsersBean", "New Instance");
	}	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////
	
	@Override
	public String doInit(){
		MyLogger.debug(log, CLASS_NAME, "doInit", "IN");

		newPass1 = "";
		newPass2 = "";
		reloadUsers( null );
		stackBean.initStack();

		MyLogger.debug(log, CLASS_NAME, "doInit", "OUT");
		return BeanNameUtil.PAGE_PRINCIPAL_USERS;
	}
	
	public void filter(){
		filteredUsers= users;
	}

	@Override
	public String prepareAction(){
		MyLogger.debug(log, CLASS_NAME, "prepareAction", "IN", getPageOption(), selectedUser);

		if ( getPageOption() == applicationBean.getOptionSave() )
			selectedUser = new User();
		else
			selectedUser = usersService.getPlain( selectedUser.getCodeUser() );

		MyLogger.debug(log, CLASS_NAME, "prepareAction", "OUT",getPageOption(), selectedUser);
		
		return BeanNameUtil.PAGE_SECONDARY_USERS;
	}
	
	public void onContactEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue();  
          
        if(newValue != null && !newValue.equals(oldValue))
        	applicationBean.sendMessageInfo("clientes.contacto.cambio.titulo", "clientes.contacto.cambio", oldValue.toString(), newValue.toString());
    }

	@Override
	public String save(){
		try{
			MyLogger.debug(log, CLASS_NAME, "save", "IN");
			usersService.save( selectedUser );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.save");
			MyLogger.debug(log, CLASS_NAME, "save", "OUT");
		} catch(ValidationException e){
			applicationBean.sendMessageError("web.error.general.validacion", "web.error.general.xp", e.getMessage() );
			return null;
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "Código de Usuario repetido" );
			return null;
		}
		return doInit();
	}
	
	@Override
	public String update(){
		try{
			MyLogger.debug(log, CLASS_NAME, "update", "IN");
			usersService.update( selectedUser );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.update");
			MyLogger.debug(log, CLASS_NAME, "update", "OUT");
		} catch(ValidationException e){
			applicationBean.sendMessageError("web.error.general.validacion", "web.error.general.xp", e.getMessage() );
			return null;
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "Código del Cliente repetido" );
			return null;
		}
		return doInit();
	}
	
	@Override
	public String delete(){
		try{
			MyLogger.debug(log, CLASS_NAME, "delete", "IN");
			usersService.delete( selectedUser.getIdUser() );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.delete");
			MyLogger.debug(log, CLASS_NAME, "delete", "OUT");
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "El Equipo está asignado a algún Parte de Trabajo. Desactívelo en su lugar." );
			return null;
		}
		return doInit();
	}

	@Override
	public String back(){
		filteredUsers= users;
		String pop = getStackBean().popPage();
		return ( pop == null?BeanNameUtil.PAGE_PRINCIPAL_USERS:pop) ;
	}
	
	@Override
	public String cancel(){
		return back();
	}
	
	public void addRole(){
		if ( selectedUser != null ){
			if ( selectedRole != null ){
				if( selectedUser.getRoles() == null )
					selectedUser.setRoles( new ArrayList<Role>() );

				if ( ! selectedUser.getRoles().contains( selectedRole ) )
					selectedUser.getRoles().add( selectedRole );
				else
					getApplicationBean().sendMessageError("users.add.repetido.title" , "users.add.repetido.explica");
			}
		}
	}
	
	public void removeRole(){
		if ( selectedUser != null )
			if ( selectedRole != null )
				if( selectedUser.getRoles() != null )
					selectedUser.getRoles().remove( selectedRole );
	}

	public String updatePass(){
		
		try{
			MyLogger.debug(log, CLASS_NAME, "save", "IN");
			usersService.updatePassword(selectedUser.getIdUser(), newPass1, newPass2);
			try {
				selectedUser.setPasswordUser( StringUtil.getMD5(newPass1) );
			} catch (Exception e) {
				e.printStackTrace();
			}
			applicationBean.sendMessageInfo("web.operacion.correcta", "Se cambió la contraseña con éxito");
			MyLogger.debug(log, CLASS_NAME, "save", "OUT");
		} catch(ValidationException e){
			applicationBean.sendMessageError("web.error.general.validacion", e.getMessage() );
			return null;
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "No se puede cambiar la contraseña" );
			return null;
		}
		return null;
	}


	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Privados                     //
	///////////////////////////////////////////////////////////////
	public void reloadUsers(ActionEvent event){
		users = usersService.getAll();
		roles = rolesService.getAll();
		filteredUsers = users;
		filter();
	}
	
	public void reloadCommercials(ActionEvent event){
		users = usersService.getAll( Role.CODE_COMMERCIAL );
		filteredUsers = users;
		filter();
	}
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Privados               //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Getters y Setters                    //
	///////////////////////////////////////////////////////////////
	
	public ApplicationBean getApplicationBean() {
		return applicationBean;
	}
	public void setApplicationBean(ApplicationBean applicationBean) {
		this.applicationBean = applicationBean;
	}
	public StackNavigationBean getStackBean() {
		return stackBean;
	}
	public void setStackBean(StackNavigationBean stackBean) {
		this.stackBean = stackBean;
	}
	public User getSelectedUser() {
		return selectedUser;
	}
	public IUsersService getUsersService() {
		return usersService;
	}
	public void setUsersService(IUsersService usersService) {
		this.usersService = usersService;
	}
	public Collection<User> getUsers() {
		return users;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	public Collection<User> getFilteredUsers() {
		return filteredUsers;
	}
	public void setFilteredUsers(Collection<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	public Role getSelectedRole() {
		return selectedRole;
	}
	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	public String getNewPass1() {
		return newPass1;
	}
	public void setNewPass1(String newPass1) {
		this.newPass1 = newPass1;
	}
	public String getNewPass2() {
		return newPass2;
	}
	public void setNewPass2(String newPass2) {
		this.newPass2 = newPass2;
	}
	///////////////////////////////////////////////////////////////
	//                Fin de los Getters y Setters               //
	///////////////////////////////////////////////////////////////
}
