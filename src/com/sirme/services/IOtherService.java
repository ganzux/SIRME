package com.sirme.services;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.util.TransactionException;

@Transactional(readOnly=true)
public interface IOtherService {

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public int launchQuery(String query) throws TransactionException;

}
