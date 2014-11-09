package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.core.data.UserData;

@Transactional(readOnly=true)
public interface UsersDao {

	@Transactional(readOnly=true)
	public UserData getByCode(String codeUser);

	@Transactional(readOnly=true)
	public UserData getByCodeUserLoadAllData(String codeUser);

	@Transactional(readOnly=true)
	public Collection<UserData> getAll();
	
	@Transactional(readOnly=false)
	public void save(UserData user);
	
	@Transactional(readOnly=false)
	public void delete(int idUser);

	@Transactional(readOnly=false)
	public void update(UserData user);

	@Transactional(readOnly=false)
	public void updatePass(int idUser,String pass);

	@Transactional(readOnly=false)
	public void updateLastDate(String codeUser);

}
