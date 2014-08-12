package com.sirme.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sirme.bussiness.IBusinessObject;
import com.sirme.bussiness.QuestionGroup;
import com.sirme.bussiness.Reply;
import com.sirme.bussiness.ReplyGroup;
import com.sirme.bussiness.Report;
import com.sirme.bussiness.Work;
import com.sirme.data.IDataObject;
import com.sirme.data.QuestionGroupData;
import com.sirme.data.ReplyData;
import com.sirme.data.ReplyGroupData;
import com.sirme.data.ReportData;
import com.sirme.data.WorkData;
import com.sirme.transform.comparators.ReplyComparator;

public class ReplyGroupTransform extends DefaultTransformator implements ITransformator{
	@Override
	public IBusinessObject toBusinessObject(IDataObject dataObject) {

		ReplyGroup business = new ReplyGroup();
		ReplyGroupData data = (ReplyGroupData) dataObject;

		business.setIdReplyGroup( data.getIdReplyGroup() );
		business.setNameReplyGroup( data.getNameReplyGroup() );
		business.setReplies( (List<Reply>) TransformFactory.getTransformator(Reply.class).toBusinessObject( new ArrayList(data.getReplies()) ) );

		Collections.sort( business.getReplies(),new ReplyComparator() );
		
		return business;
	}

	@Override
	public IDataObject toDataObject(IBusinessObject businessObject) {
		
		ReplyGroup business = (ReplyGroup) businessObject;
		ReplyGroupData data = new ReplyGroupData();

		data.setIdReplyGroup( business.getIdReplyGroup() );
		data.setNameReplyGroup( business.getNameReplyGroup() );
		data.setReplies( (Set<ReplyData>) new HashSet(TransformFactory.getTransformator(Reply.class).toDataObject( new HashSet(business.getReplies()) )) );
		data.setQuestionGroup( (QuestionGroupData)TransformFactory.getTransformator(QuestionGroup.class).toDataObject( business.getQuestionGroup() ) );
		data.setReport( (ReportData) TransformFactory.getTransformator(Report.class).toDataObject( business.getQuestionGroup().getReport() ) );
		data.setWork( (WorkData) TransformFactory.getTransformator(Work.class).toDataObject( business.getWork() ) );

		return data;
	}

	@Override
	public void copy(IBusinessObject source, IBusinessObject target) {
		// TODO Auto-generated method stub
		
	}
}
