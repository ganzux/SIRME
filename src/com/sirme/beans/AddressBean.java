package com.sirme.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sirme.bussiness.Address;
import com.sirme.bussiness.Customer;
import com.sirme.bussiness.Work;
import com.sirme.services.ICustomerService;
import com.sirme.services.IWorkService;
import com.sirme.util.BeanNameUtil;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;


@Component( BeanNameUtil.ADDRESS_BEAN )
@Scope("session")
public class AddressBean extends ManagedBean {

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger( AddressBean.class );
	private static final String CLASS_NAME = BeanNameUtil.ADDRESS_BEAN;

	@Resource( name=BeanNameUtil.APP_BEAN)
	private ApplicationBean applicationBean;

	@Resource(name = SpringConstants.CUSTOMER_SERVICE)
	private ICustomerService customerService;
	
	@Resource(name = SpringConstants.WORK_SERVICE)
	private IWorkService worksService;

	@Resource( name = BeanNameUtil.STACK_BEAN )
	protected StackNavigationBean stackBean;

	private Collection<Address> addresses;
	private Collection<Address> filteredAddresses;
	private Address selectedAddress;

	private Customer selectedCustomer;
	Collection<Work> currentWorks;
	
	private List<String> selectedMonths;

	private String monthFilter;

	private Date filterForDate;
    private int filterForTypeWork;

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public AddressBean(){				
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

		selectedCustomer = null;
		stackBean.initStack();
		
		Collection<Customer> customers = customerService.getAllCustomers();
		addresses = new ArrayList<Address>();
		for ( Customer c:customers )
			addresses.addAll( c.getAddress() );
		filteredAddresses = addresses;
		
		MyLogger.debug(log, CLASS_NAME, "doInit", "OUT");
		return BeanNameUtil.PAGE_PRINCIPAL_ADDRESS;
	}

	public void filter(){
		filteredAddresses = addresses;
	}

	@Override
	public String prepareAction(){
		MyLogger.debug(log, CLASS_NAME, "prepareAction", "IN", getPageOption(), selectedAddress);

		selectedMonths = new ArrayList<String>();
		
		if ( getPageOption() == applicationBean.getOptionSave() ){
			selectedAddress = new Address();
			selectedCustomer = new Customer();
		}

		if ( getPageOption() != applicationBean.getOptionSave() ){
			selectedCustomer = selectedAddress.getCustomer();
			currentWorks = worksService.get( selectedAddress );
		}
		
		selectedMonths = selectedAddress.getMonthsInt();
		
		MyLogger.debug(log, CLASS_NAME, "prepareAction", "OUT",getPageOption(), selectedAddress);
		
		return BeanNameUtil.PAGE_SECONDARY_ADDRESS;
	}
	
	@Override
	public String save(){
		try{
			MyLogger.debug(log, CLASS_NAME, "save", "IN");

			selectedAddress.setMonths( selectedMonths );
			selectedAddress.setCustomer( selectedCustomer );
			
			customerService.save( selectedAddress );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.save");

			MyLogger.debug(log, CLASS_NAME, "save", "OUT");
		} catch(ValidationException e){
			applicationBean.sendMessageError("web.error.general.validacion", "web.error.general.xp", e.getMessage() );
			return null;
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "Código del Cliente o CIF repetido" );
			return null;
		}

