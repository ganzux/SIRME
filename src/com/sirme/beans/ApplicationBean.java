package com.sirme.beans;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.context.RequestContext;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.sirme.bussiness.Report;
import com.sirme.bussiness.Team;
import com.sirme.bussiness.TypeCustomer;
import com.sirme.bussiness.Work;
import com.sirme.services.IQuestionService;
import com.sirme.util.BeanNameUtil;
import com.sirme.util.ConfigService;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;
import com.sirme.util.StringUtil;

@Component( BeanNameUtil.APP_BEAN )
@Scope("application")
public class ApplicationBean implements Serializable {

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger( ApplicationBean.class );
	private static final String CLASS_NAME = BeanNameUtil.APP_BEAN;
	
	@Resource(name = SpringConstants.QUESTION_SERVICE)
	protected IQuestionService reportQuestionervice;
	
	@Resource
	private ConfigService cfg;
	
	private int failures = 0;

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	public ApplicationBean(){
		MyLogger.info(log, CLASS_NAME, "ApplicationBean", "New Instance");
		initFilters();
	}	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	/////////////////////////////////////////////////////////////////////////
	//                       Colecciones Compartidas                       //
	/////////////////////////////////////////////////////////////////////////

	private SelectItem[] typeOptions;
	private SelectItem[] activeOptions;
	private Collection<TypeCustomer> allTypeCustomers;
	
	private void initFilters(){
		typeOptions = new SelectItem[ 3 ];
		activeOptions = new SelectItem[ 3 ];

		activeOptions[ 0 ] = new SelectItem(""		, "Seleccione...");  
		activeOptions[ 1 ] = new SelectItem("true", "Sí");
		activeOptions[ 2 ] = new SelectItem("false", "No");
		
		allTypeCustomers = new ArrayList<TypeCustomer>();
		typeOptions[ 0 ] = new SelectItem("", "Seleccione...");  
		allTypeCustomers.add( new TypeCustomer(1) );
		typeOptions[ 1 ] = new SelectItem("1",TypeCustomer.TYPE_COMUNIDADS );
		allTypeCustomers.add( new TypeCustomer(2) );
		typeOptions[ 2 ] = new SelectItem("2",TypeCustomer.TYPE_CLIENTE );
	}
	
	public SelectItem[] getActiveOptions() {
		return activeOptions;
	}
	public void setActiveOptions(SelectItem[] activeOptions) {
		this.activeOptions = activeOptions;
	}
	public SelectItem[] getTypeOptions() {
		return typeOptions;
	}
	public void setTypeOptions(SelectItem[] typeOptions) {
		this.typeOptions = typeOptions;
	}
	public Collection<TypeCustomer> getAllTypeCustomers() {
		return allTypeCustomers;
	}
	public void setAllTypeCustomers(Collection<TypeCustomer> allTypeCustomers) {
		this.allTypeCustomers = allTypeCustomers;
	}

	/////////////////////////////////////////////////////////////////////////
	//                    Fin de Colecciones Compartidas                   //
	/////////////////////////////////////////////////////////////////////////



	/////////////////////////////////////////////////////////////////////////
	//                            Control REST                             //
	/////////////////////////////////////////////////////////////////////////
	private Map<String,Object[]> restData;
	
	public void addRestLogin(Team team, String device){
		addRestCall(team, device, 1);
	}
	public List<String> getRestLogin(){
		return getRestCall( 1 );
	}
	public void addRestPassword(Team team){
		addRestCall(team, "", 2);
	}
	public List<String> getRestPassword(){
		return getRestCall( 2 );
	}
	public void addRestQuestions(){
		addRestCall(new Team(), "", 3);
	}
	public List<String> getRestQuestions(){
		return getRestCall( 3 );
	}
	public void addRestAdvices( int teamId ){
		addRestCall(new Team(), teamId + "", 4);
	}
	public List<String> getRestAdvices(){
		return getRestCall( 4 );
	}
	public void addRestWorks( int teamId ){
		addRestCall(new Team(), teamId + "", 5);
	}
	public List<String> getRestWorks(){
		return getRestCall( 5 );
	}
	
	
	

