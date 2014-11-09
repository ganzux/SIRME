package com.alcedomoreno.sirme.business.transform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.QuestionGroup;
import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.QuestionGroupData;
import com.alcedomoreno.sirme.core.data.ReportData;

public class ReportTransform extends DefaultTransformator implements Transformator{
	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {
		
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
	public DataObject toDataObject(BusinessObject businessObject) {
		
		Report business = (Report) businessObject;
		ReportData data = new ReportData();

		data.setIdReport( business.getIdReport() );
		data.setNameReport( business.getNameReport() );
		data.setTitleReport( business.getTitleReport() );
		data.setFileReport( business.getFileReport() );
		if (business.getQuestionGroups() != null) {
			data.setQuestionGroups( new HashSet((Collection<QuestionGroupData>) TransformFactory.getTransformator(QuestionGroup.class).toDataObject( business.getQuestionGroups() )) );
		}

		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
