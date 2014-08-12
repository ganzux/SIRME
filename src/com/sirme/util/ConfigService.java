package com.sirme.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sirme.basicsecurity.SpringSecurityConstants;

@Service
public class ConfigService  {

	private static Logger log = LoggerFactory.getLogger( ConfigService.class );
	private final String CLASS_NAME = "ConfigService";
	
	private Date timeUp;

	public ConfigService() {
		MyLogger.info( log , CLASS_NAME, "ConfigService", "New Instance");
		timeUp = new Date();
	}
	
	private String cronExpression;
	private String cronExpression2;
	private String dynUser;
	private String dynPath;
	private String dynPass;
	private String dynEnabled;
	private String exportCustomers;
	private String photoDirectory;
	private String logDirectory;
	private String allowFiles;
	private String allowZIP;

	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getDynUser() {
		return dynUser;
	}
	public void setDynUser(String dynUser) {
		this.dynUser = dynUser;
	}
	public String getDynPath() {
		return dynPath;
	}
	public void setDynPath(String dynPath) {
		this.dynPath = dynPath;
	}
	public String getDynPass() {
		return dynPass;
	}
	public void setDynPass(String dynPass) {
		this.dynPass = dynPass;
	}
	public String getDynEnabled() {
		return dynEnabled;
	}
	public void setDynEnabled(String dynEnabled) {
		this.dynEnabled = dynEnabled;
	}
	public boolean isDNSEnabled(){
		return "true".equalsIgnoreCase( dynEnabled );
	}
	public String getExportCustomers() {
		return exportCustomers;
	}
	public void setExportCustomers(String exportCustomers) {
		this.exportCustomers = exportCustomers;
	}
	public boolean isXportCustomers(){
		return "true".equalsIgnoreCase( exportCustomers );
	}
	public String getAllowFiles() {
		return allowFiles;
	}
	public void setAllowFiles(String allowFiles) {
		this.allowFiles = allowFiles;
	}
	public boolean isAllowedFiles(){
		return "true".equalsIgnoreCase( allowFiles );
	}
	public boolean isAllowedZIP(){
		return "true".equalsIgnoreCase( allowZIP );
	}
	public String getAllowZIP() {
		return allowZIP;
	}
	public void setAllowZIP(String allowZIP) {
		this.allowZIP = allowZIP;
	}
	public String getCronExpression2() {
		return cronExpression2;
	}
	public void setCronExpression2(String cronExpression2) {
		this.cronExpression2 = cronExpression2;
	}
	@PostConstruct
	public void prepareLoadApp() throws Exception{
		testSpringConstants();
		photoDirExistx();
	}
	

	


	public Date getTimeUp() {
		return timeUp;
	}
	public void setTimeUp(Date timeUp) {
		this.timeUp = timeUp;
	}
	public String getPhotoDirectory() {
		return photoDirectory;
	}
	public void setPhotoDirectory(String photoDirectory) {
		this.photoDirectory = photoDirectory;
	}
	public String getLogDirectory() {
		return logDirectory;
	}
	public void setLogDirectory(String logDirectory) {
		this.logDirectory = logDirectory;
	}
	@Deprecated
	private void testSpringConstants() throws Exception{
		
		MyLogger.info( log , CLASS_NAME, "testSpringConstants", "INIT", "Comprobando que las Variables de SPRING no estén repetidas" );

		List<Class> springConfigClasses = new ArrayList<Class>();
		springConfigClasses.add( SpringConstants.class );
		springConfigClasses.add( SpringSecurityConstants.class );

		try{
			Map<String,String> names = new HashMap<String,String>();

			for ( @SuppressWarnings("rawtypes") Class clase: springConfigClasses ){
				MyLogger.info( log , CLASS_NAME, "testSpringConstants", "INIT", "Comprobando la clase" ,clase.getCanonicalName());
				Field[] fileds = clase.getDeclaredFields();

				if ( fileds!=null )
					for ( Field field:fileds ){
						String variableName = 	field.getName();
						String variableValue = 	(String) field.get( String.class );
						
						MyLogger.info( log , CLASS_NAME, "testSpringConstants", variableName, variableValue );
						
						if ( names.get(variableValue)==null )
							names.put(variableValue, variableName);
						else{
							String logStr = "Se detiene la carga de la aplicación. Ya existe una configuración para esa variable. '";
							logStr += variableValue + "' repetido en '" + variableName + "' y '" + names.get(variableValue) +"'.";
							throw new Exception( logStr );
						}
							
						
					}
			}

		} catch (Exception e){
			MyLogger.error( log , CLASS_NAME, "testSpringConstants", "Se detiene la carga de la Aplicación", e.getMessage() );
			throw new Exception( e );
		}
		
		MyLogger.info( log , CLASS_NAME, "testSpringConstants", "END" );
	}
	
	private boolean photoDirExistx() throws Exception{
		boolean exitst = false;
		MyLogger.info( log , CLASS_NAME, "photoDirExistx", "Comprobando si existe directorio de imágenes", photoDirectory );
		try{
			File f = new File( photoDirectory );
			if ( !f.exists() )
				throw new Exception( photoDirectory + " No existe" );
			else if ( !f.isDirectory() )
				throw new Exception( photoDirectory + " No es un directorio/carpeta" );
			else if ( !f.canWrite() )
				throw new Exception( photoDirectory + " No se tiene permiso de escritura" );
			else 
				exitst = true;
		} catch ( Exception e ){
			MyLogger.error( log , CLASS_NAME, "photoDirExistx", "Se detiene la carga de la Aplicación", e.getMessage() );
			throw e;
		}
		MyLogger.info( log , CLASS_NAME, "photoDirExistx", photoDirectory, exitst );
		return exitst;
	}

}


