package com.alcedomoreno.sirme.business.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.alcedomoreno.sirme.business.data.Team;
import com.alcedomoreno.sirme.business.dto.LoginInDTO;
import com.alcedomoreno.sirme.business.dto.LogonDTO;
import com.alcedomoreno.sirme.business.services.ConfigService;
import com.alcedomoreno.sirme.business.services.TeamService;

@RunWith(MockitoJUnitRunner.class)
public class LoginRestTest extends TestCase {
	
	@InjectMocks
	private LoginRestController loginController;
	
	@Mock
	TeamService teamService;

	@Mock
	ConfigService cfg;

	@Test
	public void testLogin(){
		try {
			String nameTeam = "123";
			String pass = "dummyPass";

			when(cfg.getTimeUp()).thenReturn(new Date());
			when(teamService.get(nameTeam, pass)).thenReturn(getDummyTeam(nameTeam, pass));

			LoginInDTO in = new LoginInDTO();
			in.setDeviceId("Terminal Test 1");
			in.setPassword(pass);
			in.setTeam(nameTeam);

			LogonDTO out = loginController.login(in);

			assertEquals(out.getTeamName(), nameTeam);
			assertEquals(out.isSuccesful(), true);

			in.setPassword(pass + "Bad");
			out = loginController.login(in);

			assertEquals(out.isSuccesful(), false);
			assertEquals(out.getTeamName(), nameTeam);

			in.setPassword(pass);
			out = loginController.login(in);

			assertEquals(out.getTeamName(), nameTeam);
			assertEquals(out.isSuccesful(), true);

			in.setTeam(nameTeam + "other Str");
			out = loginController.login(in);
			
			assertEquals(out.isSuccesful(), false);
			assertEquals(out.getTeamName(), nameTeam + "other Str");


		} catch (Exception e){
			fail(e.getMessage());
		}
    }

	
	
	
	private Team getDummyTeam(String nameTeam, String pass){
		Team team = new Team();
		
		team.setCanUploadPhotos(true);
		team.setEnabled(true);
		team.setIdTeam(288);
		team.setNameTeam(nameTeam);
		team.setPassWord(pass);
		team.setPhoneNumber("555-288-069");
		team.setUsers(null);
		
		return team;
	}
}
