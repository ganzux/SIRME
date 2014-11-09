package com.alcedomoreno.sirme.business.services;

import com.alcedomoreno.sirme.business.data.Application;

public interface ApplicationsService {
	
	public Application getRootApplication();
	public Application getFullApplicationTree();
	public boolean existsQuestion(int idQuestion);
	public boolean existsQuestionGriup(int idQuestionGruop);
}