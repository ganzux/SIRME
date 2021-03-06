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
import com.alcedomoreno.sirme.business.data.Team;
import com.alcedomoreno.sirme.business.data.User;
import com.alcedomoreno.sirme.business.services.TeamService;
import com.alcedomoreno.sirme.business.services.UsersService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.business.util.TransactionException;
import com.alcedomoreno.sirme.business.util.ValidationException;
import com.alcedomoreno.sirme.core.util.MyLogger;
import com.alcedomoreno.sirme.web.util.BeanNameUtil;


@Component( BeanNameUtil.TEAMS_BEAN )
@Scope("session")
public class TeamsBean extends ManagedBean {
	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger( TeamsBean.class );
	private static final String CLASS_NAME = BeanNameUtil.TEAMS_BEAN;
	
	@Resource( name=BeanNameUtil.APP_BEAN)
	private ApplicationBean applicationBean;
	
	@Resource(name = ServiceConstants.TEAM_SERVICE)
	protected TeamService teamService;

	@Resource(name = ServiceConstants.USER_SERVICE)
	protected UsersService usersService;

	@Resource( name = BeanNameUtil.STACK_BEAN )
	protected StackNavigationBean stackBean;

	private Collection<Team> teams;
	private Collection<Team> filteredTeams;
	private Team selectedTeam;
	private User selectedUser;
	private Collection<User> allUsers;
	
	private String newPass1;
	private String newPass2;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	public TeamsBean(){				
		MyLogger.info(log, CLASS_NAME, "TeamsBean", "New Instance");
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

		reloadTeams( null );
		newPass1 = "";
		newPass2 = "";
		stackBean.initStack();

		MyLogger.debug(log, CLASS_NAME, "doInit", "OUT");
		return BeanNameUtil.PAGE_PRINCIPAL_TEAMS;
	}
	
	public void filter(){
		filteredTeams= teams;
	}

	@Override
	public String prepareAction(){
		MyLogger.debug(log, CLASS_NAME, "prepareAction", "IN", getPageOption(), selectedTeam);

		if ( getPageOption() == applicationBean.getOptionSave() )
			selectedTeam = new Team();
		else
			selectedTeam = teamService.get( selectedTeam.getNameTeam(), selectedTeam.getPassWord());

		MyLogger.debug(log, CLASS_NAME, "prepareAction", "OUT",getPageOption(), selectedTeam);
		
		return BeanNameUtil.PAGE_SECONDARY_TEAMS;
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
			teamService.save( selectedTeam );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.save");
			MyLogger.debug(log, CLASS_NAME, "save", "OUT");
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
	public String update(){
		try{
			MyLogger.debug(log, CLASS_NAME, "update", "IN");
			teamService.update( selectedTeam );
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
			teamService.delete( selectedTeam );
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
		filteredTeams = teams;
		String pop = getStackBean().popPage();
		return ( pop == null?BeanNameUtil.PAGE_PRINCIPAL_TEAMS:pop) ;
	}
	
	@Override
	public String cancel(){
		selectedTeam = teamService.get( selectedTeam.getNameTeam(), selectedTeam.getPassWord());
		return back();
	}
	
	public void addUser(){
		if ( selectedUser != null ){
			if ( selectedTeam != null ){
				// Si no hay coleccion, se crea
				if( selectedTeam.getUsers() == null )
					selectedTeam.setUsers( new ArrayList<User>() );

				if ( ! selectedTeam.getUsers().contains( selectedUser ) )
					selectedTeam.getUsers().add( selectedUser );
				else
					getApplicationBean().sendMessageError("grupos.add.repetido.title" , "grupos.add.repetido.explica");
			}
		}
	}
	
	public void removeUser(){
		if ( selectedUser != null )
			if ( selectedTeam != null )
				if( selectedTeam.getUsers() != null )
					selectedTeam.getUsers().remove( selectedUser );
	}
	
	public String updatePass(){
		
		try{
			MyLogger.debug(log, CLASS_NAME, "save", "IN");
			teamService.updatePassword(selectedTeam.getIdTeam(), newPass1, newPass2);
			applicationBean.sendMessageInfo("web.operacion.correcta", "Se cambio la contrasena con exito");
			MyLogger.debug(log, CLASS_NAME, "save", "OUT");
		} catch(ValidationException e){
			applicationBean.sendMessageError("web.error.general.validacion", e.getMessage() );
			return null;
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "No se puede cambiar la contrasena" );
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
	public void reloadTeams(ActionEvent event){
		teams = teamService.getAll();
		allUsers = usersService.getAll( Role.CODE_TECHNICS );
		filteredTeams = new ArrayList<Team>();
		for( Team c:teams)
			filteredTeams.add( c );
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
	public Collection<Team> getTeams() {
		return teams;
	}
	public void setTeams(Collection<Team> teams) {
		this.teams = teams;
	}
	public Collection<Team> getFilteredTeams() {
		return filteredTeams;
	}
	public void setFilteredTeams(Collection<Team> filteredTeams) {
		this.filteredTeams = filteredTeams;
	}
	public Team getSelectedTeam() {
		return selectedTeam;
	}
	public void setSelectedTeam(Team selectedTeam) {
		this.selectedTeam = selectedTeam;
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
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	public Collection<User> getAllUsers() {
		return allUsers;
	}
	public void setAllUsers(Collection<User> allUsers) {
		this.allUsers = allUsers;
	}

	///////////////////////////////////////////////////////////////
	//                Fin de los Getters y Setters               //
	///////////////////////////////////////////////////////////////
}
