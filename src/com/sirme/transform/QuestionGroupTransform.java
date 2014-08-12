package com.sirme.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.sirme.bussiness.IBusinessObject;
import com.sirme.bussiness.Question;
import com.sirme.bussiness.QuestionGroup;
import com.sirme.data.IDataObject;
import com.sirme.data.QuestionData;
import com.sirme.data.QuestionGroupData;

public class QuestionGroupTransform extends DefaultTransformator implements ITransformator{
	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		QuestionGroup business = new QuestionGroup();
		QuestionGroupData data = (QuestionGroupData) dataObject;

		business.setIdQuestionGroup( data.getIdQuestionGroup() );
		business.setNameQuestionGroup( data.getNameQuestionGroup() );
		business.setTimes( data.getTimes() );
		business.setQuestions( (Collection<Question>) TransformFactory.getTransformator(Question.class).toBusinessObject( new ArrayList(data.getQuestions()) ) );

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
		QuestionGroup business = (QuestionGroup) businessObject;
		QuestionGroupData data = new QuestionGroupData();

		data.setIdQuestionGroup( business.getIdQuestionGroup() );
		data.setNameQuestionGroup( business.getNameQuestionGroup() );
		data.setTimes( business.getTimes() );
		data.setQuestions( new HashSet( (Collection<QuestionData>) TransformFactory.getTransformator(Question.class).toDataObject( new HashSet(business.getQuestions()) ) ) );

		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
