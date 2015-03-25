package com.alcedomoreno.sirme.core.util;

public class TypeWork {
	
	public static String TYPE_PARTE	= "Parte de Trabajo";
	public static String TYPE_AVISO	= "Aviso";
	
	public static int ID_PARTE	= 1;
	public static int ID_AVISO	= 2;

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
		if (idTypeWork == ID_PARTE){
			return TYPE_PARTE;
		}
		return TYPE_AVISO;
	}
	
	public static int getIdTypeWork(String type){
		if (TYPE_PARTE.equalsIgnoreCase(type)){
			return ID_PARTE;
		}
		return ID_AVISO;
	}
}
