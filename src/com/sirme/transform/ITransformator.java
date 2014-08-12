package com.sirme.transform;

import java.util.Collection;
import java.util.Set;

import com.sirme.bussiness.IBusinessObject;
import com.sirme.data.IDataObject;

public interface ITransformator {
	
	public IBusinessObject toBusinessObject(IDataObject dataObject);
	
	public void copy(IBusinessObject source, IBusinessObject target);
	
	public Collection<? extends IBusinessObject> toBusinessObject(Collection<? extends IDataObject> dataObjects);
	
	public IDataObject toDataObject(IBusinessObject businessObject);
	
	public Collection<? extends IDataObject> toDataObject(Collection<? extends IBusinessObject> businessObjects);
	
	public Set<? extends IDataObject> toDataObjectSet(Collection<? extends IBusinessObject> businessObjects);
}
