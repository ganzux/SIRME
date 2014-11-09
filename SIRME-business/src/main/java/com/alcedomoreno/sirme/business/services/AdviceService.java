package com.alcedomoreno.sirme.business.services;

import com.alcedomoreno.sirme.business.data.Advice;
import com.alcedomoreno.sirme.business.data.FirextFile;

public interface AdviceService {

	public void insert(String key, Advice value) throws Exception;
	public Advice get(String key) throws Exception;
	public boolean exists(String key) throws Exception;
	public void addImage(String key, FirextFile file) throws Exception;
	public void addSign(String key, FirextFile file) throws Exception;
	public void close(String key, Advice value) throws Exception;
	public int getPictureSize(String key) throws Exception;

}
