package com.alcedomoreno.sirme.business.transform;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Question;
import com.alcedomoreno.sirme.business.data.ReplyType;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.QuestionData;
import com.alcedomoreno.sirme.core.data.ReplyTypeData;

public class QuestionTransform extends DefaultTransformator implements Transformator{
	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
		Question business = new Question();
		QuestionData data = (QuestionData) dataObject;

		business.setIdQuestion(data.getIdQuestion());
		business.setQuestion(data.getQuestion());
		business.setSearch(data.getSearch());
		business.setOrder(data.getOrder());
		business.setReplyType((ReplyType) TransformFactory.getTransformator(ReplyType.class).toBusinessObject(data.getReplyType()));
		business.setTotalize(data.getTotalize());
		//business.setReport((Report)TransformFactory.getTransformator(Report.class).toBusinessObject(data.getReport()));  BUCLE INFINITO!!!!

		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
		Question business = (Question) businessObject;
		QuestionData data = new QuestionData();

		data.setIdQuestion(business.getIdQuestion());
		data.setSearch(business.getSearch());
		data.setQuestion(business.getQuestion());
		data.setOrder(business.getOrder());
		data.setTotalize(business.getTotalize());
		if (business.getReplyType() != null){
			data.setReplyType((ReplyTypeData) TransformFactory.getTransformator(ReplyType.class).toDataObject(business.getReplyType()));
		}
		//data.setReport((ReportData) TransformFactory.getTransformator(Report.class).toDataObject(business.getReport()));   BUCLE INFINITO!!!!

		
		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
