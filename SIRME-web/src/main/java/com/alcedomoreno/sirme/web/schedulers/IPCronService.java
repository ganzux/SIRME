package com.alcedomoreno.sirme.web.schedulers;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alcedomoreno.sirme.business.services.ConfigService;
import com.alcedomoreno.sirme.core.util.MyLogger;
import com.alcedomoreno.sirme.web.dyn.DynDNSUpdater;

public class IPCronService {

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( IPCronService.class );
	private static final String CLASS_NAME = "IPCronService";

	private String myPublicIP = null;

	@Resource
	private ConfigService cfg;

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////

	public IPCronService(){
		MyLogger.info(log, CLASS_NAME, "IPCronService", "New Instance");
	}

	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////

	public void resetDynDNS(){
		resetDynDNS( DynDNSUpdater.getInstance().getPublicIP() );
	}

	public void resetDynDNSCron(){
		MyLogger.info(log,CLASS_NAME,"resetDynDNSCron", (new Date()).toString() );

		if ( !cfg.isDNSEnabled() )
 			MyLogger.info(log,CLASS_NAME,"resetDynDNSCron","Actualizacion de DNS desactivada");
		else 
			resetDynDNS( DynDNSUpdater.getInstance().getPublicIP() );

		MyLogger.info(log,CLASS_NAME,"resetDynDNSCron", (new Date()).toString());
	}

	public void resetDynDNS( String nowPublicIP ){
		try{
			String user = cfg.getDynUser();
			String pass = cfg.getDynPass();
			String path = cfg.getDynPath();

			DynDNSUpdater.getInstance().resetDynDNS(nowPublicIP, user, pass, path);
			myPublicIP = nowPublicIP;

		} catch (Exception ex) {
			MyLogger.error(log,CLASS_NAME,"updateIPDynDNS",ex.getMessage());
		}
	}

	public void updateIPDynDNS() {
 		MyLogger.info(log,CLASS_NAME,"updateIPDynDNS","Iniciado cron");
 		
 		if ( !cfg.isDNSEnabled() )
 			MyLogger.info(log,CLASS_NAME,"updateIPDynDNS","Actualizacion de DNS desactivada");
 		else{
			MyLogger.info(log,CLASS_NAME,"updateIPDynDNS","Recuperando IP actual...");
			String nowPublicIP = DynDNSUpdater.getInstance().getPublicIP();
			MyLogger.info(log,CLASS_NAME,"updateIPDynDNS",nowPublicIP);

			// Si las IP no coinciden
			if ( nowPublicIP != null && ( myPublicIP == null || ! myPublicIP.equals( nowPublicIP ) ) ){
				MyLogger.info(log,CLASS_NAME,"updateIPDynDNS","La IP ha cambiado.",myPublicIP);
				resetDynDNS( nowPublicIP );
			}

			// IP no ha cambiado
			else
				MyLogger.info(log,CLASS_NAME,"updateIPDynDNS","La IP no ha cambiado.");
 		}
		
		MyLogger.info(log,CLASS_NAME,"updateIPDynDNS","Finalizado cron");
	}

}
