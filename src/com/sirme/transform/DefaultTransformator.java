package com.sirme.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.sirme.bussiness.IBusinessObject;
import com.sirme.data.IDataObject;


public abstract class DefaultTransformator implements ITransformator {
	
	@Override
	public abstract IBusinessObject toBusinessObject(IDataObject dataObject);

	@Override
	public abstract IDataObject toDataObject(IBusinessObject businessObject);

	@Override
	public Collection<? extends IBusinessObject> toBusinessObject(Collection<? extends IDataObject> dataObjects) {
		Collection<IBusinessObject> businessObjects = new ArrayList<IBusinessObject>();
		IBusinessObject business = null;
		for (IDataObject data : dataObjects) {
			business = (IBusinessObject) this.toBusinessObject(data);
			
			businessObjects.add(business);
		}
		
		return businessObjects;
	}
	
	@Override
	public Collection<? extends IDataObject> toDataObject(Collection<? extends IBusinessObject> businessObjects) {
		Collection<IDataObject> dataObjects = new ArrayList<IDataObject>();
		IDataObject data = null;
		for (IBusinessObject business : businessObjects) {
			data = (IDataObject) this.toDataObject(business);
			
			dataObjects.add(data);
		}
		
		return dataObjects;
	}
	
	@Override
	public Set<? extends IDataObject> toDataObjectSet(Collection<? extends IBusinessObject> businessObjects) {
		Set<IDataObject> dataObjects = new HashSet<IDataObject>();
		IDataObject data = null;
		for (IBusinessObject business : businessObjects) {
			data = (IDataObject) this.toDataObject(business);
			
			dataObjects.add(data);
		}
		
		return dataObjects;
	}
	
}
