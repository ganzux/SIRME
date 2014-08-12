package com.sirme.services.rest.dto;

public class CodeDTO {
	
	public static final String OK = "OK";
	public static final String KO = "ERROR";
	

	private String code;
	private String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public CodeDTO(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	
}
