package com.sirme.dao;

import java.util.Collection;

import com.sirme.data.ReportData;
import com.sirme.data.WorkData;

public interface IQuestionsDao {
	
	public Collection<ReportData> getAll();
	
	public WorkData getWork( int idWork );
	
	public WorkData getWorkByAddress( int idAddress );
	
	public void update( WorkData wd );
	
	public int deleteReplyGroupsFromWork( int idWork );
	
	public int deleteRepliesFromWork( int idWork );
}