	private void addRestCall( Team team, String device, int position ){
		if ( restData == null )
			restData = new HashMap<String,Object[]>();

		Object[] old = restData.get( team.getNameTeam() + ":" + device );
		if ( old == null )
			old = new Object[]{team,0,0,0,0,0,0,0,0,0,0};

		int times = 0;
		try{
			times = (int) old[ position ];
		} catch ( Exception e){
		}
		old[ position ] = ++times;

		restData.put( team.getNameTeam() + ":" + device , old );
	}
	
	private List<String> getRestCall( int position ){
		List<String> restCalls = new ArrayList<String>();

		if ( restData != null ){
			Iterator<Entry<String, Object[]>> it = restData.entrySet().iterator();
		    while ( it.hasNext() ) {
		    	Entry<String,Object[]> pairs = it.next();
		    	restCalls.add( pairs.getKey() + " - " + pairs.getValue()[position] );
			}
		}

		return restCalls;
	}
	
	/////////////////////////////////////////////////////////////////////////
	//                        Fin de Control REST                          //
	/////////////////////////////////////////////////////////////////////////



	/////////////////////////////////////////////////////////////////////////
	//                         Control de Usuarios                         //
	/////////////////////////////////////////////////////////////////////////
	private int numUsers = 0;
	private Map<String,HttpSession> sessions;
	
	private Map<String,List<Date>> userAccess;
	
	private static final String pushDataNumUsers= ":form:numUsers";
	private static final String pushUserChannel	= "/USERS_CHANNEL";
	
	public void doRegisterUser( HttpSession session ) {
		numUsers++;
		pushUsers();
		if (!getSessions().containsKey(session.getId()))
			getSessions().put(session.getId(), session);
	}
	
	public void doUnregisterUser( HttpSession session  ) {
		if ( numUsers>0 ){
			numUsers--;
			pushUsers();
		}
		if (getSessions().containsKey(session.getId()))
			getSessions().remove(session.getId());
	}
	
	private void pushUsers(){
		MyLogger.info(log, CLASS_NAME, "pushUsers", numUsers);
		PushContext pushContext = PushContextFactory.getDefault().getPushContext();
		pushContext.push( pushUserChannel, String.valueOf(numUsers) );
		try{
			RequestContext.getCurrentInstance().update( pushDataNumUsers );
		} catch(Exception e){
		}
	}
	
	public Map<String,HttpSession> getSessions() {
		if (this.sessions == null) {
			this.sessions = new HashMap<String, HttpSession>();
		}
		return this.sessions;
	}
	
	public void doRegisterOKLogin( Object u ){
		User user = (User) u;
		String key = user.getUsername();
		
		if ( userAccess == null )
			userAccess = new HashMap<String,List<Date>>();

		List<Date> dates = userAccess.get( key );
		
		if ( dates == null )
			dates = new ArrayList<Date>();
		
		dates.add( new Date() );
		
		userAccess.put(key, dates);
	}
	

	/////////////////////////////////////////////////////////////////////////
	//                      Fin de Control de Usuarios                     //
	/////////////////////////////////////////////////////////////////////////




	/////////////////////////////////////////////////////////////////////////
	//                             Properties                              //
	/////////////////////////////////////////////////////////////////////////
	public boolean isExportCustomers(){
		return cfg.isXportCustomers();
	}
	public boolean isSeeFiles(){
		return cfg.isAllowedFiles();
	}
	public boolean isGenerateZIP(){
		return cfg.isAllowedZIP();
	}
	/////////////////////////////////////////////////////////////////////////
	//                         Fin de Properties                           //
	/////////////////////////////////////////////////////////////////////////



	/////////////////////////////////////////////////////////////////////////
	//                              Contexto                               //
	/////////////////////////////////////////////////////////////////////////
	
	private String contextRoot = null;
	
	public String getContextRoot() {
		
		if (this.contextRoot == null) {
			HttpServletRequest request = (HttpServletRequest)
					FacesContext.getCurrentInstance().getExternalContext().getRequest();
			
			this.contextRoot = request.getContextPath();
		}
	
		return this.contextRoot;
	}
	/////////////////////////////////////////////////////////////////////////
	//                          Fin del Contexto                           //
	/////////////////////////////////////////////////////////////////////////



