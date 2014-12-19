package com.alcedomoreno.sirme.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.core.AppTestConfig;
import com.alcedomoreno.sirme.core.data.TeamData;
import com.alcedomoreno.sirme.core.data.UserData;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=AppTestConfig.class)
public class UsersAndTeamsDaoTest {

	@Autowired
	UsersDao usersDao;
	
	@Autowired
	TeamsDao teamsDao;

	 
	@Test
	@Transactional
	public void findAllUsersTest() {

		try {
			Collection<UserData> users = usersDao.getAll();
			assertEquals(users.size(), 3);
			
			for (UserData user : users){
				assertEquals(user.getCodeUser(), "user" + user.getIdUser());
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void getByCodeTest() {

		try {
			UserData user = usersDao.getByCode("user1");
			assertEquals(user.getEmailUser(), "mail1");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void getByCodeUserLoadAllDataTest() {

		try {
			UserData user = usersDao.getByCodeUserLoadAllData("user1");
			assertEquals(user.getSecondSurnameUser(), "2nd Sur 1");
			assertNotNull(user.getRoles());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void saveAndUpdateDataTest() {

		UserData user = usersDao.getByCodeUserLoadAllData("user1");
		
		try {
			user.setCodeUser("otherCode");
			usersDao.save(user);
			
			UserData user2 = usersDao.getByCodeUserLoadAllData("otherCode");
			
			assertEquals(user2.getIdUser(), user.getIdUser());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		try {
			user.setCodeUser("otherCode");
			usersDao.update(user);
			
			UserData user2 = usersDao.getByCodeUserLoadAllData("otherCode");
			
			assertEquals(user2.getIdUser(), user.getIdUser());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		try {
			user.setIdUser(0);
			usersDao.save(user);
		} catch (Exception e) {
			assertEquals(e.getCause().getMessage(), "could not insert: [com.alcedomoreno.sirme.core.data.UserData]");
		}
	}
	
	@Test
	@Transactional
	public void deleteDataTest() {
		UserData user = usersDao.getByCodeUserLoadAllData("user3");
		
		try {
			usersDao.delete(user.getIdUser());
			
			user = usersDao.getByCodeUserLoadAllData("user3");
			
			assertNull(user);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void updatePassDataTest() {

		UserData user = usersDao.getByCodeUserLoadAllData("user1");
		
		try {
			user.setPasswordUser("newPass");
			usersDao.updatePass(user.getIdUser(), "newPass");
			
			UserData user2 = usersDao.getByCodeUserLoadAllData("user1");

			assertEquals(user2.getIdUser(), user.getIdUser());
			assertEquals(user2.getPasswordUser(), "newPass");
			assertEquals(user2.getAddedDateUser(), user.getAddedDateUser());
			assertEquals(user2.getFirstSurnameUser(), user.getFirstSurnameUser());
			assertEquals(user2.getPhoneNumberUser(), user.getPhoneNumberUser());
			
			
			
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void updateLastDate() {

		UserData user = usersDao.getByCodeUserLoadAllData("user1");
		
		try {
			assertNull(user.getLastAccess());
			
			usersDao.updateLastDate(user.getCodeUser());

			user = usersDao.getByCodeUserLoadAllData("user1");

			assertNotNull(user.getLastAccess());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}


	@Test
	@Transactional
	public void allTeamsTest() {

		try {
			Collection<TeamData> teams = teamsDao.getAll();
			assertEquals(teams.size(), 5);
			
			for (TeamData team : teams){
				assertEquals(team.getNameTeam(), "team" + team.getIdTeam());
				assertEquals(team.getPassWord(), "pass " + team.getIdTeam());
				assertEquals(team.getPhoneNumber(), "" + team.getIdTeam());
				
				if (team.getIdTeam() == 1){
					assertEquals(team.getUsers().size(), 1);
				} else if (team.getIdTeam() == 2){
					assertEquals(team.getUsers().size(), 2);
				}
			}
			
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	@Transactional
	public void oneTeamTest() {

		try {
			TeamData team = teamsDao.get("team1", "bad pass");
			assertNull(team);
			
			team = teamsDao.get("Bad name", "pass 1");
			assertNull(team);
			
			team = teamsDao.get("Bad name", "Bad Pass");
			assertNull(team);
			
			team = teamsDao.get("team1", null);
			assertNull(team);
			
			team = teamsDao.get(null, "pass 1");
			assertNull(team);
			
			team = teamsDao.get(null, null);
			assertNull(team);
			
			team = teamsDao.get("team1", "pass 1");
			assertNotNull(team);
			assertEquals(team.getNameTeam(), "team1");
			assertEquals(team.getPassWord(), "pass 1");
			assertEquals(team.getPhoneNumber(), "1");
			assertEquals(team.getUsers().size(), 1);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void saveAndUpdateTeamTest() {

		TeamData team = teamsDao.get("team1", "pass 1");
		
		try {
			team.setNameTeam("newName");
			teamsDao.save(team);
			
			TeamData team2 = teamsDao.get("newName", "pass 1");
			
			assertEquals(team.getIdTeam(), team2.getIdTeam());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		try {
			UserData user = usersDao.getByCodeUserLoadAllData("user3");
			team.getUsers().add(user);
			teamsDao.update(team);
			
			TeamData team2 = teamsDao.get("newName", "pass 1");
			
			assertEquals(team2.getUsers().size(), 2);
			
			team2.setUsers(new HashSet<UserData>());
			teamsDao.update(team2);

			TeamData team3 = teamsDao.get("newName", "pass 1");
			
			assertEquals(team3.getUsers().size(), 0);
			assertEquals(team3.getIdTeam(), team2.getIdTeam());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void deleteTeamTest() {
		TeamData team = teamsDao.get("team1", "pass 1");
		
		try {
			teamsDao.delete(team);
			team = teamsDao.get("team1", "pass 1");
			assertNull(team);
			
			UserData user = usersDao.getByCodeUserLoadAllData("user1");
			assertNotNull(user);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void updatePassTeamTest() {

		TeamData team = teamsDao.get("team1", "pass 1");
		
		try {
			team.setPassWord("newPass");
			teamsDao.updatePass(team.getIdTeam(), "newPass");
			
			TeamData team2 = teamsDao.get("team1", "newPass");

			assertEquals(team.getIdTeam(), team2.getIdTeam());
			assertEquals(team.getNameTeam(), team2.getNameTeam());
			assertEquals(team.getPhoneNumber(), team2.getPhoneNumber()); 
			assertEquals(team.getUsers(), team2.getUsers());
			
			team = teamsDao.get("team1", "pass 1");
			
			assertNull(team);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
