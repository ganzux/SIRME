package com.alcedomoreno.sirme.business.services;

import com.alcedomoreno.sirme.business.util.TransactionException;

public interface OtherService {

	public int launchQuery(String query) throws TransactionException;

}
