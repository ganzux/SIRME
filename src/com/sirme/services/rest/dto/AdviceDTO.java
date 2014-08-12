package com.sirme.services.rest.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import com.sirme.bussiness.Work;

public class AdviceDTO {

	@JsonProperty
	private String team;
	@JsonProperty
	private String password;
	@JsonProperty
	private String idClient;
	@JsonProperty
	private String workText;
	@JsonProperty
	private String alertId;
	@JsonProperty
	private Boolean downloaded;
	@JsonProperty
	private String address;
	@JsonProperty
	private String postalCode;
	@JsonProperty
	private String town;
	@JsonProperty
	private String province;
	@JsonProperty
	private String phone;

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
	public Boolean getDownloaded() {
		return downloaded;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setDownloaded(Boolean downloaded) {
		this.downloaded = downloaded;
	}
	public String getAlertId() {
		return alertId;
	}
	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}
	@Override
	public String toString() {
		return "AdviceDTO [team=" + team + ", password=" + password
				+ ", idClient=" + idClient + ", workText=" + workText
				+ ", alertId=" + alertId + "]";
	}
	
	public AdviceDTO() {
		super();
	}

	public AdviceDTO( Work w ){
		try{
			team = String.valueOf( w.getTeam().getIdTeam() );
		} catch ( Exception e){
		}
		try{
			password = String.valueOf( w.getTeam().getPassWord() );
		} catch ( Exception e){
		}
		try{
			idClient = String.valueOf( w.getAddress().getIdAddress() );
		} catch ( Exception e){
		}
		try{
			workText = String.valueOf( w.getMemo() );
		} catch ( Exception e){
		}
		try{
			alertId = String.valueOf( w.getIdWork() );
		} catch ( Exception e){
		}
		try{
			address = w.getAddress().getMainAddress();
		} catch ( Exception e){
		}
		try{
			postalCode = w.getAddress().getMainPostalCode();
		} catch ( Exception e){
		}
		try{
			town = w.getAddress().getMainPobl();
		} catch ( Exception e){
		}
		try{
			province = w.getAddress().getMainProv();
		} catch ( Exception e){
		}
		try{
			phone = w.getCustomer().getMainPhone();
		} catch ( Exception e){
		}

		downloaded = true;
	}
	
}