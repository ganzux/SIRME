package com.sirme.dao;

import java.util.Collection;
import java.util.Date;

import com.sirme.data.ReplyData;
import com.sirme.data.ReportData;
import com.sirme.data.WorkData;

public interface IWorkDao {
	
	public Collection<WorkData> getAll();
	
	public Collection<ReportData> getAllReportsType();
	
	public WorkData get(int idWork);
	
	public Collection<WorkData> getFromAddress(int idAddress);
	
	public Collection<WorkData> getOpenAdvicesOrWorksFromTeam(int idTeam, Date date, boolean work);
	
	public Collection<ReplyData> getRepliesFromWork(int idWork);
	
	public int getMaxAlbaranByYear(int year);
	
	public void save(WorkData cd);
	
	public void update(WorkData cd);
	
	public void updateStatus(int idWoirk, int newStatus);
	
	public void updateSign(int idWoirk, String pathSign, String signer);
	
	public void delete(WorkData cd);
}
