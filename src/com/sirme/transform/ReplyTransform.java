package com.sirme.transform;

import com.sirme.bussiness.IBusinessObject;
import com.sirme.bussiness.Question;
import com.sirme.bussiness.Reply;
import com.sirme.data.IDataObject;
import com.sirme.data.QuestionData;
import com.sirme.data.ReplyData;

public class ReplyTransform extends DefaultTransformator implements ITransformator{
	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		Reply business = new Reply();
		ReplyData data = (ReplyData) dataObject;

		business.setIdReply( data.getIdReply() );
		business.setReply( data.getReply() );
		business.setQuestion( (Question) TransformFactory.getTransformator(Question.class).toBusinessObject( data.getQuestion() )  );

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
		Reply business = (Reply) businessObject;
		ReplyData data = new ReplyData();

		data.setIdReply( business.getIdReply() );
		data.setReply( business.getReply() );
		data.setQuestion( (QuestionData) TransformFactory.getTransformator(Question.class).toDataObject( business.getQuestion() )  );

		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
