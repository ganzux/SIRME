package com.alcedomoreno.sirme.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.primefaces.event.CellEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alcedomoreno.sirme.business.data.Address;
import com.alcedomoreno.sirme.business.data.Customer;
import com.alcedomoreno.sirme.business.data.Manager;
import com.alcedomoreno.sirme.business.data.Month.MonthE;
import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.services.CustomerService;
import com.alcedomoreno.sirme.business.services.OtherService;
import com.alcedomoreno.sirme.business.services.QuestionService;
import com.alcedomoreno.sirme.business.services.UsersService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.business.util.TransactionException;
import com.alcedomoreno.sirme.business.util.ValidationException;
import com.alcedomoreno.sirme.core.util.MyLogger;
import com.alcedomoreno.sirme.web.excel.CustomerXLSExtract;
import com.alcedomoreno.sirme.web.excel.ManagerDocExtract;
import com.alcedomoreno.sirme.web.schedulers.IPCronService;
import com.alcedomoreno.sirme.web.util.BeanNameUtil;

@Component( BeanNameUtil.SETTINGS_BEAN )
@Scope("session")
public class SettingsBean extends ManagedBean {
	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger( SettingsBean.class );
	private static final String CLASS_NAME = BeanNameUtil.SETTINGS_BEAN;
	
	@Resource( name=BeanNameUtil.APP_BEAN)
	private ApplicationBean applicationBean;
	
	@Resource(name = ServiceConstants.CUSTOMER_SERVICE)
	private CustomerService customerService;
	
	@Resource(name = ServiceConstants.USER_SERVICE )
	private UsersService usersService;

	@Resource(name = ServiceConstants.OTHER_SERVICE )
	private OtherService otherService;
	
	@Resource(name = ServiceConstants.QUESTION_SERVICE )
	private QuestionService questionService;
	
	@Resource( name = "IPDNSCron")
	protected IPCronService cronService;
	
	private String newPass1;
	private String newPass2;
	
	private String processDir = "C:\\w";
	
	private String processDirManager = "C:\\a";
	
	private String sql;
	
	private List<String> restCalls;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	public SettingsBean(){				
		MyLogger.info(log, CLASS_NAME, "SettingsBean", "New Instance");
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
		filter();
		MyLogger.debug(log, CLASS_NAME, "doInit", "OUT");
		return BeanNameUtil.PAGE_PRINCIPAL_CONFIG;
	}
	
	public void filter(){
	}

