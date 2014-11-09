package com.alcedomoreno.sirme.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.alcedomoreno.sirme.business.data.Address;
import com.alcedomoreno.sirme.business.data.Contact;
import com.alcedomoreno.sirme.business.data.Customer;
import com.alcedomoreno.sirme.business.data.Manager;
import com.alcedomoreno.sirme.business.data.TypeCustomer;
import com.alcedomoreno.sirme.business.data.User;
import com.alcedomoreno.sirme.business.services.CustomerService;
import com.alcedomoreno.sirme.business.services.WorkService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.business.util.TransactionException;
import com.alcedomoreno.sirme.business.util.ValidationException;
import com.alcedomoreno.sirme.core.util.MyLogger;
import com.alcedomoreno.sirme.web.excel.CustomerXLSExtract;
import com.alcedomoreno.sirme.web.util.BeanNameUtil;


@Component( BeanNameUtil.CUSTOMERS_BEAN )
@Scope("session")
public class CustomersBean extends ManagedBean {
	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger( CustomersBean.class );
	private static final String CLASS_NAME = BeanNameUtil.CUSTOMERS_BEAN;
	
	@Resource( name=BeanNameUtil.APP_BEAN)
	private ApplicationBean applicationBean;
	
	@Resource(name = ServiceConstants.CUSTOMER_SERVICE)
	private CustomerService customerService;

	@Resource(name = ServiceConstants.WORK_SERVICE)
	private WorkService worksService;

	@Resource( name = BeanNameUtil.STACK_BEAN )
	protected StackNavigationBean stackBean;

	private Collection<Customer> customers;
	private Collection<Customer> filteredCustomers;
	private Customer selectedCustomer;
	
	private Contact newContact;
	private Contact selectedContact;
	
	private Address newAddress;
	private Address selectedAddress;
	
	private Manager selectedManager;
	private User selectedCommercial;
	
	private int activeTab = 0;
	
	private UploadedFile file;

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public CustomersBean(){				
		MyLogger.info(log, CLASS_NAME, "ClientsBean", "New Instance");
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

		selectedManager = null;
		selectedCommercial = null;
		stackBean.initStack();

		customers = customerService.getAllCustomers();
		filteredCustomers = customers;

		MyLogger.debug(log, CLASS_NAME, "doInit", "OUT");
		return BeanNameUtil.PAGE_PRINCIPAL_CUSTOMERS;
	}
	
	public void filter(){
		filteredCustomers = customers;
	}

	@Override
	public String prepareAction(){
		activeTab = 0;
		MyLogger.debug(log, CLASS_NAME, "prepareAction", "IN", getPageOption(), selectedCustomer);

		if ( getPageOption() == applicationBean.getOptionSave() ){
			selectedCustomer = new Customer();
			selectedCustomer.setTypeCustomer( new TypeCustomer() );
		}

		if ( getPageOption() != applicationBean.getOptionSave() ){
			selectedCustomer = customerService.get( selectedCustomer );
			selectedManager = selectedCustomer.getManager();
			selectedCommercial = selectedCustomer.getCommercial();
			
			// En la Vista Avanzada, cargamos las Fichas
			if ( getPageOption() == applicationBean.getOptionView() )
				if ( selectedCustomer.getAddress() != null )
					for ( Address a : selectedCustomer.getAddress() )
						a.setWorks( worksService.get( a ) );
		}

		newContact = new Contact();
		newAddress = new Address();

		MyLogger.debug(log, CLASS_NAME, "prepareAction", "OUT",getPageOption(), selectedCustomer);
		
		return BeanNameUtil.PAGE_SECONDARY_CUSTOMERS;
	}
	
	public void onContactEdit(CellEditEvent event) {  
        Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue();  
          
        if(newValue != null && !newValue.equals(oldValue))
        	applicationBean.sendMessageInfo("clientes.contacto.cambio.titulo", "clientes.contacto.cambio", oldValue.toString(), newValue.toString());
    }
	
	public void onAddressEdit(CellEditEvent event) {  
        Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue();  
          
        if(newValue != null && !newValue.equals(oldValue) )
        	applicationBean.sendMessageInfo("Dirección del Servicio cambiada", "Antes ?, ahora ?", oldValue.toString(), newValue.toString());
    }
	
