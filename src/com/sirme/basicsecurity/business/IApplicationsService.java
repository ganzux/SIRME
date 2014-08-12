package com.sirme.basicsecurity.business;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.basicsecurity.business.data.Application;

@Transactional(readOnly=true)
public interface IApplicationsService {
	
	public Application getRootApplication();
	
	public Application getFullApplicationTree();
}