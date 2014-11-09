package com.alcedomoreno.sirme.business.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.business.data.Advice;
import com.alcedomoreno.sirme.business.data.FirextFile;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Transactional(readOnly=true)
@Service( ServiceConstants.ADVICE_SERVICE)
public class AdviceServiceImpl implements AdviceService{

	///////////////////////////////////////////////////////////////
	//                         Atributos                         //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( AdviceServiceImpl.class );
	private static final String CLASS_NAME = "AdviceServiceImpl";
	
	private static Map<String,Advice> advices;

	///////////////////////////////////////////////////////////////
	//                    Fin de los Atributos                   //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                       Constructores                       //
	///////////////////////////////////////////////////////////////
	
	public AdviceServiceImpl(){
		MyLogger.info(log, CLASS_NAME, "OtherServiceImpl", "New Instance");
		advices = new ConcurrentHashMap<String,Advice>();
	}
	
	///////////////////////////////////////////////////////////////
	//                  Fin de los Constructores                 //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                      M�todos P�blicos                     //
	///////////////////////////////////////////////////////////////

	@Override
	public void insert(String key, Advice value) throws Exception {
		MyLogger.info(log, CLASS_NAME, "insertAdvice", "IN", key, value);
		advices.put(key, value);
		MyLogger.info(log, CLASS_NAME, "insertAdvice", "OUT", key, value);
	}

	@Override
	public void addImage(String key, FirextFile file) throws Exception {
		MyLogger.info(log, CLASS_NAME, "addImage", "IN", key, file.getName());
		
		if ( !advices.containsKey( key ) )
			throw new Exception("No existe ese Aviso");

		Advice adv = advices.get( key );
		Collection<FirextFile> files = adv.getPictures();
		if ( files == null )
			files = new ArrayList<FirextFile>();
		files.add( file );
		adv.setPictures( files );
		advices.put(key, adv);

		MyLogger.info(log, CLASS_NAME, "addImage", "OUT", key, file.getName());
	}

	@Override
	public void addSign(String key, FirextFile file) throws Exception {
		MyLogger.info(log, CLASS_NAME, "addSign", "IN", key, file.getName());
		
		if ( !advices.containsKey( key ) )
			throw new Exception("No existe ese Aviso");

		Advice adv = advices.get( key );

		if ( adv.getSign() != null )
			throw new Exception("Hay m�s de una firma");

		adv.setSign( file );
		advices.put(key, adv);

		MyLogger.info(log, CLASS_NAME, "addSign", "OUT", key, file.getName());
	}

	@Override
	public void close(String key, Advice value) throws Exception {
		MyLogger.info(log, CLASS_NAME, "closeAdvice", "IN", key, value);
		if ( !advices.containsKey( key ) )
			throw new Exception("No existe ese Aviso");
		advices.remove( key );
		MyLogger.info(log, CLASS_NAME, "closeAdvice", "OUT", key, value);
	}
	@Override
	public int getPictureSize(String key) throws Exception{
		if ( !advices.containsKey( key ) )
			throw new Exception("No existe ese Aviso");
		return advices.get( key ).getPictures()==null?0:advices.get( key ).getPictures().size();
	}

	@Override
	public boolean exists( String key ) throws Exception {
		if ( advices == null )
			return false;
		if ( advices.containsKey( key ) )
			return true;
		return false;
	}

	@Override
	public Advice get(String key) throws Exception{
		return advices.get( key );
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
