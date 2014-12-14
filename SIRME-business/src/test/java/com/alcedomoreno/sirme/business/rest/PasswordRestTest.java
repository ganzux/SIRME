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
import com.alcedomoreno.sirme.business.dto.ChangePassWordInDTO;
import com.alcedomoreno.sirme.business.dto.ChangePassWordOutDTO;
import com.alcedomoreno.sirme.business.dto.CodeDTO;
import com.alcedomoreno.sirme.business.dto.LoginInDTO;
import com.alcedomoreno.sirme.business.dto.LogonDTO;
import com.alcedomoreno.sirme.business.services.ConfigService;
import com.alcedomoreno.sirme.business.services.TeamService;

@RunWith(MockitoJUnitRunner.class)
public class PasswordRestTest extends TestCase {
	
	@InjectMocks
	private ChangePasswordRestController changePasswordController;
	
	@Mock
	TeamService teamService;

	@Mock
	ConfigService cfg;

	@Test
	public void testLogin(){
		try {
			String nameTeam = "A Team";
			String pass = "dummyPass";
			String newPass = "newDummyPass";

			when(cfg.getTimeUp()).thenReturn(new Date());
			when(teamService.get(nameTeam, pass)).thenReturn(getDummyTeam(nameTeam, pass));

			ChangePassWordInDTO in = new ChangePassWordInDTO();
			in.setNewPassword(newPass);
			in.setPassword(pass);
			in.setTeam(nameTeam);

			ChangePassWordOutDTO out = changePasswordController.changePassword(in);

			assertEquals(out.getError(), null);
			assertEquals(out.getOperation(), CodeDTO.OK);
			assertEquals(out.getTeam(), nameTeam);
			
			
			in.setPassword(newPass + "other Str");

			out = changePasswordController.changePassword(in);

			assertNotNull(out.getError());
			assertEquals(out.getOperation(), CodeDTO.KO);
			assertEquals(out.getTeam(), nameTeam);
			
			in.setPassword(newPass);
			in.setTeam("other Str");

			out = changePasswordController.changePassword(in);

			assertNotNull(out.getError());
			assertEquals(out.getOperation(), CodeDTO.KO);
			assertEquals(out.getTeam(), "other Str");


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
