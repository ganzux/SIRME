package com.alcedomoreno.sirme.business.transform;

import java.util.Collection;
import java.util.Set;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.core.data.DataObject;

public interface Transformator {
	
	public BusinessObject toBusinessObject(DataObject dataObject);
	
	public void copy(BusinessObject source, BusinessObject target);
	
	public Collection<? extends BusinessObject> toBusinessObject(Collection<? extends DataObject> dataObjects);
	
	public DataObject toDataObject(BusinessObject businessObject);
	
	public Collection<? extends DataObject> toDataObject(Collection<? extends BusinessObject> businessObjects);
	
	public Set<? extends DataObject> toDataObjectSet(Collection<? extends BusinessObject> businessObjects);
}
