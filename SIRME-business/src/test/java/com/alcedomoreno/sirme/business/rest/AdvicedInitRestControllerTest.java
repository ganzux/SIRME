package com.alcedomoreno.sirme.business.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.alcedomoreno.sirme.business.data.Address;
import com.alcedomoreno.sirme.business.data.Team;
import com.alcedomoreno.sirme.business.dto.AdviceDTO;
import com.alcedomoreno.sirme.business.dto.CodeDTO;
import com.alcedomoreno.sirme.business.services.AdviceService;
import com.alcedomoreno.sirme.business.services.CustomerService;
import com.alcedomoreno.sirme.business.services.TeamService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;

@RunWith(MockitoJUnitRunner.class)
public class AdvicedInitRestControllerTest extends TestCase {

	@InjectMocks
	private AdvicedInitRestController adviceController;
	
	@Resource(name = ServiceConstants.ADVICE_SERVICE)
	@Mock
	protected AdviceService adviceService;
	
	@Resource(name = ServiceConstants.TEAM_SERVICE)
	@Mock
	protected TeamService teamService;
	
	@Resource(name = ServiceConstants.CUSTOMER_SERVICE)
	@Mock
	protected CustomerService customerService;

	@Test
	public void testOKAdvice(){
		try {
			when(teamService.get(any(String.class), any(String.class))).thenReturn(getMockTeam());
			when(customerService.getAddressById(any(Integer.class))).thenReturn(getMockAddress());

			AdviceDTO advice = new AdviceDTO();
			advice.setAlertId("01");
			advice.setAddress("address");
			advice.setPassword("password");
			advice.setIdClient("288");
			
			CodeDTO dto = adviceController.openWork(advice);
			assertEquals(dto.getCode(), CodeDTO.OK);

		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	public void testKOAdviceByAddress(){
		try {
			when(teamService.get(any(String.class), any(String.class))).thenReturn(getMockTeam());
			AdviceDTO advice = new AdviceDTO();
			advice.setIdClient("288");
			CodeDTO dto = adviceController.openWork(advice);
			assertEquals(dto.getCode(), CodeDTO.KO);

		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	public void testKOAdviceByTeam(){
		try {
			AdviceDTO advice = new AdviceDTO();
			advice.setIdClient("288");
			CodeDTO dto = adviceController.openWork(advice);
			assertEquals(dto.getCode(), CodeDTO.KO);
		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	public void testKOAdviceByException(){
		when(teamService.get(any(String.class), any(String.class))).thenThrow(Exception.class);
		CodeDTO dto = adviceController.openWork(new AdviceDTO());
		//assertEquals(e.getClass(), Exception.class);
		assertEquals(dto.getCode(), CodeDTO.KO);
    }
	
	private Team getMockTeam(){
		Team team = new Team();

		team.setIdTeam(1);
		team.setEnabled(true);
		team.setNameTeam("nameTeam");

		return team;
	}
	
	private Address getMockAddress(){
		Address address = new Address();
		
		return address;
	}
}
