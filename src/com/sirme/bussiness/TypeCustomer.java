package com.sirme.bussiness;

public class TypeCustomer {
	
	public static String TYPE_COMUNIDAD	= "Comunidad de Propietarios";
	public static String TYPE_CLIENTE	= "Cliente";
	public static String TYPE_COMUNIDADS= "Comunidad";

	public TypeCustomer(int idTypeCustomer) {
		super();
		this.idTypeCustomer = idTypeCustomer;
	}
	
	public TypeCustomer() {
	}

	int idTypeCustomer;

	public int getIdTypeCustomer() {
		return idTypeCustomer;
	}

	public void setIdTypeCustomer(int idTypeCustomer) {
		this.idTypeCustomer = idTypeCustomer;
	}

	public String getNameTypeCustomer(){
		if ( idTypeCustomer == 1 )
			return TYPE_COMUNIDAD;
		return TYPE_CLIENTE;
	}
	
	public String getNameShortTypeCustomer(){
		if ( idTypeCustomer == 1 )
			return TYPE_COMUNIDADS;
		return TYPE_CLIENTE;
	}
}
