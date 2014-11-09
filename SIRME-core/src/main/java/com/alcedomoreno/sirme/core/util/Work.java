package com.alcedomoreno.sirme.core.util;


public class Work {

	public static final int STATUS_ABIERTO = 1;
	public static final int STATUS_DESCARGADO = 2;
	public static final int STATUS_RECIBIDO = 3;
	public static final int STATUS_CERRADO = 4;
	public static final int STATUS_PTE_ENTREGA = 5;

	private String status;



	public void setStatus(String status) {
		this.status = status;
	}
	public void setStatus(int status) {
		this.status = String.valueOf(status);
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

}