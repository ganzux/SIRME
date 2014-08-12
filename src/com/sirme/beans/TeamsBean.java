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
import com.sirme.basicsecurity.business.IUsersService;
import com.sirme.basicsecurity.business.data.Role;
import com.sirme.basicsecurity.business.data.User;
import com.sirme.bussiness.Team;
import com.sirme.services.ITeamService;
import com.sirme.util.BeanNameUtil;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;


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
	
	@Resource(name = SpringConstants.TEAM_SERVICE)
	protected ITeamService teamService;

	@Resource(name = SpringSecurityConstants.USER_SERVICE)
	protected IUsersService usersService;

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
	//                      Métodos Públicos                     //
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
			applicationBean.sendMessageError("web.error.general.transaccion", "Código del Cliente repetido" );
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
			applicationBean.sendMessageError("web.error.general.transaccion", "Código del Cliente repetido" );
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
			applicationBean.sendMessageError("web.error.general.transaccion", "El Equipo está asignado a algún Parte de Trabajo. Desactívelo en su lugar." );
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
				// Si no hay colección, se crea
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
	public void reloadTeams(ActionEvent event){
		teams = teamService.getAll();
		allUsers = usersService.getAll( Role.CODE_TECHNICS );
		filteredTeams = new ArrayList<Team>();
		for( Team c:teams)
			filteredTeams.add( c );
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
	public ITeamService getTeamService() {
		return teamService;
	}
	public void setTeamService(ITeamService teamService) {
		this.teamService = teamService;
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
