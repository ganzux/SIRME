package com.alcedomoreno.sirme.business.data;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alcedomoreno.sirme.business.data.Month.MonthE;
import com.alcedomoreno.sirme.business.transform.CustomerTransform;
import com.alcedomoreno.sirme.business.transform.Transformator;

public class Customer implements BusinessObject,Cloneable{

	private int idCustomer;
	private String cifCustomer;
	private String nameCustomer;	// Razon Social
	private String codeCustomer;	// NIF Cliente

	private String mainAddress;
	private String mainProv;
	private String mainPobl;
	private String mainPostalCode;
	private String mainMail;
	private String mainPhone;
	private String observations;
	private Boolean active;
	
	private Date dateCreated;
	
	// Los siguientes datos son de la direccion donde se realiza el servicio
	private String serviceLocation;
	private String serviceAddress;
	private String serviceProv;
	private String servicePobl;
	private String servicePostalCode;
	
	
	private TypeCustomer typeCustomer;
	private Collection<Contact> contacts;
	private Collection<Address> address;
	private Collection<Work> works;

	
	private Manager manager;
	private User commercial;

	public int getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	public String getNameCustomer() {
		return nameCustomer;
	}
	public void setNameCustomer(String nameCustomer) {
		this.nameCustomer = nameCustomer;
	}
	public String getCifCustomer() {
		return cifCustomer;
	}
	public void setCifCustomer(String cifCustomer) {
		this.cifCustomer = cifCustomer;
	}
	public String getCodeCustomer() {
		return codeCustomer;
	}
	public void setCodeCustomer(String codeCustomer) {
		this.codeCustomer = codeCustomer;
	}
	public String getServiceLocation() {
		return serviceLocation;
	}
	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}
	public String getServiceAddress() {
		return serviceAddress;
	}
	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}
	public String getServiceProv() {
		return serviceProv;
	}
	public void setServiceProv(String serviceProv) {
		this.serviceProv = serviceProv;
	}
	public String getServicePobl() {
		return servicePobl;
	}
	public void setServicePobl(String servicePobl) {
		this.servicePobl = servicePobl;
	}
	public String getServicePostalCode() {
		return servicePostalCode;
	}
	public void setServicePostalCode(String servicePostalCode) {
		this.servicePostalCode = servicePostalCode;
	}
	public String getMainAddress() {
		return mainAddress;
	}
	public void setMainAddress(String mainAddress) {
		this.mainAddress = mainAddress;
	}
	public String getMainMail() {
		return mainMail;
	}
	public void setMainMail(String mainMail) {
		this.mainMail = mainMail;
	}
	public String getMainPhone() {
		return mainPhone;
	}
	public void setMainPhone(String mainPhone) {
		this.mainPhone = mainPhone;
	}
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	public User getCommercial() {
		return commercial;
	}
	public void setCommercial(User commercial) {
		this.commercial = commercial;
	}
	public Collection<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(Collection<Contact> contacts) {
		this.contacts = contacts;
	}
	public String getMainPobl() {
		return mainPobl;
	}
	public void setMainPobl(String mainPobl) {
		this.mainPobl = mainPobl;
	}
	public String getMainProv() {
		return mainProv;
	}
	public TypeCustomer getTypeCustomer() {
		return typeCustomer;
	}
	public void setTypeCustomer(TypeCustomer typeCustomer) {
		this.typeCustomer = typeCustomer;
	}
	public void setMainProv(String mainProv) {
		this.mainProv = mainProv;
	}
	public String getMainPostalCode() {
		return mainPostalCode;
	}
	public void setMainPostalCode(String mainPostalCode) {
		this.mainPostalCode = mainPostalCode;
	}
	public Collection<Address> getAddress() {
		return address;
	}
	public void setAddress(Collection<Address> address) {
		this.address = address;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Collection<Work> getWorks() {
		return works;
	}
	public void setWorks(Collection<Work> works) {
		this.works = works;
	}
	
	public Customer(){}
	
	public Customer(String cif){
		this.cifCustomer = cif;
	}

	public String getServicesAddress(){
		StringBuilder sb = new StringBuilder();
		
		if ( address!=null && !address.isEmpty() )
			for ( Address a:address )
			sb.append( a.getMainAddress() ).append("\r\n");
		
		return sb.toString();
	}
	
	public String getServicesAddressMin(){
		int l = 25;
		String str = getServicesAddress();
		return str.substring(0, l<str.length()?l:str.length() );
	}
	
	public String getMonthsStr(){
		StringBuilder sb = new StringBuilder();

		Map<MonthE,MonthE> months = new HashMap<MonthE,MonthE>();

		if ( address != null )
			for ( Address a : address )
				if ( a.getMonths() != null )
					for ( MonthE m : a.getMonths() )
						months.put(m, m);

		if ( months.containsKey(MonthE.ENERO) )
			sb.append("Ene,");
		if ( months.containsKey(MonthE.FEBRERO) )
			sb.append("Feb,");
		if ( months.containsKey(MonthE.MARZO) )
			sb.append("Mar,");
		if ( months.containsKey(MonthE.ABRIL) )
			sb.append("Abr,");
		if ( months.containsKey(MonthE.MAYO) )
			sb.append("May,");
		if ( months.containsKey(MonthE.JUNIO) )
			sb.append("Jun,");
		if ( months.containsKey(MonthE.JULIO) )
			sb.append("Jul,");
		if ( months.containsKey(MonthE.AGOSTO) )
			sb.append("Ago,");
		if ( months.containsKey(MonthE.SEPTIEMBRE) )
			sb.append("Sep,");
		if ( months.containsKey(MonthE.OCTUBRE) )
			sb.append("Oct,");
		if ( months.containsKey(MonthE.NOVIEMBRE) )
			sb.append("Nov,");
		if ( months.containsKey(MonthE.DICIEMBRE) )
			sb.append("Dic,");
		
		return (sb.length()==0?"":sb.toString().substring( 0,sb.length()-1) );
	}
	
	public Double getAmount() {
		Double d = 0d;

		if ( address != null )
			for ( Address a : address )
				try{
					d += a.getAmountNumber();
				} catch ( Exception e ){}

		return d;
	}
	
	@Override
	public String toString(){
		return idCustomer + "," + nameCustomer + "," + cifCustomer + "," + codeCustomer;
	}

	@Override
	public Class<? extends Transformator> getTransformator() {
		return CustomerTransform.class;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}