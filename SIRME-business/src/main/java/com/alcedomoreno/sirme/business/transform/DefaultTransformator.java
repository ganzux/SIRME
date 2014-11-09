package com.alcedomoreno.sirme.business.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.core.data.DataObject;


public abstract class DefaultTransformator implements Transformator {
	
	@Override
	public abstract BusinessObject toBusinessObject(DataObject dataObject);

	@Override
	public abstract DataObject toDataObject(BusinessObject businessObject);

	@Override
	public Collection<? extends BusinessObject> toBusinessObject(Collection<? extends DataObject> dataObjects) {
		Collection<BusinessObject> businessObjects = new ArrayList<BusinessObject>();
		BusinessObject business = null;
		for (DataObject data : dataObjects) {
			business = (BusinessObject) this.toBusinessObject(data);
			
			businessObjects.add(business);
		}
		
		return businessObjects;
	}
	
	@Override
	public Collection<? extends DataObject> toDataObject(Collection<? extends BusinessObject> businessObjects) {
		Collection<DataObject> dataObjects = new ArrayList<DataObject>();
		DataObject data = null;
		for (BusinessObject business : businessObjects) {
			data = (DataObject) this.toDataObject(business);
			
			dataObjects.add(data);
		}
		
		return dataObjects;
	}
	
	@Override
	public Set<? extends DataObject> toDataObjectSet(Collection<? extends BusinessObject> businessObjects) {
		Set<DataObject> dataObjects = new HashSet<DataObject>();
		DataObject data = null;
		for (BusinessObject business : businessObjects) {
			data = (DataObject) this.toDataObject(business);
			
			dataObjects.add(data);
		}
		
		return dataObjects;
	}
	
}
