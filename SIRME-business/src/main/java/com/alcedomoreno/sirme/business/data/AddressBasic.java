package com.alcedomoreno.sirme.business.data;

import com.alcedomoreno.sirme.business.transform.AddressBasicTransform;
import com.alcedomoreno.sirme.business.transform.Transformator;


public class AddressBasic implements BusinessObject, Cloneable {

	@Override
	public Class<? extends Transformator> getTransformator() {
		return AddressBasicTransform.class;
	}

}