package com.sirme.export.doc.types.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cabecera {

	private String cliente;
	private String direccion;
	private String fechaRevision;
	private String albaran;
	private String revisadoPor;
	
	@Override
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\t\tCliente: ").append( cliente );
		sb.append(", Direcci�n: ").append( direccion );
		sb.append(", Fecha Revisi�n: ").append( fechaRevision );
		sb.append(", Albar�n: ").append( albaran );
		sb.append(", Revisado Por: ").append( revisadoPor );
		
		return sb.toString();
	}
	
	
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getFechaRevision() {
		return fechaRevision;
	}
	public void setFechaRevision(String fechaRevision) {
		this.fechaRevision = fechaRevision;
	}
	public String getAlbaran() {
		return albaran;
	}
	public void setAlbaran(String albaran) {
		this.albaran = albaran;
	}
	public String getRevisadoPor() {
		return revisadoPor;
	}
	public void setRevisadoPor(String revisadoPor) {
		this.revisadoPor = revisadoPor;
	}
	public Date getDate() throws ParseException{

		SimpleDateFormat formatter = null;

		if ( fechaRevision.contains(".") ){
			formatter = new SimpleDateFormat("d.M.y");
		} else if ( fechaRevision.contains("/") ){
			formatter = new SimpleDateFormat("d/M/y");
		} else if ( fechaRevision.contains("-") ){
			formatter = new SimpleDateFormat("d-M-y");
		}
		
		return formatter.parse( fechaRevision );
	}
	
	
}
