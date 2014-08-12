package com.sirme.dao;

import java.util.Collection;

import com.sirme.data.TeamData;

public interface ITeamsDao {
	
	public Collection<TeamData> getAll();
	
	public TeamData get( String teamName,String passWord );

	public void save(TeamData cd);
	
	public void update(TeamData cd);
	
	public void delete(TeamData cd);
	
	public void updatePass(int idUser,String pass);
}
