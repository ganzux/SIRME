package com.sirme.transform;

import com.sirme.bussiness.IBusinessObject;
import com.sirme.bussiness.Question;
import com.sirme.bussiness.ReplyType;
import com.sirme.data.IDataObject;
import com.sirme.data.QuestionData;
import com.sirme.data.ReplyTypeData;

public class QuestionTransform extends DefaultTransformator implements ITransformator{
	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		Question business = new Question();
		QuestionData data = (QuestionData) dataObject;

		business.setIdQuestion( data.getIdQuestion() );
		business.setQuestion( data.getQuestion() );
		business.setOrder( data.getOrder() );
		business.setReplyType( (ReplyType) TransformFactory.getTransformator(ReplyType.class).toBusinessObject( data.getReplyType() ) );
		business.setTotalize( data.getTotalize() );
		//business.setReport( (Report) TransformFactory.getTransformator(Report.class).toBusinessObject( data.getReport() ) );  BUCLE INFINITO!!!!

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
		Question business = (Question) businessObject;
		QuestionData data = new QuestionData();

		data.setIdQuestion( business.getIdQuestion() );
		data.setQuestion( business.getQuestion() );
		data.setOrder( business.getOrder() );
		data.setTotalize( business.getTotalize() );
		data.setReplyType( (ReplyTypeData) TransformFactory.getTransformator(ReplyType.class).toDataObject( business.getReplyType() ) );
		//data.setReport( (ReportData) TransformFactory.getTransformator(Report.class).toDataObject( business.getReport() ) );   BUCLE INFINITO!!!!

		
		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
