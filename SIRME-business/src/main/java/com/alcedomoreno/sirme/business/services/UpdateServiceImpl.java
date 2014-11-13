package com.alcedomoreno.sirme.business.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Service( ServiceConstants.UPDATED_SERVICE)
public class UpdateServiceImpl implements UpdatedService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( UpdateServiceImpl.class );
	private static final String CLASS_NAME = "UpdateServiceImpl";
	
	private static Date lastUpdate;;

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////

	public UpdateServiceImpl(){
		MyLogger.info(log, CLASS_NAME, "UpdateServiceImpl", "New Instance");
		lastUpdate = new Date();
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      M�todos P�blicos                     //
	///////////////////////////////////////////////////////////////

	@Override
	public boolean isUpdated(Date date) {
		if ( date == null )
			return false;
		if ( lastUpdate == null )
			return true;
		
		return lastUpdate.before( date );
	}
	
	@Override
	public void refreshDate(){
		lastUpdate = new Date();
	}
	
	///////////////////////////////////////////////////////////////
	//                 Fin de los M�todos P�blicos               //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                      M�todos Privados                     //
	///////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////
	//                 Fin de los M�todos Privados               //
	///////////////////////////////////////////////////////////////
}