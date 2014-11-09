package com.alcedomoreno.sirme.business.data;

public class TypeWork {
	
	public static String TYPE_PARTE	= "Parte de Trabajo";
	public static String TYPE_AVISO	= "Aviso";

	public TypeWork(int idTypeWork) {
		super();
		this.idTypeWork = idTypeWork;
	}
	
	public TypeWork() {
	}

	int idTypeWork;

	

	public int getIdTypeWork() {
		return idTypeWork;
	}

	public void setIdTypeWork(int idTypeWork) {
		this.idTypeWork = idTypeWork;
	}

	public String getNameTypeWork(){
		if ( idTypeWork == 1 )
			return TYPE_PARTE;
		return TYPE_AVISO;
	}
	
	public static int getIdTypeWork( String type ){
		if ( TYPE_PARTE.equalsIgnoreCase( type ) )
			return 2;
		return 1;
	}
}
