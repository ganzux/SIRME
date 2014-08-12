package com.sirme.transform;

import com.sirme.bussiness.IBusinessObject;
import com.sirme.bussiness.ReplyType;
import com.sirme.data.IDataObject;
import com.sirme.data.ReplyTypeData;

public class ReplyTypeTransform extends DefaultTransformator implements ITransformator{
	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		ReplyType business = new ReplyType();
		ReplyTypeData data = (ReplyTypeData) dataObject;

		business.setIdReplyType( data.getIdReplyType() );
		business.setNameReplyType( data.getNameReplyType() );

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
		ReplyType business = (ReplyType) businessObject;
		ReplyTypeData data = new ReplyTypeData();

		data.setIdReplyType( business.getIdReplyType() );
		data.setNameReplyType( business.getNameReplyType() );
		
		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
	}
}
