package com.sirme.bussiness;

import java.util.Collection;

import com.sirme.services.rest.dto.AdviceDTO;
import com.sirme.transform.ITransformator;

public class Advice implements IBusinessObject,Cloneable{

	private String team;
	private String password;
	private String idClient;
	private String workText;
	private String alertId;
	private FirextFile sign;
	private Collection<FirextFile> pictures;
	
	public Advice() {
		
	}
	
	public Advice(String team, String password, String idClient,
			String workText, String alertId) {
		super();
		this.team = team;
		this.password = password;
		this.idClient = idClient;
		this.workText = workText;
		this.alertId = alertId;
	}
	
	public Advice( AdviceDTO dto ){
		super();
		this.team =  dto.getTeam();
		this.password = dto.getPassword();
		this.idClient = dto.getIdClient();
		this.workText = dto.getWorkText();
		this.alertId = dto.getAlertId();
	}



	public String getTeam() {
		return team;
	}



	public void setTeam(String team) {
		this.team = team;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getIdClient() {
		return idClient;
	}



	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}



	public String getWorkText() {
		return workText;
	}



	public void setWorkText(String workText) {
		this.workText = workText;
	}



	public String getAlertId() {
		return alertId;
	}



	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}



	public FirextFile getSign() {
		return sign;
	}



	public void setSign(FirextFile sign) {
		this.sign = sign;
	}



	public Collection<FirextFile> getPictures() {
		return pictures;
	}



	public void setPictures(Collection<FirextFile> pictures) {
		this.pictures = pictures;
	}



	@Override
	public Class<? extends ITransformator> getTransformator() {
		return null;
	}
}