	public void addNewContact(){
		
		if ( newContact.getNameContact() == null || newContact.getNameContact().trim().isEmpty()
			|| newContact.getDataContact() == null || newContact.getDataContact().trim().isEmpty() )
			applicationBean.sendMessageInfo("web.error.general.validacion", "Tiene que introducir todos los datos del contacto");
		else {
			if ( selectedCustomer.getContacts()==null )
				selectedCustomer.setContacts( new ArrayList<Contact>() );
			selectedCustomer.getContacts().add( newContact );
			applicationBean.sendMessageInfo("clientes.contacto.nuevo.contacto", "clientes.contacto.nuevo.contacto.ok", newContact.getNameContact(), newContact.getDataContact());
			newContact = new Contact();
		}
	}

	public void addNewAddress(){
		
		if ( newAddress.getLocation() == null || newAddress.getLocation().trim().isEmpty()
			|| newAddress.getMainAddress() == null || newAddress.getMainAddress().trim().isEmpty() )
			applicationBean.sendMessageInfo("web.error.general.validacion", "Tiene que introducir Ubicación y Dirección");
		else {
			if ( selectedCustomer.getAddress()==null )
				selectedCustomer.setAddress( new ArrayList<Address>() );
			selectedCustomer.getAddress().add( newAddress );
			applicationBean.sendMessageInfo("clientes.contacto.nuevo.contacto", "Se ha añadido una nueva dirección con localización ? y dirección ?", newAddress.getLocation(), newAddress.getMainAddress());
			newAddress = new Address();
		}
	}
	
	public void removeContact(){
		if ( selectedContact != null && selectedCustomer.getContacts() != null )
			selectedCustomer.getContacts().remove( selectedContact );
		applicationBean.sendMessageInfo("clientes.contacto.borrado.contacto", "clientes.contacto.borrado.contacto.ok", selectedContact.getNameContact(), selectedContact.getDataContact());
	}
	
	public void removeAddress(){
		if ( selectedAddress != null && selectedCustomer.getAddress() != null )
			selectedCustomer.getAddress().remove( selectedAddress );
		applicationBean.sendMessageInfo("clientes.address.borrado.contacto", "clientes.address.borrado.contacto.ok", selectedAddress.getLocation());
	}

	@Override
	public String save(){
		try{
			MyLogger.debug(log, CLASS_NAME, "save", "IN");

			selectedCustomer.setManager( selectedManager );
			selectedCustomer.setCommercial( selectedCommercial );
			
			customerService.save( selectedCustomer );
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

			selectedCustomer.setManager( selectedManager );
			selectedCustomer.setCommercial( selectedCommercial );

			customerService.update( selectedCustomer );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.update");
			MyLogger.debug(log, CLASS_NAME, "update", "OUT");
		} catch(ValidationException e){
			applicationBean.sendMessageError("web.error.general.validacion", "web.error.general.xp", e.getMessage() );
			return null;
		} catch(Exception e){
			if ( e instanceof DataIntegrityViolationException )
				applicationBean.sendMessageError("web.error.general.transaccion", "clientes.error.codigo.repetido" );
			else
				applicationBean.sendMessageError("web.error.general.transaccion", "web.error.general.xp", e.getMessage() );
			return null;
		}
		return doInit();
	}

