package com.alcedomoreno.sirme.business.services;

import java.util.Collection;

import com.alcedomoreno.sirme.business.data.Team;
import com.alcedomoreno.sirme.business.util.TransactionException;
import com.alcedomoreno.sirme.business.util.ValidationException;

public interface TeamService {

	public Collection<Team> getAll();
	public Team get(String teamName,String passWord);
	public void save(Team team) throws ValidationException, TransactionException;
	public void delete(Team team) throws TransactionException;
	public void update(Team team) throws ValidationException, TransactionException;
	public void updatePassword(int idTeam, String pass1,String pass2) throws ValidationException,TransactionException;
	public void updateMD5Password(int idTeam, String hashedPass,String hashedPass2) throws ValidationException,TransactionException;
}
