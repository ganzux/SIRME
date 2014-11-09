package com.alcedomoreno.sirme.business.transform;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Question;
import com.alcedomoreno.sirme.business.data.Reply;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.QuestionData;
import com.alcedomoreno.sirme.core.data.ReplyData;

public class ReplyTransform extends DefaultTransformator implements Transformator{
	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
		Reply business = new Reply();
		ReplyData data = (ReplyData) dataObject;

		business.setIdReply( data.getIdReply() );
		business.setReply( data.getReply() );
		business.setQuestion( (Question) TransformFactory.getTransformator(Question.class).toBusinessObject( data.getQuestion() )  );

		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
		Reply business = (Reply) businessObject;
		ReplyData data = new ReplyData();

		data.setIdReply( business.getIdReply() );
		data.setReply( business.getReply() );
		data.setQuestion( (QuestionData) TransformFactory.getTransformator(Question.class).toDataObject( business.getQuestion() )  );

		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