	@Override
	public String prepareAction(){
		MyLogger.debug(log, CLASS_NAME, "prepareAction", "IN", getPageOption());

		MyLogger.debug(log, CLASS_NAME, "prepareAction", "OUT",getPageOption());
		
		return BeanNameUtil.PAGE_PRINCIPAL_CONFIG;
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
			usersService.updatePassword(getUser().getIdUser(), newPass1, newPass2);
			applicationBean.sendMessageInfo("web.operacion.correcta", "Se cambio la contraseña con exito");
			MyLogger.debug(log, CLASS_NAME, "save", "OUT");
		} catch(ValidationException e){
			applicationBean.sendMessageError("web.error.general.validacion", e.getMessage() );
			return null;
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "No se puede cambiar la contrasena" );
			return null;
		}
		return doInit();
	}

	@Override
	public String update(){
		return doInit();
	}
	
	@Override
	public String delete(){
		return doInit();
	}

	@Override
	public String back(){
		return BeanNameUtil.PAGE_PRINCIPAL_CONFIG;
	}
	
	@Override
	public String cancel(){
		newPass1 = "";
		newPass2 = "";
		return BeanNameUtil.PAGE_PRINCIPAL_CONFIG;
	}
	
	public void doRestLogin(){
		restCalls = applicationBean.getRestLogin();
	}
	public void doRestPassword(){
		restCalls = applicationBean.getRestPassword();
	}
	public void doRestQuestions(){
		restCalls = applicationBean.getRestQuestions();
	}
	public void doRestAdvices(){
		restCalls = applicationBean.getRestAdvices();
	}
	public void doRestWorks(){
		restCalls = applicationBean.getRestWorks();
	}
	
	public void process(){

		int correctos = 0;
		int fallos = 0;
		List<String> oks = new ArrayList<String>();
		List<String[]> kos = new ArrayList<String[]>();

		try{
			 File dir = new File( processDir );
			  for (File child : dir.listFiles())
			    if ( child.isFile() ){
			    	try{
			    		FileInputStream fis = new FileInputStream( child );

			    		Customer customer = CustomerXLSExtract.getInstance().getCustomerFromXLS( fis );
			    		Address address = null;
			    		for ( Address a:customer.getAddress() )
			    			address = a;
			    		address.setCustomer( customer );

						boolean oldC = false;
						Customer filter = new Customer();
						filter.setIdCustomer( 0 );
						filter.setCodeCustomer( customer.getCodeCustomer() );
						filter = customerService.get( filter );
						if ( filter != null )
							if ( filter.getCodeCustomer().equalsIgnoreCase( customer.getCodeCustomer() ) ){
								oldC = true;
								address.setCustomer( filter );
							}

						List<String> selectedMonths = new ArrayList<String>();
						if ( address.getMonths()!=null && !address.getMonths().isEmpty() )
							for ( MonthE m:address.getMonths() )
								selectedMonths.add( String.valueOf( m.getValue() ) );
						address.setMonths( selectedMonths );

						if ( !oldC ){
							customerService.save( customer );
						}
						else
							customerService.save( address );

						correctos++;
			    		oks.add( child.getName() );
			    		
			    		child.delete();
			    		
			    		MyLogger.debug(log, CLASS_NAME, "process", "OK",child.getPath());
			    	} catch (Exception e){
			    		
			    		String errorS = "";
			    		
			    		try{
			    			errorS = ((org.hibernate.exception.ConstraintViolationException)((org.springframework.dao.DataIntegrityViolationException)e.getCause()).getCause()).getCause().getMessage();
			    		} catch ( Exception e1 ){
			    			errorS = e.getMessage();
			    		}
			    		MyLogger.debug(log, CLASS_NAME, "process", "KO", child.getPath(),errorS);
			    		fallos++;
			    		String[] ko = new String[]{child.getName(),e.getMessage()};
			    		kos.add( ko );
			    	}
			    }
		} catch ( Exception e){
			applicationBean.sendMessageError("web.error.general", "No se puede leer el directorio" );
		}
		applicationBean.sendMessageInfo("Info", "Proceso finalizado con " + correctos + " OK y " + fallos + " KO.");
		System.out.println("Proceso finalizado con " + correctos + " OK y " + fallos + " KO.");
		
		try {
			FileWriter fichero = new FileWriter( processDir + "\\ok.txt");
			for (String ok:oks)
				fichero.write( ok + "\r\n");
			fichero.close();
			
			FileWriter fichero2 = new FileWriter( processDir + "\\ko.txt");
			for (String[] ko:kos)
				fichero2.write( ko[0] + "\t" + ko[1] + "\r\n");
			fichero2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void prepareReports(){
		Collection<Report> reports = questionService.getAll();
		questionService.getWork( 21 );
		questionService.getWork( 20 );
	}
	
	public void forceDNS(){
		try{
			cronService.resetDynDNS();
			applicationBean.sendMessageInfo("web.operacion.correcta", "Se actualizo DynDNS con exito");
		} catch (Exception e){
			applicationBean.sendMessageError("web.error.general.transaccion", "Error actualizando DynDNS" );
		}
	}

	public void resetConnections(){
		try{
			applicationBean.resetUserAccess();
			applicationBean.sendMessageInfo("web.operacion.correcta", "Se resetearon las conexiones con exito");
		} catch (Exception e){
			applicationBean.sendMessageError("web.error.general.transaccion", "Error reseteando las conexiones" );
		}
	}

	public void launchSql(){
		try{
			int i = otherService.launchQuery( sql );
			applicationBean.sendMessageInfo("web.operacion.correcta", "Se lanzo la query con resultado " + i);
		} catch (Exception e){
			applicationBean.sendMessageError("web.error.general.transaccion", "Error lanzando la query: ?", e.getMessage() );
		}
	}
	
	public void processDirManager(){
		int correctos = 0;
		int fallos = 0;
		List<String> oks = new ArrayList<String>();
		List<String[]> kos = new ArrayList<String[]>();

		try{
			 File dir = new File( processDirManager );
			  for (File child : dir.listFiles())
				if ( child.isDirectory() )
					for (File child2 : child.listFiles()){
			    if ( child2.isFile() ){
			    	try{
			    		FileInputStream fis = new FileInputStream( child2 );

			    		Manager manager = ManagerDocExtract.getInstance().getCustomerFromDOC( fis );
			    		
			    		int id = customerService.save( manager );
			    		manager.setIdManager( id );
			    		
			    		if ( manager.getCustomers() != null ){
			    			Iterator<Customer> customIt = manager.getCustomers().iterator();	
			    			while( customIt.hasNext() ){
			    				Customer c = customIt.next();
			    				c.setIdCustomer( 0 );
			    				c = customerService.get( c );
			    				if ( c != null )
			    					otherService.launchQuery("UPDATE customers SET idManager = " + id + " WHERE idCustomer = " + c.getIdCustomer() );
			    			}
			    		}

			    		correctos++;
			    		oks.add( child2.getName() );
			    		child2.delete();

			    		MyLogger.debug(log, CLASS_NAME, "process", "OK",child2.getPath());
			    	} catch (Exception e){
			    		
			    		String errorS = "";
			    		
			    		try{
			    			errorS = ((org.hibernate.exception.ConstraintViolationException)((org.springframework.dao.DataIntegrityViolationException)e.getCause()).getCause()).getCause().getMessage();
			    		} catch ( Exception e1 ){
			    			errorS = e.getMessage();
			    		}
			    		MyLogger.debug(log, CLASS_NAME, "process", "KO", child2.getPath(),errorS);
			    		fallos++;
			    		String[] ko = new String[]{child2.getName(),e.getMessage()};
			    		kos.add( ko );
			    	}
			    }
			  }
		} catch ( Exception e){
			applicationBean.sendMessageError("web.error.general", "No se puede leer el directorio" );
		}
		applicationBean.sendMessageInfo("Info", "Proceso finalizado con " + correctos + " OK y " + fallos + " KO.");
		System.out.println("Proceso finalizado con " + correctos + " OK y " + fallos + " KO.");
		
		try {
			FileWriter fichero = new FileWriter( processDirManager + "\\ok.txt");
			for (String ok:oks)
				fichero.write( ok + "\r\n");
			fichero.close();
			
			FileWriter fichero2 = new FileWriter( processDirManager + "\\ko.txt");
			for (String[] ko:kos)
				fichero2.write( ko[0] + "\t" + ko[1] + "\r\n");
			fichero2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	///////////////////////////////////////////////////////////////
	//                 Fin de los Metodos Publicos               //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      Metodos Privados                     //
	///////////////////////////////////////////////////////////////

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
	public String getProcessDir() {
		return processDir;
	}
	public void setProcessDir(String processDir) {
		this.processDir = processDir;
	}
	public String getProcessDirManager() {
		return processDirManager;
	}
	public void setProcessDirManager(String processDirManager) {
		this.processDirManager = processDirManager;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<String> getRestCalls() {
		return restCalls;
	}
	public void setRestCalls(List<String> restCalls) {
		this.restCalls = restCalls;
	}

	///////////////////////////////////////////////////////////////
	//                Fin de los Getters y Setters               //
	///////////////////////////////////////////////////////////////
}
