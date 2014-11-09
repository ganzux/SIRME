package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;

import com.alcedomoreno.sirme.core.data.ReportData;
import com.alcedomoreno.sirme.core.data.WorkData;

public interface QuestionsDao {
	
	public Collection<ReportData> getAll();
	
	public WorkData getWork( int idWork );
	
	public WorkData getWorkByAddress( int idAddress );
	
	public void update( WorkData wd );
	
	public int deleteReplyGroupsFromWork( int idWork );
	
	public int deleteRepliesFromWork( int idWork );
}