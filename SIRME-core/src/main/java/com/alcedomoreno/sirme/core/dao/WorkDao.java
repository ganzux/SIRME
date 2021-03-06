package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.alcedomoreno.sirme.core.data.ReportData;
import com.alcedomoreno.sirme.core.data.WorkData;

public interface WorkDao {
	
	public Collection<WorkData> getAll();
	
	public Collection<WorkData> getAll(List<Integer> selectedYears);

	public Collection<ReportData> getAllReportsType();
	
	public WorkData get(int idWork);
	
	public Collection<WorkData> getFromAddress(int idAddress);
	
	public Collection<WorkData> getOpenAdvicesOrWorksFromTeam(int idTeam, Date date, boolean work);

	public int getMaxAlbaranByYear(int year);
	
	public int save(WorkData cd);
	
	public void update(WorkData cd);
	
	public void updateStatus(int idWoirk, int newStatus);
	
	public void updateSign(int idWoirk, String pathSign, String signer);
	
	public void delete(WorkData cd);
}
