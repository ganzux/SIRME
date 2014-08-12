package com.sirme.util;

import java.text.MessageFormat;

public class TransactionException extends Exception {

	private static final long serialVersionUID = 1L;
	private String data;

	public TransactionException(String message, String data,  Object... arguments) {
		super(MessageFormat.format(message, arguments));
		this.data = String.valueOf( data );
	}
	
	public TransactionException(String message, Object... arguments) {
		super(MessageFormat.format(message, arguments));
	}

	public TransactionException(Exception e) {
		super(e);
	}
	
	public TransactionException(String error) {
		super(error);
	}

	public TransactionException(Throwable e, String message, Object[] objects) {
		super(MessageFormat.format(message, objects),e);
	}

	public String getData() {
		return data;
	}
}