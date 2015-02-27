package com.alcedomoreno.sirme.web.jasper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRCsvDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.PdfReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

import com.alcedomoreno.sirme.business.data.QuestionGroup;
import com.alcedomoreno.sirme.business.data.Reply;
import com.alcedomoreno.sirme.business.data.ReplyGroup;
import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.business.transform.comparators.QuestionComparator;
import com.alcedomoreno.sirme.business.transform.comparators.ReplyGroupComparator;



public class GenerateReport {
	
	private static String RECORD_DELIMITER = "\t\t@&&\r\n";
	private static String ENTER = "\r\n";
	private static char FIELD_DELIMITER = ';';

	private static GenerateReport instance;
	public static GenerateReport getInstance(){
		if ( instance == null )
			instance = new GenerateReport();
		return instance;
	}
	private GenerateReport(){
	}

   

   public ByteArrayOutputStream fillPDF( com.alcedomoreno.sirme.business.data.Report report, Work work ) throws Exception{
	  return fillPDF( fillReport(report.getTitleReport(), report.getNameReport(), report, work ) );
   }
 
   public JasperPrint fillReport(String title, String subTitle, Report report, Work work) throws Exception{

	String realTemplateName = report.getFileReport();

	String path = ( (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext() ).getRealPath("/");

	JasperPrint jasperPrint = null;

	String reportName = path  + "WEB-INF/reports/" + realTemplateName;

	Map<String, Object> pars = new HashMap<String, Object>();
	pars.put("CLI_NAM", work.getCustomer().getNameCustomer());
	pars.put("CLI_DAT", work.getDate());
	pars.put("CLI_ALB", work.getAlbaran());
	pars.put("CLI_ADD", work.getAddress().getMainAddress());
	pars.put("CLI_TEC", work.getTeam().getNameTec());
	pars.put("TITULO", title);
	pars.put("PATH", path  + "WEB-INF/reports/");
	
	if ( report.getReplyGroups() != null )
		Collections.sort( report.getReplyGroups(), new ReplyGroupComparator() );

	// Tipos Basicos, con datos en forma de tabla
	if ( report.getIdReport() == Report.REPORT_EXTINTORES 
	  || report.getIdReport() == Report.REPORT_BIES
	  || report.getIdReport() == Report.REPORT_ALUMBRADO_EMERGENCIA
	  || report.getIdReport() == Report.REPORT_HUMOS_Y_CALOR ){
		int size = 0;
		
		QuestionGroup unique = report.getQuestionGroups().iterator().next();
		size = unique.getQuestions().size();
		
		JRCsvDataSource dataSourceTable = getData( report.getReplyGroups(),size );
		pars.put("DS", dataSourceTable );
	}

	// INCENDIOS
	else if ( report.getIdReport() == Report.REPORT_DETECCION_INCENDIOS
		   || report.getIdReport() == Report.REPORT_DETECCION_MONOXIDO ){
		int size = 0;
		Collection<ReplyGroup> replieGroups = new ArrayList<ReplyGroup>();
		
		for ( ReplyGroup  rg : report.getReplyGroups() ){
			
			// CARACTERISTICAS DE LA CENTRAL
			if ( rg.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_5_1_CARACTERISTICAS
			  || rg.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_6_1_CARACTERISTICAS )
				for ( Reply r:rg.getReplies() )
					pars.put("CAR_CEN_" + r.getQuestion().getIdQuestion(), r.getReply() );

			// ELEMENTOS DEL SISTEMA Y DISTRIBUCION
			else if ( rg.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_5_2_SISTEMA
				   || rg.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_6_2_SISTEMA )
				for ( Reply r:rg.getReplies() )
					pars.put("ELE_SIS_" + r.getQuestion().getIdQuestion(), r.getReply() );
			
			// TABLA
			else {
				replieGroups.add( rg );
				if ( size == 0 )
					size = rg.getQuestionGroup().getQuestions().size();
			}
		}
		JRCsvDataSource dataSourceTable = getData( replieGroups,size );
		pars.put("DS", dataSourceTable );
	}
	
	
	// DETECCION Y EXTINCION DE INCENDIOS
	else if ( report.getIdReport() == Report.REPORT_EXTINCON_DE_INCENDIOS ){
		int size1 = 0;
		int size2 = 0;
		Collection<ReplyGroup> replieGroups1 = new ArrayList<ReplyGroup>();
		Collection<ReplyGroup> replieGroups2 = new ArrayList<ReplyGroup>();
		
		for ( ReplyGroup  rg : report.getReplyGroups() ){
			
			// CARACTERISTICAS DE LA CENTRAL
			if ( rg.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_10_1_CARACTERISTICAS )
				for ( Reply r:rg.getReplies() )
					pars.put("CAR_CEN_" + r.getQuestion().getIdQuestion(), r.getReply() );

			// ELEMENTOS DEL SISTEMA Y DISTRIBUCION
			else if ( rg.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_10_2_ELEMENTOS
				   || rg.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_10_4_VALVULAS )
				for ( Reply r:rg.getReplies() )
					pars.put("ELE_SIS_" + r.getQuestion().getIdQuestion(), r.getReply() );
			
			// TABLAS
			else if ( rg.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_10_3_DETECTORES ){
				replieGroups1.add( rg );
				if ( size1 == 0 )
					size1 = rg.getQuestionGroup().getQuestions().size();
			}
			
			else {
				replieGroups2.add( rg );
				if ( size2 == 0 )
					size2 = rg.getQuestionGroup().getQuestions().size();
			}
		}
		JRCsvDataSource dataSourceTable = getData( replieGroups1,size1 );
		pars.put("DS", dataSourceTable );
		JRCsvDataSource dataSourceTable2 = getData( replieGroups2,size2 );
		pars.put("DS2", dataSourceTable2 );
	}
	
	
	// HIDRANTES
	else if ( report.getIdReport() == Report.REPORT_HIDRANTES ){

		Collection<ReplyGroup> repliesHidrantes = new ArrayList<ReplyGroup>();
		Collection<ReplyGroup> repliesCasetas = new ArrayList<ReplyGroup>();
		
		for ( ReplyGroup reply : report.getReplyGroups()){
			if ( reply.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_8_1_HIDRANTES )
				repliesHidrantes.add( reply );
			else if ( reply.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_8_2_CASETAS )
				repliesCasetas.add( reply );
		}
		
		ReplyGroup unique = repliesHidrantes.iterator().next();
		JRCsvDataSource dataSourceTable = getData( repliesHidrantes,unique.getQuestionGroup().getQuestions().size() );
		pars.put("DS", dataSourceTable );
		
		if ( repliesCasetas.isEmpty() ){
			pars.put("HAY_CASETAS", 0 );
		} else {
			ReplyGroup unique2 = repliesCasetas.iterator().next();
			JRCsvDataSource dataSourceTable2 = getData( repliesCasetas,unique2.getQuestionGroup().getQuestions().size() );
			pars.put("DS2", dataSourceTable2 );
			pars.put("HAY_CASETAS", 1 );
		}
		
	}
	
	// COLUMNA SECA
	else if ( report.getIdReport() == Report.REPORT_COLUMNA_SECA ){
		int size = 0;
		
		Collection<ReplyGroup> repliesButGeneral = new ArrayList<ReplyGroup>();
		
		for ( ReplyGroup reply : report.getReplyGroups()){
			if ( reply.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_9_1_GENERAL ){
				for ( Reply r:reply.getReplies() )
					pars.put("REPLY_" + r.getQuestion().getIdQuestion(), r.getReply() );
			} else {
				repliesButGeneral.add( reply );
				if ( size == 0 )
					size = reply.getQuestionGroup().getQuestions().size();
			}
		}

		JRCsvDataSource dataSourceTable = getData( repliesButGeneral,size );
		pars.put("DS", dataSourceTable );
	}

	// CAMPANAS DE COCINA
	else if ( report.getIdReport() == Report.REPORT_CAPANAS_DE_COCINA ){

		Collection<ReplyGroup> repliesButGeneral = new ArrayList<ReplyGroup>();

		for ( ReplyGroup  rg : report.getReplyGroups() ){
			
			// CARACTERISTICAS DE LA CENTRAL
			if ( rg.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_11_1_GENERAL )
				for ( Reply r:rg.getReplies() )
					pars.put("CAR_CEN_" + r.getQuestion().getIdQuestion(), r.getReply() );

			// ELEMENTOS DEL SISTEMA Y DISTRIBUCION
			else if ( rg.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_11_2_PREGUNTAS )
				repliesButGeneral.add( rg );
		}
		
		JRCsvDataSource dataSourceTable = getDataWithQuestion( repliesButGeneral,2, false );
		pars.put("DS", dataSourceTable );
	}
	
	// GRUPO DE PRESION SANITARIO
	else if ( report.getIdReport() == Report.REPORT_GRUPO_DE_PRESION ){

		Collection<ReplyGroup> repliesButGeneral = new ArrayList<ReplyGroup>();

		for ( ReplyGroup  rg : report.getReplyGroups() ){

			if ( rg.getQuestionGroup().getIdQuestionGroup() == QuestionGroup.QUG_12_1_GENERAL )
				repliesButGeneral.add( rg );
		}
		
		JRCsvDataSource dataSourceTable = getDataWithQuestion( repliesButGeneral,2, true );
		pars.put("DS", dataSourceTable );
	}

	JRCsvDataSource dataSourceMain = getData();
	
	if ( report.getIdReport() == Report.REPORT_ROCIADORES ){
		int size = 0;

		QuestionGroup unique = report.getQuestionGroups().iterator().next();
		size = unique.getQuestions().size();

		dataSourceMain = getData( report.getReplyGroups(),size );
	}
	

	if ( report.getIdReport() == Report.REPORT_ALBARAN ){
		pars.put("comments", work.getMemo() );
		pars.put("firma1", work.getSignName() );
		pars.put("firma2", work.getSignpath() );
	}
	
	pars.put("DS_HEAD", getData());
	pars.put("DS_FOOT", getData());

	JasperReport jasperReport;
	
	try{
	    jasperReport = (JasperReport) JRLoader.loadObject( new File( reportName+".jasper" ) );
	    jasperPrint = JasperFillManager.fillReport( jasperReport, pars, dataSourceMain );
	}catch (Throwable e){
		e.printStackTrace();
	}
	
	return jasperPrint;
   }
   
   public ByteArrayOutputStream fillPDF(JasperPrint jasperPrint) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
		try{
			//JRPdfExporter exporter = new  JRPdfExporter();
			//exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			
			StringBuilder javaScript = new StringBuilder("this.zoom=").append( "50" ).append(";")
				// Hide the Preferences menu item
				.append("app.hideMenuItem(\"GeneralPrefs\");")
				// Hide the Email and toolbar items
				.append("app.hideMenuItem(\"AcroSendMail:SendMail\");")
				.append("app.hideToolbarButton(\"AcroSendMail:SendMail\");")
				// Hide the Review Tracker
				.append("app.hideMenuItem(\"Annots:ReviewTracker\");")
				// Hide the Spelling Check menu item
				.append("app.hideMenuItem(\"Spelling:Spelling\");")
				// Hide the digital ID menu item
				.append("app.hideMenuItem(\"ppklite:UserSettings\");")
				.append("app.hideMenuItem(\"DIGSIG:DigitalSignatures\");")
				// Hide the Trusted Identities menu item
				.append("app.hideMenuItem(\"PUBSEC:AddressBook\");")
				// Turn off Auto-Complete
				.append("this.noautocomplete = true;")
				// Turn off Acrobat forms caching 
				.append("this.nocache = true;");

			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);
	
			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
			
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			configuration.setCreatingBatchModeBookmarks(true);
			exporter.setConfiguration(configuration);

			exporter.exportReport();
			
			/*exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, javaScript.toString() );
		    exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM,baos); */
		    exporter.exportReport(); 
		    //JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
		}catch (Throwable e){
			e.printStackTrace();
		}
		
