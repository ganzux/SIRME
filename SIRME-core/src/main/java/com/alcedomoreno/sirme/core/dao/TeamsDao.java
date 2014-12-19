package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;

import com.alcedomoreno.sirme.core.data.TeamData;

public interface TeamsDao {
	
	public Collection<TeamData> getAll();
	
	public TeamData get( String teamName,String passWord );

	public void save(TeamData cd);
	
	public void update(TeamData cd);
	
	public void delete(TeamData cd);
	
	public void updatePass(int idTeam,String pass);
}
