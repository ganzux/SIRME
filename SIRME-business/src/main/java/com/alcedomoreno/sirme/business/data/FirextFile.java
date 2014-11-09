package com.alcedomoreno.sirme.business.data;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.alcedomoreno.sirme.business.transform.Transformator;

public class FirextFile implements BusinessObject,Cloneable{

	private String name;
	private String originalFileName;
	private InputStream inputStream;
	
public FirextFile() {
		
	}
	
	public FirextFile( String name, MultipartFile file ){
		super();
		this.name =  name;
		this.originalFileName = file.getOriginalFilename();
		try {
			this.inputStream = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public Class<? extends Transformator> getTransformator() {
		return null;
	}
}