		return doInit();
	}

	@Override
	public String update(){
		try{
			MyLogger.debug(log, CLASS_NAME, "update", "IN");

			selectedAddress.setMonths( selectedMonths );
			selectedAddress.setCustomer( selectedCustomer );

			customerService.update( selectedAddress );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.update");
			MyLogger.debug(log, CLASS_NAME, "update", "OUT");
		} catch(ValidationException e){
			applicationBean.sendMessageError("web.error.general.validacion", "web.error.general.xp", e.getMessage() );
			return null;
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "web.error.general.xp", e.getMessage() );
			return null;
		}
		return doInit();
	}

	@Override
	public String delete(){
		try{
			MyLogger.debug(log, CLASS_NAME, "delete", "IN");
			customerService.delete( selectedAddress );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.delete");
			MyLogger.debug(log, CLASS_NAME, "delete", "OUT");
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "No se ha podido borrar la Dirección porque tiene Trabajos asociados", e.getMessage() );
			return null;
		}
		return doInit();
	}

	@Override
	public String back(){
		filteredAddresses = addresses;
		String pop = getStackBean().popPage();
		return ( pop == null?BeanNameUtil.PAGE_PRINCIPAL_ADDRESS:pop);
	}
	
	@Override
	public String cancel(){
		return back();
	}
	
	public String reloadAddress(){

		Collection<Customer> customers = customerService.getAllCustomers();
		addresses = new ArrayList<Address>();
		for ( Customer c:customers )
			addresses.addAll( c.getAddress() );
		filteredAddresses = addresses;

		// TIPO 2, Aviso: todos los Clientes
		if ( filterForTypeWork == 2 )
			return BeanNameUtil.PAGE_PRINCIPAL_ADDRESS;

		// Resto de Tipos, tenemos la fecha en cuenta
		if ( filterForDate== null ){
			applicationBean.sendMessageInfo("Info", "No se ha seleccionado fecha, aparecerán todas las Direcciones");
			return BeanNameUtil.PAGE_PRINCIPAL_ADDRESS;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime( filterForDate );
		int month = cal.get(Calendar.MONTH) + 1;

		List<Address> monthAddresses = new ArrayList<Address>();
			
		for ( Address a:addresses )
			if ( a.includeMonth(month) )
				monthAddresses.add( a );
		
		addresses = monthAddresses;
		filteredAddresses = addresses;
		
		return BeanNameUtil.PAGE_PRINCIPAL_ADDRESS;
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
	
	
	public StackNavigationBean getStackBean() {
		return stackBean;
	}
	public void setStackBean(StackNavigationBean stackBean) {
		this.stackBean = stackBean;
	}
	public Customer getSelectedCustomer() {
		return selectedCustomer;
	}
	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}
	public Collection<Address> getAddresses() {
		return addresses;
	}
	public Collection<Work> getCurrentWorks() {
		return currentWorks;
	}
	public void setCurrentWorks(Collection<Work> currentWorks) {
		this.currentWorks = currentWorks;
	}
	public void setAddresses(Collection<Address> addresses) {
		this.addresses = addresses;
	}
	public Collection<Address> getFilteredAddresses() {
		return filteredAddresses;
	}
	public void setFilteredAddresses(Collection<Address> filteredAddresses) {
		this.filteredAddresses = filteredAddresses;
	}
	public Address getSelectedAddress() {
		return selectedAddress;
	}
	public List<String> getSelectedMonths() {
		return selectedMonths;
	}
	public void setSelectedMonths(List<String> selectedMonths) {
		this.selectedMonths = selectedMonths;
	}
	public void setSelectedAddress(Address selectedAddress) {
		this.selectedAddress = selectedAddress;
	}
	public String getMonthFilter() {
		return monthFilter;
	}
	public void setMonthFilter(String monthFilter) {
		this.monthFilter = monthFilter;
	}
	public String getPeriod(){
		int size = ((selectedMonths!=null && !selectedMonths.isEmpty())?selectedMonths.size():0);
		
		if ( size==1 )
			return "ANUAL";
		if ( size==2 )
			return "SEMESTRAL";
		if ( size==3 )
			return "TRIMESTRAL";
		if ( size==4 )
			return "CUATRIMESTRAL";
		if ( size==12 )
			return "MENSUAL";
		
		return "OTROS";
	}
	public Date getFilterForDate() {
		return filterForDate;
	}
	public int getFilterForTypeWork() {
		return filterForTypeWork;
	}
	public void setFilterForTypeWork(int filterForTypeWork) {
		this.filterForTypeWork = filterForTypeWork;
	}
	public void setFilterForDate(Date filterForDate) {
		this.filterForDate = filterForDate;
	}
	///////////////////////////////////////////////////////////////
	//                Fin de los Getters y Setters               //
	///////////////////////////////////////////////////////////////
}