package com.alcedomoreno.sirme.business.services;

import java.util.Collection;

import com.alcedomoreno.sirme.business.data.User;
import com.alcedomoreno.sirme.business.util.TransactionException;
import com.alcedomoreno.sirme.business.util.ValidationException;


public interface UsersService {

	public org.springframework.security.core.userdetails.User get(String codeUser);
	public User getPlain(String codeUser);
	public User getByCodeUserLoadAllData(String codeUser);
	public Collection<User> getAll();
	public Collection<User> getAll( String type );
	public void save(User user) throws ValidationException, TransactionException;
	public void update(User user) throws ValidationException, TransactionException;
	public void delete(int idUser) throws TransactionException;
	public void updatePassword(int idUser, String pass1,String pass2) throws ValidationException,TransactionException;
	public void updateDateAccess(String codeUser) throws ValidationException,TransactionException;
}
