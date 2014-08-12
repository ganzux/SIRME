package com.sirme.services;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.bussiness.Advice;
import com.sirme.bussiness.FirextFile;

@Transactional(readOnly=true)
public interface IAdviceService {

	//@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void insertAdvice(String key, Advice value) throws Exception;
	
	public Advice getAdvice(String key) throws Exception;
	
	public boolean exists(String key) throws Exception;
	
	public void addImage(String key, FirextFile file) throws Exception;
	
	public void addSign(String key, FirextFile file) throws Exception;
	
	public void closeAdvice(String key, Advice value) throws Exception;
	
	public int getPictureSize(String key) throws Exception;

}
