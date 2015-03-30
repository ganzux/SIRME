package com.alcedomoreno.sirme.business.data;

import com.alcedomoreno.sirme.business.transform.Transformator;
import com.alcedomoreno.sirme.business.transform.WorkBasicTransform;


public class WorkBasic implements BusinessObject, Cloneable {

	@Override
	public Class<? extends Transformator> getTransformator() {
		return WorkBasicTransform.class;
	}

}