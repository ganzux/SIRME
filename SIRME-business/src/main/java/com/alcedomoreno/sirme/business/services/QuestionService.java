package com.alcedomoreno.sirme.business.services;

import java.util.Collection;

import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.data.Work;

public interface QuestionService {

	public Collection<Report> getAll();
	public Collection<Report> getAllWithQuestions();
	public Work getWork( int idWork );
	public Work getLastWorkByAddress( int idAddress );
	public void update( Work work );
}
