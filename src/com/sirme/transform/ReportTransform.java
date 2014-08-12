package com.sirme.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.sirme.bussiness.IBusinessObject;
import com.sirme.bussiness.QuestionGroup;
import com.sirme.bussiness.Report;
import com.sirme.data.IDataObject;
import com.sirme.data.QuestionGroupData;
import com.sirme.data.ReportData;

public class ReportTransform extends DefaultTransformator implements ITransformator{
	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {
		
		Report business = new Report();
		ReportData data = (ReportData) dataObject;

		business.setIdReport( data.getIdReport() );
		business.setNameReport( data.getNameReport() );
		business.setTitleReport( data.getTitleReport() );
		business.setFileReport( data.getFileReport() );
		business.setQuestionGroups( (Collection<QuestionGroup>) TransformFactory.getTransformator(QuestionGroup.class).toBusinessObject( new ArrayList(data.getQuestionGroups()) ) );

		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
		Report business = (Report) businessObject;
		ReportData data = new ReportData();

		data.setIdReport( business.getIdReport() );
		data.setNameReport( business.getNameReport() );
		data.setTitleReport( business.getTitleReport() );
		data.setFileReport( business.getFileReport() );
		data.setQuestionGroups( new HashSet((Collection<QuestionGroupData>) TransformFactory.getTransformator(QuestionGroup.class).toDataObject( business.getQuestionGroups() )) );

		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
