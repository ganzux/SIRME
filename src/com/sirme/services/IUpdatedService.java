package com.sirme.services;

import java.util.Date;


public interface IUpdatedService {

	public boolean isUpdated( Date date );
	
	public void refreshDate();

}