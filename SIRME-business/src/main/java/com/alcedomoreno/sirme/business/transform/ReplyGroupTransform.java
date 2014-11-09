package com.alcedomoreno.sirme.business.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alcedomoreno.sirme.business.data.BusinessObject;
import com.alcedomoreno.sirme.business.data.QuestionGroup;
import com.alcedomoreno.sirme.business.data.Reply;
import com.alcedomoreno.sirme.business.data.ReplyGroup;
import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.business.transform.comparators.ReplyComparator;
import com.alcedomoreno.sirme.core.data.DataObject;
import com.alcedomoreno.sirme.core.data.QuestionGroupData;
import com.alcedomoreno.sirme.core.data.ReplyData;
import com.alcedomoreno.sirme.core.data.ReplyGroupData;
import com.alcedomoreno.sirme.core.data.ReportData;
import com.alcedomoreno.sirme.core.data.WorkData;

public class ReplyGroupTransform extends DefaultTransformator implements Transformator{
	@Override
	public BusinessObject toBusinessObject(DataObject dataObject) {

		ReplyGroup business = new ReplyGroup();
		ReplyGroupData data = (ReplyGroupData) dataObject;

		business.setIdReplyGroup( data.getIdReplyGroup() );
		business.setNameReplyGroup( data.getNameReplyGroup() );
		business.setReplies( (List<Reply>) TransformFactory.getTransformator(Reply.class).toBusinessObject( new ArrayList(data.getReplies()) ) );

		Collections.sort( business.getReplies(),new ReplyComparator() );
		
		return business;
	}

	@Override
	public DataObject toDataObject(BusinessObject businessObject) {
		
		ReplyGroup business = (ReplyGroup) businessObject;
		ReplyGroupData data = new ReplyGroupData();

		data.setIdReplyGroup( business.getIdReplyGroup() );
		data.setNameReplyGroup( business.getNameReplyGroup() );
		
		if (business.getReplies() != null){
			data.setReplies( (Set<ReplyData>) new HashSet(TransformFactory.getTransformator(Reply.class).toDataObject( new HashSet(business.getReplies()) )) );
		}

		if (business.getQuestionGroup() != null){
			data.setQuestionGroup( (QuestionGroupData)TransformFactory.getTransformator(QuestionGroup.class).toDataObject( business.getQuestionGroup() ) );
			if(business.getQuestionGroup().getReport() != null) {
				data.setReport( (ReportData) TransformFactory.getTransformator(Report.class).toDataObject( business.getQuestionGroup().getReport() ) );
			}
		}
		
		if (business.getWork() != null){
			data.setWork( (WorkData) TransformFactory.getTransformator(Work.class).toDataObject( business.getWork() ) );
		}

		return data;
	}

	@Override
	public void copy(BusinessObject source, BusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
