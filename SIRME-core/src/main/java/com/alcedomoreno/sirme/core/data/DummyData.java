package com.alcedomoreno.sirme.core.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
@Entity
@Proxy(lazy=false)
@Table(name="dummy")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class DummyData implements Serializable,DataObject {
	
	private static final long serialVersionUID = 1L;
	
	public DummyData() {}
	
	public DummyData(int id) {
		this.idDummy = id;
	}
	
	@Column(name="idDummy", nullable=false)	
	@Id
	@GeneratedValue(generator="VC0A80160139DDDAA5D008132")	
	@GenericGenerator(name="VC0A80160139DDDAA5D008132", strategy="native")	
	private int idDummy;
	
	@Column(name="[text]", nullable=false, unique=true, length=50)	
	private String text;

	public int getIdDummy() {
		return idDummy;
	}

	public void setIdDummy(int idDummy) {
		this.idDummy = idDummy;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
}