	@Override
	public String delete(){
		try{
			MyLogger.debug(log, CLASS_NAME, "delete", "IN");
			customerService.delete( selectedCustomer );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.delete");
			MyLogger.debug(log, CLASS_NAME, "delete", "OUT");
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "web.error.general.xp", e.getMessage() );
			return null;
		}
		return doInit();
	}

	@Override
	public String back(){
		filteredCustomers = customers;
		String pop = getStackBean().popPage();
		return ( pop == null?BeanNameUtil.PAGE_PRINCIPAL_CUSTOMERS:pop) ;
	}
	
	@Override
	public String cancel(){
		return back();
	}
	
	public void findCIF(AjaxBehaviorEvent event){
		try{
			String cif = (String) ((InputText) event.getSource()).getValue();
			Customer existing = customerService.get( new Customer(cif) );

			// if exists and the id is not current, the cif is repeated
			if ( existing != null && selectedCustomer.getIdCustomer() != existing.getIdCustomer() ){
				applicationBean.sendMessageInfo("CIF Existente", "El CIF ? ya existe como Cliente. Compruébelo antes de continuar", cif);
			}
		} catch( Exception e){
			
		}
	}
	
	public void upload() {  
		try {
			Customer customerFile = CustomerXLSExtract.getInstance().getCustomerFromXLS( file.getInputstream() );

			Address ad = new Address();
			ad.setLocation( customerFile.getServiceLocation()==null?"":customerFile.getServiceLocation() );
			ad.setMainAddress( customerFile.getServiceAddress() );
			ad.setMainPobl( customerFile.getServicePobl() );
			ad.setMainPostalCode( customerFile.getServicePostalCode() );
			ad.setMainProv( customerFile.getServiceProv() );
			ad.setOther("");

			boolean oldC = false;
			for ( Customer c:customers )
				if ( c.getCodeCustomer().equalsIgnoreCase(customerFile.getCodeCustomer() ) ){
					oldC = true;
					selectedCustomer = c;
					break;
				}
			
			if ( !oldC ){
				selectedCustomer = customerFile;
				selectedCustomer.setAddress( new ArrayList<Address>() );
				selectedCustomer.getAddress().add( ad );
			} else {
				setPageOption( applicationBean.getOptionUpdate() );
				if ( selectedCustomer.getAddress() == null )
					selectedCustomer.setAddress( new ArrayList<Address>() );
				selectedCustomer.getAddress().add( ad );
			}

//			selectedMonths = new ArrayList<String>();
//			if ( selectedCustomer.getMonths()!=null && !selectedCustomer.getMonths().isEmpty() )
//				for ( MonthE m:selectedCustomer.getMonths() )
//					selectedMonths.add( String.valueOf( m.getValue() ) );
					
			applicationBean.sendMessageInfo("web.operacion.correcta", "Se han cargado los datos del contrato correctamente. REVISELOS.");
		} catch (Exception e) {
			applicationBean.sendMessageError("web.error.general", "Ha ocurrido un error al procesar el contrato. Remítaselo al administrador" );
		}
    }
	
	public String reloadCustomers(){
		customers = customerService.getAllCustomers();
		filteredCustomers = customers;

		return BeanNameUtil.PAGE_PRINCIPAL_CUSTOMERS;
	}
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      Métodos Privados                     //
	///////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Privados               //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                      Getters y Setters                    //
	///////////////////////////////////////////////////////////////
	
	public Collection<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(Collection<Customer> customers) {
		this.customers = customers;
	}
	public Customer getSelectedCustomer() {
		return selectedCustomer;
	}
	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}
	public Collection<Customer> getFilteredCustomers() {
		return filteredCustomers;
	}
	public void setFilteredCustomers(Collection<Customer> filteredCustomers) {
		this.filteredCustomers = filteredCustomers;
	}
	public int getActiveTab() {
		return activeTab;
	}
	public void setActiveTab(int activeTab) {
		this.activeTab = activeTab;
	}
	public Contact getNewContact() {
		return newContact;
	}
	public void setNewContact(Contact newContact) {
		this.newContact = newContact;
	}
	public Contact getSelectedContact() {
		return selectedContact;
	}
	public void setSelectedContact(Contact selectedContact) {
		this.selectedContact = selectedContact;
	}
	public StackNavigationBean getStackBean() {
		return stackBean;
	}
	public void setStackBean(StackNavigationBean stackBean) {
		this.stackBean = stackBean;
	}
	public Manager getSelectedManager() {
		return selectedManager;
	}
	public void setSelectedManager(Manager selectedManager) {
		this.selectedManager = selectedManager;
	}
	public User getSelectedCommercial() {
		return selectedCommercial;
	}
	public void setSelectedCommercial(User selectedCommercial) {
		this.selectedCommercial = selectedCommercial;
	}
	public UploadedFile getFile() {  
        return file;  
    }
    public void setFile(UploadedFile file) {  
        this.file = file;  
    }
	public Address getNewAddress() {
		return newAddress;
	}
	public void setNewAddress(Address newAddress) {
		this.newAddress = newAddress;
	}
	public Address getSelectedAddress() {
		return selectedAddress;
	}
	public void setSelectedAddress(Address selectedAddress) {
		this.selectedAddress = selectedAddress;
	}
	///////////////////////////////////////////////////////////////
	//                Fin de los Getters y Setters               //
	///////////////////////////////////////////////////////////////
}
