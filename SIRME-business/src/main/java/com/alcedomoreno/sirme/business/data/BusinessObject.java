package com.alcedomoreno.sirme.business.data;

import com.alcedomoreno.sirme.business.transform.Transformator;


public interface BusinessObject {
	
	//TODO @JsonIgnore
	public Class<? extends Transformator> getTransformator();
}