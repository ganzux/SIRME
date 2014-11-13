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

import com.alcedomoreno.sirme.business.data.Manager;
import com.alcedomoreno.sirme.business.data.ManagerContact;
import com.alcedomoreno.sirme.business.services.CustomerService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.business.util.TransactionException;
import com.alcedomoreno.sirme.business.util.ValidationException;
import com.alcedomoreno.sirme.core.util.MyLogger;
import com.alcedomoreno.sirme.web.util.BeanNameUtil;

@Component( BeanNameUtil.MANAGER_BEAN )
@Scope("session")
public class ManagerBean extends ManagedBean {
	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger( UsersBean.class );
	private static final String CLASS_NAME = BeanNameUtil.MANAGER_BEAN;
	
	@Resource( name=BeanNameUtil.APP_BEAN)
	private ApplicationBean applicationBean;

	@Resource(name = ServiceConstants.CUSTOMER_SERVICE)
	private CustomerService customerService;

	@Resource( name = BeanNameUtil.STACK_BEAN )
	protected StackNavigationBean stackBean;

	private Collection<Manager> managers;
	private Collection<Manager> filteredManagers;
	private Manager selectedManager;
	
	private ManagerContact newContact;
	private ManagerContact selectedContact;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	public ManagerBean(){				
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

		reloadManagers( null );
		stackBean.initStack();

		MyLogger.debug(log, CLASS_NAME, "doInit", "OUT");
		return BeanNameUtil.PAGE_PRINCIPAL_MANAGER;
	}
	
	public void filter(){
		filteredManagers= managers;
	}

	@Override
	public String prepareAction(){
		MyLogger.debug(log, CLASS_NAME, "prepareAction", "IN", getPageOption(), selectedManager);

		if ( getPageOption() == applicationBean.getOptionSave() )
			selectedManager = new Manager();
		else
			selectedManager = customerService.get( selectedManager );

		newContact = new ManagerContact();

		MyLogger.debug(log, CLASS_NAME, "prepareAction", "OUT",getPageOption(), selectedManager);
		
		return BeanNameUtil.PAGE_SECONDARY_MANAGER;
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
			customerService.save( selectedManager );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.save");
			MyLogger.debug(log, CLASS_NAME, "save", "OUT");
		} catch(ValidationException e){
			applicationBean.sendMessageError("web.error.general.validacion", "web.error.general.xp", e.getMessage() );
			return null;
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "Gestor Repetido" );
			return null;
		}
		return doInit();
	}
	
	@Override
	public String update(){
		try{
			MyLogger.debug(log, CLASS_NAME, "update", "IN");
			customerService.update( selectedManager );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.update");
			MyLogger.debug(log, CLASS_NAME, "update", "OUT");
		} catch(ValidationException e){
			applicationBean.sendMessageError("web.error.general.validacion", "web.error.general.xp", e.getMessage() );
			return null;
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "Gestor Repetido" );
			return null;
		}
		return doInit();
	}
	
	@Override
	public String delete(){
		try{
			MyLogger.debug(log, CLASS_NAME, "delete", "IN");
			customerService.delete( selectedManager );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.delete");
			MyLogger.debug(log, CLASS_NAME, "delete", "OUT");
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "Gestor con Clientes Asociados." );
			return null;
		}
		return doInit();
	}

	@Override
	public String back(){
		filteredManagers= managers;
		return BeanNameUtil.PAGE_PRINCIPAL_MANAGER;
	}
	
	@Override
	public String cancel(){
		filteredManagers= managers;
		return BeanNameUtil.PAGE_PRINCIPAL_MANAGER;
	}
	
	public void addNewContact(){
		
		if ( newContact.getNameContact() == null || newContact.getNameContact().trim().isEmpty()
			|| newContact.getDataContact() == null || newContact.getDataContact().trim().isEmpty() )
			applicationBean.sendMessageInfo("web.error.general.validacion", "Tiene que introducir todos los datos del contacto");
		else {
			if ( selectedManager.getContacts()==null )
				selectedManager.setContacts( new ArrayList<ManagerContact>() );
			selectedManager.getContacts().add( newContact );
			applicationBean.sendMessageInfo("clientes.contacto.nuevo.contacto", "clientes.contacto.nuevo.contacto.ok", newContact.getNameContact(), newContact.getDataContact());
			newContact = new ManagerContact();
		}
	}
	
	public void removeContact(){
		if ( selectedContact != null && selectedManager.getContacts() != null )
			selectedManager.getContacts().remove( selectedContact );
		applicationBean.sendMessageInfo("clientes.contacto.borrado.contacto", "clientes.contacto.borrado.contacto.ok", selectedContact.getNameContact(), selectedContact.getDataContact());
	}
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los Metodos Publicos               //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      Metodos Privados                     //
	///////////////////////////////////////////////////////////////
	public void reloadManagers(ActionEvent event){
		managers = customerService.getAllManagers();
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
	public Collection<Manager> getManagers() {
		return managers;
	}
	public void setManagers(Collection<Manager> managers) {
		this.managers = managers;
	}
	public Collection<Manager> getFilteredManagers() {
		return filteredManagers;
	}
	public void setFilteredManagers(Collection<Manager> filteredManagers) {
		this.filteredManagers = filteredManagers;
	}
	public Manager getSelectedManager() {
		return selectedManager;
	}
	public void setSelectedManager(Manager selectedManager) {
		this.selectedManager = selectedManager;
	}
	public StackNavigationBean getStackBean() {
		return stackBean;
	}
	public void setStackBean(StackNavigationBean stackBean) {
		this.stackBean = stackBean;
	}
	public ManagerContact getNewContact() {
		return newContact;
	}
	public void setNewContact(ManagerContact newContact) {
		this.newContact = newContact;
	}
	public ManagerContact getSelectedContact() {
		return selectedContact;
	}
	public void setSelectedContact(ManagerContact selectedContact) {
		this.selectedContact = selectedContact;
	}
	///////////////////////////////////////////////////////////////
	//                Fin de los Getters y Setters               //
	///////////////////////////////////////////////////////////////

}
