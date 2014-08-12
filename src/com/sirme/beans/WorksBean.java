package com.sirme.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alvex.firext.validation.Validator;
import com.sirme.bussiness.Address;
import com.sirme.bussiness.Customer;
import com.sirme.bussiness.Question;
import com.sirme.bussiness.QuestionGroup;
import com.sirme.bussiness.Reply;
import com.sirme.bussiness.ReplyGroup;
import com.sirme.bussiness.Report;
import com.sirme.bussiness.Team;
import com.sirme.bussiness.TypeWork;
import com.sirme.bussiness.Work;
import com.sirme.jasper.GenerateReport;
import com.sirme.services.ICustomerService;
import com.sirme.services.IQuestionService;
import com.sirme.services.ITeamService;
import com.sirme.services.IUpdatedService;
import com.sirme.services.IWorkService;
import com.sirme.util.BeanNameUtil;
import com.sirme.util.ImageUtil;
import com.sirme.util.MyLogger;
import com.sirme.util.OutputUtil;
import com.sirme.util.SpringConstants;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;
import com.sirme.util.ZipUtil;


@Component( BeanNameUtil.WORKS_BEAN )
@Scope("session")
public class WorksBean extends ManagedBean {
	
	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger( WorksBean.class );
	private static final String CLASS_NAME = BeanNameUtil.WORKS_BEAN;
	
	@Resource( name=BeanNameUtil.APP_BEAN)
	private ApplicationBean applicationBean;
	
	@Resource(name = SpringConstants.TEAM_SERVICE)
	protected ITeamService teamService;
	
	@Resource(name = SpringConstants.UPDATED_SERVICE)
	protected IUpdatedService updateService;
	
	@Resource(name = SpringConstants.CUSTOMER_SERVICE)
	protected ICustomerService customerService;
	
	@Resource(name = SpringConstants.WORK_SERVICE)
	protected IWorkService workService;

	@Resource( name = BeanNameUtil.STACK_BEAN )
	protected StackNavigationBean stackBean;
	
	@Resource(name = SpringConstants.QUESTION_SERVICE )
	private IQuestionService questionService;

	private Collection<Work> works;
	private Collection<Work> filteredWorks;
	private Work selectedWork;
	
	private Report selectedReport;
	private Reply selectedReply;

	private Collection<Team> allTeams;
	private Collection<Customer> allCustomers;
	
	private Customer selectedCustomer;
	private Address selectedAddress;
	private Team selectedTeam;
	
	private Date dateFilterI;
	private Date dateFilterE;
	
	private Collection<TypeWork> allTypeWorks;
	
	private ReplyGroup selectedReplyGroup;
	private List<QuestionGroup> questionGroups;
	private QuestionGroup selectedQuestionGroups;

	private Collection<Report> avaiableReports;
	private int selectedAddTypeReport;
	
	private ByteArrayOutputStream pdf;
	
	private UploadedFile file;
	private InputStream newSign;
	
	private boolean showAlbaranAndStatus = false;
	
	private int reportInView;
	
	private String downloadOption;
	
	private SelectItem[] statusOptions;
	
	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	public WorksBean(){				
		MyLogger.info(log, CLASS_NAME, "WorksBean", "New Instance");
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

		allTeams	= teamService.getAll();
		allCustomers= customerService.getAllCustomers();
		works		= workService.getAll();
		
		dateFilterI = null;
		dateFilterE = null;
		
		allTypeWorks = new ArrayList<TypeWork>();
		allTypeWorks.add( new TypeWork(1) );
		allTypeWorks.add( new TypeWork(2) );

		showAlbaranAndStatus = false;
		
		reportInView = Report.REPORT_ALBARAN;
		
		statusOptions = new SelectItem[ 7 ];
		statusOptions[ 0 ] = new SelectItem("", "Seleccione...");
		statusOptions[ 1 ] = new SelectItem("1", "Abiertos");
		
		statusOptions[ 2 ] = new SelectItem("11", "Abierto");
		statusOptions[ 3 ] = new SelectItem("21", "Descargado");
		statusOptions[ 4 ] = new SelectItem("31", "Recibido");
		statusOptions[ 5 ] = new SelectItem("41", "Pendiente");
		statusOptions[ 6 ] = new SelectItem("0", "Cerrado");

		filter();
		
		setMyDate( new Date() );
		
		MyLogger.debug(log, CLASS_NAME, "doInit", "OUT");
		return BeanNameUtil.PAGE_PRINCIPAL_WORKS;
	}
	
