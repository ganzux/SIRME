package com.alcedomoreno.sirme.business.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.alcedomoreno.sirme.business.transform.Transformator;
import com.alcedomoreno.sirme.business.transform.WorkTransform;

public class Work implements BusinessObject, Cloneable {

	public static final int STATUS_ABIERTO = 1;
	public static final int STATUS_DESCARGADO = 2;
	public static final int STATUS_RECIBIDO = 3;
	public static final int STATUS_CERRADO = 4;
	public static final int STATUS_PTE_ENTREGA = 5;

	private int idWork;
	private int year;
	private int albaran;
	private Date date;
	private String memo;
	private String data;
	private String signpath;
	private String signName;
	private Date dateCreated;

	private String status;

	private TypeWork typeWork;
	private Team team;
	private Customer customer;
	private Address address;

	private List<Report> reports;
	private Collection<Photo> photos;

	public Work(){
		super();
	}

	@Override
	public String toString() {
		return idWork + "," + date + "," + team + "," + customer;
	}
	@Override
	public Class<? extends Transformator> getTransformator() {
		return WorkTransform.class;
	}
	public int getIdWork() {
		return idWork;
	}
	public void setIdWork(int idWork) {
		this.idWork = idWork;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getCodeWork() {
		return year + "." + albaran;
	}
	public String getStatus() {
		return status;
	}
	public int getYear() {
		return year;
	}
	public List<Report> getReports() {
		return reports;
	}
	public void setReports(List<Report> reports) {
		this.reports = reports;
	}	
	public void addReport( Report report ){
		if ( reports == null )
			reports = new ArrayList<Report>();
		reports.add( report );
	}
	public int getAlbaran() {
		return albaran;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public void setAlbaran(int albaran) {
		this.albaran = albaran;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setStatus(int status) {
		this.status = String.valueOf(status);
	}
	public TypeWork getTypeWork() {
		return typeWork;
	}
	public void setTypeWork(TypeWork typeWork) {
		this.typeWork = typeWork;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getSignpath() {
		return signpath;
	}
	public void setSignpath(String signpath) {
		this.signpath = signpath;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Collection<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(Collection<Photo> photos) {
		this.photos = photos;
	}
	public String getStatusString() {
		switch (Integer.parseInt(status)) {
			case STATUS_ABIERTO:
				return "Abierto";
			case STATUS_CERRADO:
				return "Cerrado";
			case STATUS_DESCARGADO:
				return "Descargado";
			case STATUS_RECIBIDO:
				return "Recibido";
			case STATUS_PTE_ENTREGA:
				return "Pendiente de Entrega";
		}
		return null;
	}
	public String getStatusState() {
		switch (Integer.parseInt(status)) {
			case STATUS_ABIERTO:
				return "11";
			case STATUS_CERRADO:
				return "0";
			case STATUS_DESCARGADO:
				return "21";
			case STATUS_RECIBIDO:
				return "31";
			case STATUS_PTE_ENTREGA:
				return "41";
		}
		return "";
	}
	public int getStatusCode() {
		try {
			int iStatus = Integer.parseInt(status);
			return iStatus;
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idWork;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Work other = (Work) obj;
		if (idWork != other.idWork)
			return false;
		return true;
	}	
}