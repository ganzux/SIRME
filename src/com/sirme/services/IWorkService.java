package com.sirme.services;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.bussiness.Address;
import com.sirme.bussiness.Report;
import com.sirme.bussiness.Work;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;

@Transactional(readOnly=true)
public interface IWorkService {

	public Collection<Work> getAll();
	
	public Work get(int idWork);
	
	public Collection<Work> get( Address address );
	
	public Collection<Work> getOpenAdvicesOrWorksFromTeam( int idTeam, Date date, boolean work );
	
	public int getNextAlbaran(int year);
	
	public Collection<Report> getReportsFormWork( int idWork );
	
	public Collection<Report> getAllKindReports();

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public int save(Work work) throws ValidationException, TransactionException;
	
	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void changeStatus(int idWork, int newStatus) throws TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void delete(Work work) throws TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void update(Work work) throws ValidationException, TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void update(Work work, InputStream newSign, String fileName, long size) throws ValidationException, TransactionException;
}
