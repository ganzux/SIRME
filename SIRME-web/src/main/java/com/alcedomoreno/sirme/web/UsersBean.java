package com.alcedomoreno.sirme.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.event.ActionEvent;

import org.primefaces.event.CellEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alcedomoreno.sirme.business.data.Role;
import com.alcedomoreno.sirme.business.data.User;
import com.alcedomoreno.sirme.business.services.RolesService;
import com.alcedomoreno.sirme.business.services.UsersService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.business.util.StringUtil;
import com.alcedomoreno.sirme.business.util.TransactionException;
import com.alcedomoreno.sirme.business.util.ValidationException;
import com.alcedomoreno.sirme.core.util.MyLogger;
import com.alcedomoreno.sirme.web.util.BeanNameUtil;

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

	@Resource(name = ServiceConstants.USER_SERVICE)
	protected UsersService usersService;
	
	@Resource(name = ServiceConstants.ROLE_SERVICE)
	protected RolesService rolesService;

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
	//                      Metodos Publicos                     //
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
			applicationBean.sendMessageError("web.error.general.transaccion", "Codigo de Usuario repetido" );
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
			applicationBean.sendMessageError("web.error.general.transaccion", "Codigo del Cliente repetido" );
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
			applicationBean.sendMessageError("web.error.general.transaccion", "El Equipo esta asignado a algun Parte de Trabajo. Desactivelo en su lugar." );
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
			applicationBean.sendMessageInfo("web.operacion.correcta", "Se cambio la contraseña con exito");
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
	//                 Fin de los Metodos Publicos               //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Metodos Privados                     //
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
	//                 Fin de los Metodos Privados               //
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