		return baos;
	}

   public void rptCSV(Work work, OutputStream outputStream) {
       Map<String,Object> parameters = new HashMap<String,Object>();
       parameters.put("CLI_NAM", work.getCustomer().getNameCustomer());
       parameters.put("CLI_DAT", work.getDate() );
       parameters.put("CLI_ALB", work.getAlbaran() );
       parameters.put("CLI_ADD", work.getCustomer().getMainAddress() );
       parameters.put("CLI_TEC", work.getTeam().getNameTec() );
       parameters.put("PATH", "C:\\wsfirext\\Firext_local\\WebContent\\WEB-INF\\reports\\" );
       parameters.put("TITULO", "CERTIFICADO DE REVISION DE EXTINTORES" );

	   JasperReport jasperReport;
       JasperPrint jasperPrint;
       try {
         //jasperReport = JasperCompileManager.compileReport( "C:\\wsfirext\\Firext_local\\WebContent\\WEB-INF\\reports\\test.jrxml" );
    	   jasperReport = (JasperReport) JRLoader.loadObject( new File("C:\\wsfirext\\Firext_local\\WebContent\\WEB-INF\\reports\\extintores.jasper") );
         jasperPrint = JasperFillManager.fillReport( jasperReport, parameters, new JREmptyDataSource() );

         if ( outputStream != null )
        	 JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
         else
        	 JasperExportManager.exportReportToPdfFile( jasperPrint, "C:\\wsfirext\\Firext_local\\WebContent\\WEB-INF\\reports\\" + System.currentTimeMillis() + ".pdf" );

         System.out.println("Done!");
       }
       catch (JRException e) {
         e.printStackTrace();
       }
         
   }
   
   private JRCsvDataSource getData() throws UnsupportedEncodingException{
	   StringBuilder sb = new StringBuilder();
   		JRCsvDataSource ds = null;
   		sb.append("TODO, Arreglar").append( FIELD_DELIMITER ).append( RECORD_DELIMITER );
   		InputStream is = new ByteArrayInputStream( sb.toString().getBytes() );
		
		ds = new JRCsvDataSource( is );
    	ds.setRecordDelimiter( RECORD_DELIMITER );
        ds.setUseFirstRowAsHeader( false );
        ds.setFieldDelimiter( FIELD_DELIMITER );
		
		return ds;
   }
	   
   private JRCsvDataSource getData( Collection<ReplyGroup> replieGroups, int size ) throws UnsupportedEncodingException{
		// Cabeceras que encajan con el Report, NO TOCAR
   	StringBuilder sb = new StringBuilder();
   	JRCsvDataSource ds = null;
   	
   	for ( int i=1;i<=size;i++ )
   	   	sb.append("F" + i).append( FIELD_DELIMITER );
   	sb.append( RECORD_DELIMITER );

		if ( replieGroups.size()==0 )
			sb = fillEmptyDataWithZeros(sb);
		else {
			// Contenido
			for (ReplyGroup replieGroup:replieGroups){
				Collections.sort( replieGroup.getReplies(),new QuestionComparator() );
				for ( Reply reply:replieGroup.getReplies() )
					sb.append( (reply.getReply()==null)?"":reply.getReply() ).append(FIELD_DELIMITER);
				sb.append(RECORD_DELIMITER);
			}
		}

		InputStream is = new ByteArrayInputStream( sb.toString().getBytes() );
		
		ds = new JRCsvDataSource( is );
   	ds.setRecordDelimiter( RECORD_DELIMITER );
       ds.setUseFirstRowAsHeader( true );
       ds.setFieldDelimiter( FIELD_DELIMITER );
		
		return ds;
  }
   
   private JRCsvDataSource getDataWithQuestion( Collection<ReplyGroup> replieGroups, int size, boolean special ) throws UnsupportedEncodingException{

	   if ( special )
		   size++;

	   StringBuilder sb = new StringBuilder();
	   JRCsvDataSource ds = null;
   	
   	for ( int i=1;i<=size;i++ )
   	   	sb.append("F" + i).append( FIELD_DELIMITER );
   	sb.append( RECORD_DELIMITER );

		if ( replieGroups.size()==0 )
			sb = fillEmptyDataWithZeros(sb);
		else {
			// Contenido
			for (ReplyGroup replieGroup:replieGroups){
				Collections.sort( replieGroup.getReplies(),new QuestionComparator() );
				for ( Reply reply:replieGroup.getReplies() ){
					sb.append( (reply.getQuestion().getQuestion()==null)?"":reply.getQuestion().getQuestion() ).append(FIELD_DELIMITER);
					sb.append( (reply.getReply()==null)?"":reply.getReply() ).append(FIELD_DELIMITER);
					if ( special ){
						if ( reply.getQuestion().getIdQuestion()==265 ||
								reply.getQuestion().getIdQuestion()==272 ||
								reply.getQuestion().getIdQuestion()==279 ||
								reply.getQuestion().getIdQuestion()==286 ||
								reply.getQuestion().getIdQuestion()==290 ||
								reply.getQuestion().getIdQuestion()==293 ||
								reply.getQuestion().getIdQuestion()==296 ||
								reply.getQuestion().getIdQuestion()==299 ||
								reply.getQuestion().getIdQuestion()==302 || 
								reply.getQuestion().getIdQuestion()==307 ||
								reply.getQuestion().getIdQuestion()==311 )
							sb.append( "1" ).append(FIELD_DELIMITER);
						else
							sb.append( "0" ).append(FIELD_DELIMITER);
							
					}
					sb.append(RECORD_DELIMITER);
				}
			}
		}

		InputStream is = new ByteArrayInputStream( sb.toString().getBytes() );
		
		ds = new JRCsvDataSource( is );
   	ds.setRecordDelimiter( RECORD_DELIMITER );
       ds.setUseFirstRowAsHeader( true );
       ds.setFieldDelimiter( FIELD_DELIMITER );
		
		return ds;
  }

   private static JRCsvDataSource getDataSource() throws JRException, IOException {  
       //JRLoader.getLocationInputStream(  
       JRCsvDataSource ds = new JRCsvDataSource(new File("C:\\wsfirext\\Firext_local\\WebContent\\WEB-INF\\reports\\test.csv"));  
       ds.setRecordDelimiter("\t");  
       ds.setUseFirstRowAsHeader(true);  
       ds.setFieldDelimiter(';');  
       return ds;  
   }
   
   private StringBuilder fillEmptyDataWithZeros(StringBuilder sb){
   	int size = sb.toString().split( String.valueOf(FIELD_DELIMITER) ).length;
		for (int i=1;i<size;i++)
			sb.append("0").append(FIELD_DELIMITER);
		sb.append("0").append(RECORD_DELIMITER);
		return sb;
   }
}
