package com.sirme.basicsecurity.business;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.basicsecurity.business.data.User;
import com.sirme.util.TransactionException;
import com.sirme.util.ValidationException;


public interface IUsersService {

	@Transactional(readOnly=true)
	public org.springframework.security.core.userdetails.User get(String codeUser);
	
	@Transactional(readOnly=true)
	public User getPlain(String codeUser);
	
	@Transactional(readOnly=true)
	public User getByCodeUserLoadAllData(String codeUser);
	
	@Transactional(readOnly=true)
	public Collection<User> getAll();
	
	@Transactional(readOnly=true)
	public Collection<User> getAll( String type );

	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void save(User user) throws ValidationException, TransactionException;
	
	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void update(User user) throws ValidationException, TransactionException;
	
	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void delete(int idUser) throws TransactionException;
	
	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void updatePassword(int idUser, String pass1,String pass2) throws ValidationException,TransactionException;
	
	@Transactional( readOnly=false,rollbackFor=TransactionException.class )
	public void updateDateAccess(String codeUser) throws ValidationException,TransactionException;
}
