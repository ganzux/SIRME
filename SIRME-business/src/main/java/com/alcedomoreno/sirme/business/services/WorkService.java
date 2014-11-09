package com.alcedomoreno.sirme.business.services;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

import com.alcedomoreno.sirme.business.data.Address;
import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.business.util.TransactionException;
import com.alcedomoreno.sirme.business.util.ValidationException;

public interface WorkService {

	public Collection<Work> getAll();
	public Work get(int idWork);
	public Collection<Work> get( Address address );
	public Collection<Work> getOpenAdvicesOrWorksFromTeam( int idTeam, Date date, boolean work );
	public int getNextAlbaran(int year);
	public Collection<Report> getReportsFormWork( int idWork );
	public Collection<Report> getAllKindReports();
	public int save(Work work) throws ValidationException, TransactionException;
	public void changeStatus(int idWork, int newStatus) throws TransactionException;
	public void delete(Work work) throws TransactionException;
	public void update(Work work) throws ValidationException, TransactionException;
	public void updateRest(Work work) throws ValidationException, TransactionException;
	public void update(Work work, InputStream newSign, String fileName, long size) throws ValidationException, TransactionException;
}
