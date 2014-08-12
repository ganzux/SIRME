package com.sirme.bussiness;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sirme.transform.ITransformator;


public interface IBusinessObject {
	
	@JsonIgnore
	public Class<? extends ITransformator> getTransformator();
}