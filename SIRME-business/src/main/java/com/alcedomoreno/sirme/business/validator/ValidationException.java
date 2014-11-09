package com.alcedomoreno.sirme.business.validator;

public class ValidationException extends Exception{

	private static final long serialVersionUID = 1L;

	public ValidationException(String exception){
		super( exception );
	}
	
}