	/////////////////////////////////////////////////////////////////////////
	//                        Variables de la Vista                        //
	/////////////////////////////////////////////////////////////////////////
	private final String optionSave		= "save";
	private final String optionUpdate	= "update";
	private final String optionDelete	= "delete";
	private final String optionView		= "view";
	private final String optionClose	= "close";
	
	private final String optionUpdateSign 	= "sign";
	private final String optionaddFile 		= "file";
	private final String optionAllFiles		= "allFiles";
	
	private final String optionReOpen		= "reOpen";
	private final String optionPteEntrega	= "optionPteEntrega";
	private final String optionReReceived	= "optionReReceived";
	
	private final String generalSortOrder	= "descending";
	private final String generalRows		= "10";
	private final String generalShowEffect= "explode";
	private final String generalHideEffect= "explode";
	
	private final String generalModalPopUp= "true";
	
	private final String lifeGrowl	= "7500";
	/////////////////////////////////////////////////////////////////////////
	//                     Fin de Variables de la Vista                    //
	/////////////////////////////////////////////////////////////////////////


	/////////////////////////////////////////////////////////////////////////
	//                         Export de Documentos                        //
	/////////////////////////////////////////////////////////////////////////
	public void postProcessXLS(Object document) {
	    HSSFWorkbook wb = (HSSFWorkbook) document;
	    HSSFSheet sheet = wb.getSheetAt(0);
	    HSSFRow header = sheet.getRow(0);

	    HSSFCellStyle cellStyle = wb.createCellStyle();
	    cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
	    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	    for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
	        HSSFCell cell = header.getCell(i);

	        cell.setCellStyle(cellStyle);
	    }
	}
	  
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
	    Document pdf = (Document) document;
	    pdf.open();
	    pdf.setPageSize(PageSize.A4);

	    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	    String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "logo.jpg";

	    pdf.add(Image.getInstance(logo));
	}
	/////////////////////////////////////////////////////////////////////////
	//                      Fin de Export de Documentos                    //
	/////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////
	//                       Mensajes de la Aplicación                     //
	/////////////////////////////////////////////////////////////////////////
	private ResourceBundle messages = 	ResourceBundle.getBundle("com.sirme.resources.messages");
	private ResourceBundle configs = 	ResourceBundle.getBundle("com.sirme.resources.configview");
	private static final char WILD_CARD = '?';
	
	public boolean sendMessageInfo( String keyTitle, String keyMessage, String... strings ){
		sendMessage( FacesMessage.SEVERITY_INFO, keyTitle, keyMessage,strings);
		return true;
	}
	
	public boolean sendMessageError( String keyTitle, String keyMessage, String... strings ){
		sendMessage( FacesMessage.SEVERITY_ERROR, keyTitle, keyMessage,strings);
		return false;
	}
	
	public String getMessageBundle(String keyMessage, String... strings){
		String content	= keyMessage;
	
		try{ content= messages.getString( keyMessage ); } catch ( Exception e ){}
	
		if ( strings!=null && strings.length>0 )
			content = StringUtil.getInstance().replaceWildCardWithTexts(content, WILD_CARD, strings);
	
		return content;
	}

	private void sendMessage( Severity severity, String keyTitle, String keyMessage, String... strings){
	
		// Si el keyMessage contiene alguna coma (,), cogeremos el primer valos como mensaje y el resto
		// lo colocaremos como las cadenas restantes. Esto nos permite mandar desde cualquier capa de la
		// aplicación un mensaje con sólo keytitle y keymessage.
		if ( keyMessage.contains(",") && (strings==null || strings.length==0) ){
			String[] chains = keyMessage.split(",");
			keyMessage = chains[0];
			
			if ( chains.length > 1 ){
				strings = new String[ chains.length-1 ];
				for ( int i = 1; i < chains.length; i++  )
					strings[ i-1 ] = chains[ i ];
			}
		}
		
		String title 	= getMessageBundle(keyTitle);
		String content	= getMessageBundle(keyMessage,strings);
		
		FacesMessage message = new FacesMessage( severity, title, content );  
		FacesContext.getCurrentInstance().addMessage(null, message);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( true );
	}

	public String getConfigurationBundle(String keyMessage, String... strings){
		String content	= keyMessage;
		
		try{ content= configs.getString( keyMessage ); } catch ( Exception e ){}
		
		if ( strings!=null && strings.length>0 )
			content = StringUtil.getInstance().replaceWildCardWithTexts(content, WILD_CARD, strings);
		
		return content;
	}
	/////////////////////////////////////////////////////////////////////////
	//                   Fin de Mensajes de la Aplicación                  //
	/////////////////////////////////////////////////////////////////////////


	/////////////////////////////////////////////////////////////////////////
	//                          Reports y Preguntas                        //
	/////////////////////////////////////////////////////////////////////////
	private Collection<Report> allReportsCollection;
	private Map<Integer,Report> allReportsMap;
	public Collection<Report> getReportsCollection(){
		if ( allReportsCollection == null ){
			allReportsCollection = new ArrayList<Report>();
			allReportsCollection = reportQuestionervice.getAllWithQuestions();
		}

		return allReportsCollection;
	}
	public Report getReport( int idReport ){
		if ( allReportsMap==null ){
			allReportsMap = new HashMap<Integer,Report>();
			for ( Report r:getReportsCollection() )
				allReportsMap.put(r.getIdReport(), r);
		}
		return allReportsMap.get( idReport );
	}
	
	/////////////////////////////////////////////////////////////////////////
	//                       Fin de Reports y Preguntas                    //
	/////////////////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                          Varios                           //
	///////////////////////////////////////////////////////////////
	public TimeZone getTimeZone() {
	    return TimeZone.getDefault();
	}
	public int getStatusAbierto() {
		return Work.STATUS_ABIERTO;
	}
	public int getStatusDescargado() {
		return Work.STATUS_DESCARGADO;
	}
	public int getStatusRecibido() {
		return Work.STATUS_RECIBIDO;
	}
	public int getStatusCerrado() {
		return Work.STATUS_CERRADO;
	}
	public int getStatusPteEntrega() {
		return Work.STATUS_PTE_ENTREGA;
	}
	
	///////////////////////////////////////////////////////////////
	//                       Fin de Varios                       //
	///////////////////////////////////////////////////////////////
	
	
	///////////////////////////////////////////////////////////////
	//                      Getters y Setters                    //
	///////////////////////////////////////////////////////////////
	public int getNumUsers() {
		return numUsers;
	}
	public void setNumUsers(int num) {
		numUsers = num;
	}
	public String getOptionSave() {
		return optionSave;
	}
	public String getOptionUpdate() {
		return optionUpdate;
	}
	public String getOptionDelete() {
		return optionDelete;
	}
	public String getOptionView() {
		return optionView;
	}	
	public String getGeneralSortOrder() {
		return generalSortOrder;
	}
	public ResourceBundle getMessages() {
		return messages;
	}
	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}
	public String getGeneralRows() {
		return generalRows;
	}
	public String getOptionUpdateSign() {
		return optionUpdateSign;
	}
	public String getOptionaddFile() {
		return optionaddFile;
	}
	public String getGeneralShowEffect() {
		return generalShowEffect;
	}
	public String getGeneralHideEffect() {
		return generalHideEffect;
	}
	public String getGeneralModalPopUp() {
		return generalModalPopUp;
	}
	public String getOptionClose() {
		return optionClose;
	}
	public String getLifeGrowl() {
		return lifeGrowl;
	}
	public int getFailures() {
		return failures;
	}
	public void setFailures(int failures) {
		this.failures = failures;
	}
	public void addFail(){
		failures++;
	}
	public Map<String, List<Date>> getUserAccess() {
		return userAccess;
	}
	public void setUserAccess(Map<String, List<Date>> userAccess) {
		this.userAccess = userAccess;
	}
	public void resetUserAccess(){
		userAccess = new HashMap<String,List<Date>>();
	}
	public Date getServerLaunched() {
		return cfg.getTimeUp();
	}
	public void setServerLaunched(Date serverLaunched) {
		cfg.setTimeUp( serverLaunched );
	}
	public String getOptionPteEntrega() {
		return optionPteEntrega;
	}
	public String getOptionReReceived() {
		return optionReReceived;
	}
	public String getOptionAllFiles() {
		return optionAllFiles;
	}
	public String getOptionReOpen() {
		return optionReOpen;
	}
	///////////////////////////////////////////////////////////////
	//                Fin de los Getters y Setters               //
	///////////////////////////////////////////////////////////////

	
}