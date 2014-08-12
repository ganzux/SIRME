package com.sirme.services;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.bussiness.Report;
import com.sirme.bussiness.Work;

@Transactional(readOnly=true)
public interface IQuestionService {

	public Collection<Report> getAll();
	
	public Collection<Report> getAllWithQuestions();

	public Work getWork( int idWork );
	
	public Work getLastWorkByAddress( int idAddress );

	@Transactional(readOnly=false)
	public void update( Work work );
}
