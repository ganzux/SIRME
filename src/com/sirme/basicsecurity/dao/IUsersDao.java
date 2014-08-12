package com.sirme.basicsecurity.dao;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.sirme.basicsecurity.data.UserData;

@Transactional(readOnly=true)
public interface IUsersDao {
	
	public UserData getByCode(String codeUser);
	
	public UserData getByCodeUserLoadAllData(String codeUser);
	
	public Collection<UserData> getAll();
	
	public void save(UserData user);
	
	public void delete(int idUser);
	
	public void update(UserData user);
	
	public void updatePass(int idUser,String pass);
	
	public void updateLastDate(String codeUser);

}
