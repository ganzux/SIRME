package com.alcedomoreno.sirme.business.util;

import java.text.MessageFormat;

public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	private String data;

	public ValidationException(String message, String data,  Object... arguments) {
		super(MessageFormat.format(message, arguments));
		this.data = String.valueOf( data );
	}
	
	public ValidationException(String message, Object... arguments) {
		super(MessageFormat.format(message, arguments));
	}

	public ValidationException(Exception e) {
		super(e);
	}
	
	public ValidationException(String error) {
		super(error);
	}

	public ValidationException(Throwable e, String message, Object[] objects) {
		super(MessageFormat.format(message, objects),e);
	}

	public String getData() {
		return data;
	}
}