	public void filter(){
		filteredWorks= works;
	}

	@Override
	public String prepareAction(){
		MyLogger.debug(log, CLASS_NAME, "prepareAction", "IN", getPageOption(), selectedWork);

		if ( getPageOption() == applicationBean.getOptionSave() ){
			selectedWork = new Work();
			selectedWork.setTypeWork( new TypeWork() );
			selectedWork.setReports( new ArrayList<Report>() );
			selectedWork.setAddress( new Address() );
		} else{
			selectedWork = questionService.getWork( selectedWork.getIdWork() );
		}
		
		selectedCustomer= selectedWork==null?null:selectedWork.getCustomer();
		selectedAddress	= selectedWork==null?null:selectedWork.getAddress();
		selectedTeam	= selectedWork==null?null:selectedWork.getTeam();

		reloadAvaiableReports();

		MyLogger.debug(log, CLASS_NAME, "prepareAction", "OUT",getPageOption(), selectedWork);
		
		if ( getPageOption() == applicationBean.getOptionUpdateSign() )
			return BeanNameUtil.PAGE_SECONDARY_SIGN;

		return BeanNameUtil.PAGE_SECONDARY_WORKS;
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
			selectedWork.setCustomer( selectedCustomer );
			selectedWork.setAddress( selectedAddress );
			selectedWork.setTeam( selectedTeam );
			int nxtAlbaran = workService.save( selectedWork );
			applicationBean.sendMessageInfo("web.operacion.correcta", "works.save.ok", String.valueOf(nxtAlbaran) );
			updateService.refreshDate();
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
			selectedWork.setCustomer( selectedCustomer );
			selectedWork.setAddress( selectedAddress );
			selectedWork.setTeam( selectedTeam );
			workService.update( selectedWork );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.update");
			updateService.refreshDate();
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

	public String close(){
		try{
			MyLogger.debug(log, CLASS_NAME, "close", "IN");
			selectedWork.setStatus( String.valueOf(Work.STATUS_CERRADO) );
			workService.update( selectedWork );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.update");
			updateService.refreshDate();
			MyLogger.debug(log, CLASS_NAME, "close", "OUT");
		} catch(ValidationException e){
			applicationBean.sendMessageError("web.error.general.validacion", "web.error.general.xp", e.getMessage() );
			return null;
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "No se ha podido cerrar el parte" );
			return null;
		}
		return doInit();
	}

