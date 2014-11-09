package com.alcedomoreno.sirme.business.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.Question;
import com.alcedomoreno.sirme.business.data.QuestionGroup;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.QuestionData;
import com.alcedomoreno.sirme.core.data.QuestionGroupData;

public class QuestionGroupTransform extends DefaultTransformator implements Transformator{
	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
		QuestionGroup business = new QuestionGroup();
		QuestionGroupData data = (QuestionGroupData) dataObject;

		business.setIdQuestionGroup( data.getIdQuestionGroup() );
		business.setNameQuestionGroup( data.getNameQuestionGroup() );
		business.setTimes( data.getTimes() );
		business.setQuestions( (Collection<Question>) TransformFactory.getTransformator(Question.class).toBusinessObject( new ArrayList(data.getQuestions()) ) );

		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
		QuestionGroup business = (QuestionGroup) businessObject;
		QuestionGroupData data = new QuestionGroupData();

		data.setIdQuestionGroup( business.getIdQuestionGroup() );
		data.setNameQuestionGroup( business.getNameQuestionGroup() );
		data.setTimes( business.getTimes() );
		
		if(business.getQuestions() != null) {
			data.setQuestions( new HashSet( (Collection<QuestionData>) TransformFactory.getTransformator(Question.class).toDataObject( new HashSet(business.getQuestions()) ) ) );
		}

		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
