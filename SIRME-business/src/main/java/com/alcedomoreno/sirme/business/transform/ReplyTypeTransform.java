package com.alcedomoreno.sirme.business.transform;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.ReplyType;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.ReplyTypeData;

public class ReplyTypeTransform extends DefaultTransformator implements Transformator{
	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
		ReplyType business = new ReplyType();
		ReplyTypeData data = (ReplyTypeData) dataObject;

		business.setIdReplyType( data.getIdReplyType() );
		business.setNameReplyType( data.getNameReplyType() );

		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
		ReplyType business = (ReplyType) businessObject;
		ReplyTypeData data = new ReplyTypeData();

		data.setIdReplyType( business.getIdReplyType() );
		data.setNameReplyType( business.getNameReplyType() );
		
		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
	}
}
