package com.sirme.services;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.bussiness.Advice;
import com.sirme.bussiness.FirextFile;

@Transactional(readOnly=true)
public interface IAdviceService {

	public void insert(String key, Advice value) throws Exception;
	
	public Advice get(String key) throws Exception;
	
	public boolean exists(String key) throws Exception;
	
	public void addImage(String key, FirextFile file) throws Exception;
	
	public void addSign(String key, FirextFile file) throws Exception;
	
	public void close(String key, Advice value) throws Exception;
	
	public int getPictureSize(String key) throws Exception;

}
