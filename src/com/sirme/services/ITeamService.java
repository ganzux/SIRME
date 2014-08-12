package com.sirme.services;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.bussiness.Team;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;

@Transactional(readOnly=true)
public interface ITeamService {

	public Collection<Team> getAll();
	
	public Team get( String teamName,String passWord );

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void save(Team team) throws ValidationException, TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void delete(Team team) throws TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void update(Team team) throws ValidationException, TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void updatePassword(int idTeam, String pass1,String pass2) throws ValidationException,TransactionException;

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void updateMD5Password(int idTeam, String hashedPass,String hashedPass2) throws ValidationException,TransactionException;
}
