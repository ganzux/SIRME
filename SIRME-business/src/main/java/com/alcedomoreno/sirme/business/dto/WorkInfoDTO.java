package com.alcedomoreno.sirme.business.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class WorkInfoDTO {

	@JsonProperty
	private int workId;
	@JsonProperty
	private long date;
	@JsonProperty
	private int clientId;
	@JsonProperty
	private String clientName;
	@JsonProperty
	private String albaran;
	@JsonProperty
	private String address;
	@JsonProperty
	private String prov;
	@JsonProperty
	private String pobl;
	@JsonProperty
	private String postalCode;
	@JsonProperty
	private String telf;
	@JsonProperty
	private String notes;

	public int getWorkId() {
		return workId;
	}

	public void setWorkId(int workId) {
		this.workId = workId;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getAlbaran() {
		return albaran;
	}

	public void setAlbaran(String albaran) {
		this.albaran = albaran;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelf() {
		return telf;
	}

	public void setTelf(String telf) {
		this.telf = telf;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getPobl() {
		return pobl;
	}

	public void setPobl(String pobl) {
		this.pobl = pobl;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
