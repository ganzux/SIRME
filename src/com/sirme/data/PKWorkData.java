package com.sirme.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

@Embeddable
public class PKWorkData implements Serializable,IDataObject {

	private static final long serialVersionUID = 1L;

	@Column(name="idWork", nullable=false)	
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idWork;
	
	@Column(name="[year]", nullable=false)	
	private int year;
	
	public PKWorkData(){
		
	}
	
	public PKWorkData(int idWork, int year) {
		super();
		this.idWork = idWork;
		this.year = year;
	}
	public int getIdWork() {
		return idWork;
	}
	public void setIdWork(int idWork) {
		this.idWork = idWork;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
}
