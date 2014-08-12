package com.sirme.services.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sirme.dao.IOtherDao;
import com.sirme.services.IOtherService;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;
import com.sirme.util.TransactionException;

@Service( SpringConstants.OTHER_SERVICE)
public class OtherServiceImpl implements IOtherService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( OtherServiceImpl.class );
	private static final String CLASS_NAME = "OtherServiceImpl";
	
	@Resource( name=SpringConstants.OTHER_DAO )
	protected IOtherDao otherDao;

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public OtherServiceImpl(){
		MyLogger.info(log, CLASS_NAME, "OtherServiceImpl", "New Instance");
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      Métodos Públicos                     //
	///////////////////////////////////////////////////////////////

	@Override
	public int launchQuery(String query) throws TransactionException{
		MyLogger.info(log, CLASS_NAME, "launchQuery", "IN");
		int i = 0;
		try{
			i = otherDao.launchQuery( query );
		} catch (Exception e){
			throw new TransactionException(e);
		}

		MyLogger.info(log, CLASS_NAME, "launchQuery", "OUT");
		
		return i;
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
}