	public String reOpen(){
		try{
			MyLogger.debug(log, CLASS_NAME, "reOpen", "IN");
			workService.changeStatus(selectedWork.getIdWork(), Work.STATUS_ABIERTO);
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.update");
			updateService.refreshDate();
			MyLogger.debug(log, CLASS_NAME, "reOpen", "OUT");
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "No se ha podido Abrir el parte" );
			return null;
		}
		return doInit();
	}

	public String reReceive(){
		try{
			MyLogger.debug(log, CLASS_NAME, "reReceive", "IN");
			workService.changeStatus(selectedWork.getIdWork(), Work.STATUS_RECIBIDO);
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.update");
			updateService.refreshDate();
			MyLogger.debug(log, CLASS_NAME, "reReceive", "OUT");
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "No se ha podido establecer el parte como Recibido" );
			return null;
		}
		return doInit();
	}
	
	public String pteEntrega(){
		try{
			MyLogger.debug(log, CLASS_NAME, "pteEntrega", "IN");
			workService.changeStatus(selectedWork.getIdWork(), Work.STATUS_PTE_ENTREGA);
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.update");
			updateService.refreshDate();
			MyLogger.debug(log, CLASS_NAME, "pteEntrega", "OUT");
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "No se ha podido establecer el parte como Recibido" );
			return null;
		}
		return doInit();
	}
	
	@Override
	public String delete(){
		try{
			MyLogger.debug(log, CLASS_NAME, "delete", "IN");
			selectedWork.setCustomer( selectedCustomer );
			selectedWork.setAddress( selectedAddress );
			selectedWork.setTeam( selectedTeam );
			workService.delete( selectedWork );
			applicationBean.sendMessageInfo("web.operacion.correcta", "web.operacion.correcta.delete");
			updateService.refreshDate();
			MyLogger.debug(log, CLASS_NAME, "delete", "OUT");
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "Código del Cliente repetido" );
			return null;
		}
		return doInit();
	}

	@Override
	public String back(){
		filteredWorks = works;
		String pop = getStackBean().popPage();
		return ( pop == null?BeanNameUtil.PAGE_PRINCIPAL_WORKS:pop) ;
	}
	
	@Override
	public String cancel(){
		filteredWorks = works;
		return BeanNameUtil.PAGE_PRINCIPAL_WORKS;
	}
	
	public String prepareReplies(){

		Comparator<Reply> comparator = new Comparator<Reply>() {
		    public int compare(Reply c2, Reply c1) {
		        return c2.getQuestion().getOrder() - c1.getQuestion().getOrder();
		    }
		};
		if ( selectedReport.getReplyGroups() != null )
			for ( ReplyGroup r:selectedReport.getReplyGroups() )
				Collections.sort( r.getReplies(), comparator);

		return BeanNameUtil.PAGE_PRINCIPAL_REPLIES;
	}
	
	public void removeReport(){
		selectedWork.getReports().remove( selectedReport );
		reloadAvaiableReports();
	}
	
	public void filterDataTable(){
		if (  dateFilterE!=null && dateFilterI!=null && dateFilterI.before(dateFilterE) ){
			filteredWorks = new ArrayList<Work>();
			for ( Work w:works )
				if ( w.getDate().before(dateFilterE) && w.getDate().after(dateFilterI) )
					filteredWorks.add( w );
		} else 
			filteredWorks = works;
	}
	
	public void changeDate(SelectEvent e){
		try{
			Date d = (Date) e.getObject();
			selectedWork.setDate( d );
		} catch( Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public void handleChangeTypeWork(ValueChangeEvent event){
		try{
			selectedWork.setTypeWork( new TypeWork( (Integer)event.getNewValue() ) ); 
		} catch( Exception ex ){
			ex.printStackTrace();
		}
	}
	
	public String handleChangeAddress(){
		try{
			selectedWork.setAddress( selectedAddress );
			
			Work lastWork = questionService.getLastWorkByAddress( selectedAddress.getIdAddress() );
			if ( lastWork != null && lastWork.getReports() != null )
				for ( Report r:lastWork.getReports() ){
					if ( r.getReplyGroups() != null )
						for ( ReplyGroup rg : r.getReplyGroups() ){
							rg.setIdReplyGroup( 0 );
							if ( rg.getReplies() != null )
								for ( Reply reply : rg.getReplies() )
									reply.setIdReply( 0 );
						}
				}
			selectedWork.setReports( lastWork.getReports() );

			if ( lastWork.getReports() == null || lastWork.getReports().isEmpty() )
				applicationBean.sendMessageInfo("Revise los Datos", "No se han encontrado Fichas para esa dirección del Servicio");
			else
				applicationBean.sendMessageInfo("Revise los Datos", "Se han cargado las últimas Fichas para esa dirección del Servicio");

			reloadAvaiableReports();
			
			return stackBean.popPage();

		} catch( Exception ex ){
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public String prepareNewElement(){

		// Se prepara la lista de Grupos de Preguntas junto con las preguntas
		questionGroups = new ArrayList<QuestionGroup>( selectedReport.getQuestionGroups() );
		Comparator<QuestionGroup> comparator = new Comparator<QuestionGroup>() {
		    public int compare(QuestionGroup c2, QuestionGroup c1) {
		        return c2.getIdQuestionGroup() - c1.getIdQuestionGroup();
		    }
		};
		Collections.sort(questionGroups, comparator);
		
		selectedQuestionGroups = null;
		selectedReplyGroup = new ReplyGroup();
		return BeanNameUtil.PAGE_SECONDARY_REPLIES;
	}
	
	public String prepareRepliesWithSelectedQuestionG(){
		selectedReplyGroup = new ReplyGroup();
		selectedReplyGroup.setWork( selectedWork );
		selectedReplyGroup.setQuestionGroup( selectedQuestionGroups );
		selectedReplyGroup.getQuestionGroup().setReport( selectedReport );
		selectedReplyGroup.setReplies( new ArrayList<Reply>() );
		
		int size = 1;

		if ( selectedReport.getReplyGroups()!=null )
			for ( ReplyGroup rg:selectedReport.getReplyGroups() )
				if ( rg.getQuestionGroup().getIdQuestionGroup() == selectedQuestionGroups.getIdQuestionGroup() )
					size++;

		selectedReplyGroup.setNameReplyGroup( String.valueOf( size ) );
		
		for ( Question q:selectedQuestionGroups.getQuestions() ){
			Reply reply = new Reply();
			reply.setQuestion( q );
			
			if ( q.getReplyType().getIdReplyType() == Validator.TYPE_AUTOINC )
				reply.setReply( String.valueOf( size ) );

			selectedReplyGroup.getReplies().add( reply );
		}

		return null;
	}
	
	public String saveElement(){
		if( validateSelectedReplyGroup() ){
			if ( selectedReport.getReplyGroups() == null )
				selectedReport.setReplyGroups( new ArrayList<ReplyGroup>() );
			selectedReport.getReplyGroups().add( selectedReplyGroup );
			return back();
		} else
			return null;
	}
	
	public String updateReport(){
		if ( testReplies( selectedReport.getReplyGroups() ) )
			return back();
		else
			return null;
	}
	
	public void removeReplyGroup(){
		try{
			selectedReport.getReplyGroups().remove( selectedReplyGroup );
			applicationBean.sendMessageInfo("web.operacion.correcta", "Elemento eliminado correctamente");
		} catch ( Exception e){
			applicationBean.sendMessageError("web.error.general", "Ha habido un error al eliminar ese elemento" );
		}
	}
	
	public void addReport(){

		for ( Report all:applicationBean.getReportsCollection() ){
			if ( selectedAddTypeReport == all.getIdReport() ){
				selectedWork.getReports().add( new Report( all ) );
				break;
			}
		}

		reloadAvaiableReports();
	}
	
	public void reloadAvaiableReports(){
		avaiableReports = new ArrayList<Report>();
		
		boolean add = true;
		for ( Report all:applicationBean.getReportsCollection() ){
			add = true;

			// Si el Report está incluído en el trabajo activo, no se incluye
			if ( selectedWork != null && selectedWork.getReports() != null )
				for ( Report r2:selectedWork.getReports() )
					if ( all.getIdReport() == r2.getIdReport() ){
						add = false;
						break;
					}

			// Si no estaba incluído, lo incluímos entre los candidatos
			if ( add )
				avaiableReports.add( all );
		}
	}

	public void viewPDF(){
	    try{
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			OutputUtil.getInstance().getPDFFile(response, pdf, "pdf", false);
	    } catch (Exception e){
	    	
	    }
	}
	
	public String preparePDF() throws Exception{
		try{
			pdf = GenerateReport.getInstance().fillPDF( selectedReport, selectedWork );
			return BeanNameUtil.PAGE_PDF;
		} catch ( Exception e ){
			applicationBean.sendMessageError("web.error.general.transaccion", "No se puede generar la ficha si no se han rellenado todos los campos obligatorios" );
		}
		return null;
	}
	
	public String prepareAlbaran() throws Exception{
		try{
			pdf = GenerateReport.getInstance().fillPDF( new Report(Report.REPORT_ALBARAN), selectedWork );
			return BeanNameUtil.PAGE_PDF;
		} catch ( Exception e ){
			applicationBean.sendMessageError("web.error.general.transaccion", "No se puede generar el Albarán." );
		}
		return null;
	}

	public String reviewDocs() throws Exception{
		setPageOption( applicationBean.getOptionAllFiles() );
		reportInView = Report.REPORT_ALBARAN;
		return prepareAlbaran();
	}
	
	public String nextPDF() throws Exception{
		try{
			if ( selectedWork.getReports() != null && !selectedWork.getReports().isEmpty() ){
				reportInView ++;
				
				if ( reportInView == selectedWork.getReports().size() + 1 )
					reportInView = Report.REPORT_ALBARAN;
				else
					selectedReport = selectedWork.getReports().get( reportInView - 1 );
			}

			return reportInView == Report.REPORT_ALBARAN ? prepareAlbaran() : preparePDF();

		} catch ( Exception e ){
			applicationBean.sendMessageError("web.error.general.transaccion", "No se puede generar el Fichero." );
		}
		return null;
	}
	
	public String downloadPDF() throws Exception{
		try{
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			OutputUtil.getInstance().getPDFFile(response, pdf, "pdf", true);
	    } catch (Exception e){
	    	
	    }
		return null;
	}
	
	public String downloadAll() throws Exception{
		try{
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			ByteArrayOutputStream zip = ZipUtil.getInstance().fillZip( selectedWork );
			OutputUtil.getInstance().getZipFile(response, zip, "Trabajo", true);
	    } catch (Exception e){
	    	applicationBean.sendMessageError("web.error.general.transaccion", "No se puede generar el Fichero." );
	    }
		return null;
	}
	
	public String addSign(){
		try{
			MyLogger.debug(log, CLASS_NAME, "addSign", "IN");
			newSign = file.getInputstream();
			workService.update(selectedWork, newSign, file.getFileName(), file.getSize());
			applicationBean.sendMessageInfo("web.operacion.correcta", "Firma Cambiada correctamente" );
			MyLogger.debug(log, CLASS_NAME, "addSign", "OUT");
		} catch(ValidationException e){
			applicationBean.sendMessageError("web.error.general.validacion", "web.error.general.xp", e.getMessage() );
			return null;
		} catch(TransactionException e){
			applicationBean.sendMessageError("web.error.general.transaccion", "No se puede actualizar la firma" );
			return null;
		} catch (IOException e) {
			applicationBean.sendMessageError("web.error.general.transaccion", "No se puede actualizar la firma" );
			return null;
		}
		return doInit();
	}

	public void rotate(){
		try{
			MyLogger.debug(log, CLASS_NAME, "rotate", "IN");
			ImageUtil.getInstance().rotate90Right(selectedWork.getSignpath(), selectedWork.getSignpath(), "JPG");
			applicationBean.sendMessageInfo("web.operacion.correcta", "Ha rotado la imagen 90º a la derecha de manera correcta" );
			updateService.refreshDate();
			MyLogger.debug(log, CLASS_NAME, "rotate", "OUT");
		} catch(Exception e){
			applicationBean.sendMessageError("web.error.general", "Ha ocurrido un error al rotar la imagen. Contacte con el administrador: " + e.getMessage() );
		}
	}

	public boolean isNewWorks(){
		return ! updateService.isUpdated( getMyDate() );
	}
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los Métodos Públicos               //
	///////////////////////////////////////////////////////////////
	
	
	
	///////////////////////////////////////////////////////////////
	//                      Métodos Privados                     //
	///////////////////////////////////////////////////////////////

	private boolean testReplies( Collection<ReplyGroup> replieGroups ){
		if ( replieGroups != null )
			for ( ReplyGroup replieGroup:replieGroups )
				if ( replieGroup.getReplies() != null )
					for ( Reply reply:replieGroup.getReplies() ){
						String rpl = reply.getReply();
						String qst = reply.getQuestion().getQuestion();
						if ( rpl != null && !rpl.trim().isEmpty() ){
							try {
								rpl = Validator.getInstance().validate(rpl, reply.getQuestion().getReplyType().getIdReplyType() );
								reply.setReply( rpl );
							} catch (com.alvex.firext.validation.ValidationException e) {
								applicationBean.sendMessageError("web.error.general", e.getMessage() + "(Campo ? de ?)", qst,replieGroup.getNameReplyGroup() );
								return false;
							}
						}
					}
		return true;
	}

	private boolean validateSelectedReplyGroup(){
		if ( selectedQuestionGroups == null ){
			applicationBean.sendMessageError("web.error.general", "Debe indicar la zona de la Ficha" );
			return false;
		}
		if ( selectedReplyGroup.getNameReplyGroup()==null || selectedReplyGroup.getNameReplyGroup().trim().isEmpty() ){
			applicationBean.sendMessageError("web.error.general", "Debe indicar un identificador del elemento" );
			return false;
		}
		
		if ( selectedQuestionGroups.getTimes() != null && selectedQuestionGroups.getTimes() > 0 && selectedReport.getReplyGroups() != null ){
			int maxTimes = selectedQuestionGroups.getTimes();
			int count = 0;
			int myId = selectedQuestionGroups.getIdQuestionGroup();
			
			for ( ReplyGroup rg: selectedReport.getReplyGroups() )
				if ( rg.getQuestionGroup().getIdQuestionGroup() == myId )
					count++;
			if ( count >= maxTimes ){
				applicationBean.sendMessageError("web.error.general", "Ya ha añadido " + count + " zona " + selectedQuestionGroups.getNameQuestionGroup() );
				return false;
			}
		}
		
		return true;
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
	public Collection<Work> getWorks() {
		return works;
	}
	public void setWorks(Collection<Work> works) {
		this.works = works;
	}
	public Collection<Work> getFilteredWorks() {
		return filteredWorks;
	}
	public void setFilteredWorks(Collection<Work> filteredWorks) {
		this.filteredWorks = filteredWorks;
	}
	public Work getSelectedWork() {
		return selectedWork;
	}
	public void setSelectedWork(Work selectedWork) {
		this.selectedWork = selectedWork;
	}
	public Collection<Team> getAllTeams() {
		return allTeams;
	}
	public void setAllTeams(Collection<Team> allTeams) {
		this.allTeams = allTeams;
	}
	public Collection<Customer> getAllCustomers() {
		return allCustomers;
	}
	public void setAllCustomers(Collection<Customer> allCustomers) {
		this.allCustomers = allCustomers;
	}
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
	public Address getSelectedAddress() {
		return selectedAddress;
	}
	public void setSelectedAddress(Address selectedAddress) {
		this.selectedAddress = selectedAddress;
	}
	public Team getSelectedTeam() {
		return selectedTeam;
	}
	public void setSelectedTeam(Team selectedTeam) {
		this.selectedTeam = selectedTeam;
	}
	public Date getDateFilterI() {
		return dateFilterI;
	}
	public void setDateFilterI(Date dateFilterI) {
		this.dateFilterI = dateFilterI;
	}
	public Date getDateFilterE() {
		return dateFilterE;
	}
	public void setDateFilterE(Date dateFilterE) {
		this.dateFilterE = dateFilterE;
	}
	public Collection<TypeWork> getAllTypeWorks() {
		return allTypeWorks;
	}
	public void setAllTypeWorks(Collection<TypeWork> allTypeWorks) {
		this.allTypeWorks = allTypeWorks;
	}
	public Report getSelectedReport() {
		return selectedReport;
	}
	public Collection<Report> getAvaiableReports() {
		return avaiableReports;
	}
	public void setAvaiableReports(Collection<Report> avaiableReports) {
		this.avaiableReports = avaiableReports;
	}
	public int getSelectedAddTypeReport() {
		return selectedAddTypeReport;
	}
	public void setSelectedAddTypeReport(int selectedAddTypeReport) {
		this.selectedAddTypeReport = selectedAddTypeReport;
	}
	public void setSelectedReport(Report selectedReport) {
		this.selectedReport = selectedReport;
	}
	public Reply getSelectedReply() {
		return selectedReply;
	}
	public void setSelectedReply(Reply selectedReply) {
		this.selectedReply = selectedReply;
	}
	public ReplyGroup getSelectedReplyGroup() {
		return selectedReplyGroup;
	}
	public void setSelectedReplyGroup(ReplyGroup selectedReplyGroup) {
		this.selectedReplyGroup = selectedReplyGroup;
	}
	public List<QuestionGroup> getQuestionGroups() {
		return questionGroups;
	}
	public void setQuestionGroups(List<QuestionGroup> questionGroups) {
		this.questionGroups = questionGroups;
	}
	public QuestionGroup getSelectedQuestionGroups() {
		return selectedQuestionGroups;
	}
	public void setSelectedQuestionGroups(QuestionGroup selectedQuestionGroups) {
		this.selectedQuestionGroups = selectedQuestionGroups;
	}
	public UploadedFile getFile() {
		return file;
	}
	public boolean isShowAlbaranAndStatus() {
		return showAlbaranAndStatus;
	}
	public void setShowAlbaranAndStatus(boolean showAlbaranAndStatus) {
		this.showAlbaranAndStatus = showAlbaranAndStatus;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public String getDownloadOption() {
		return downloadOption;
	}
	public void setDownloadOption(String downloadOption) {
		this.downloadOption = downloadOption;
	}
	public SelectItem[] getStatusOptions() {
		return statusOptions;
	}
	public void setStatusOptions(SelectItem[] statusOptions) {
		this.statusOptions = statusOptions;
	}

	///////////////////////////////////////////////////////////////
	//                Fin de los Getters y Setters               //
	///////////////////////////////////////////////////////////////
}
