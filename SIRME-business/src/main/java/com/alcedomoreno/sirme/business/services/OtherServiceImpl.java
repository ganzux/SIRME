package com.alcedomoreno.sirme.business.services;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.business.util.TransactionException;
import com.alcedomoreno.sirme.core.dao.OtherDao;
import com.alcedomoreno.sirme.core.util.DAOConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Service( ServiceConstants.OTHER_SERVICE)
public class OtherServiceImpl implements OtherService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( OtherServiceImpl.class );
	private static final String CLASS_NAME = "OtherServiceImpl";
	
	@Resource( name=DAOConstants.OTHER_DAO )
	protected OtherDao otherDao;

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
	//                      M�todos P�blicos                     //
	///////////////////////////////////////////////////////////////

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
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
	//                 Fin de los M�todos P�blicos               //
	///////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////
	//                      M�todos Privados                     //
	///////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////
	//                 Fin de los M�todos Privados               //
	///////////////////////////////////////////////////////////////
}
