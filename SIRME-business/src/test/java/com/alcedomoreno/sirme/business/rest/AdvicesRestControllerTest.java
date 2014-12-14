package com.alcedomoreno.sirme.business.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.business.dto.AdviceDTO;
import com.alcedomoreno.sirme.business.dto.CodeDTO;
import com.alcedomoreno.sirme.business.dto.IntDTO;
import com.alcedomoreno.sirme.business.services.UpdatedService;
import com.alcedomoreno.sirme.business.services.WorkService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.business.util.TransactionException;

@RunWith(MockitoJUnitRunner.class)
public class AdvicesRestControllerTest extends TestCase {

	@InjectMocks
	private AdvicesInitRestController adviceControllerInit;
	
	@InjectMocks
	private AdvicesEndRestController adviceControllerEnd;
	
	@Resource(name = ServiceConstants.WORK_SERVICE)
	@Mock
	protected WorkService workService;
	
	@Resource(name = ServiceConstants.UPDATED_SERVICE)
	@Mock
	protected UpdatedService updateService;

	@Test
	public void testOKAdvices(){
		try {
			int idTeam = 288;
			
			when(workService.getOpenAdvicesOrWorksFromTeam(eq(idTeam), any(Date.class), eq(false))).thenReturn(getMockWorks());

			IntDTO teamId = new IntDTO();
			teamId.setId(idTeam);
			Collection<AdviceDTO> dto = adviceControllerInit.getAdvices(teamId);
			assertEquals(dto.size(), 1);

			teamId.setId(idTeam+1);
			dto = adviceControllerInit.getAdvices(teamId);
			assertEquals(dto.size(), 0);

		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	
	@Test
	public void testOKAdvicesEnd(){
		try {
			int idTeam = 288;
			
			when(workService.getOpenAdvicesOrWorksFromTeam(eq(idTeam), any(Date.class), eq(false))).thenReturn(getMockWorks());

			IntDTO teamId = new IntDTO();
			teamId.setId(idTeam);
			Collection<AdviceDTO> dto = adviceControllerInit.getAdvices(teamId);
			assertEquals(dto.size(), 1);

			for (AdviceDTO advice : dto){
				IntDTO inWork = new IntDTO();
				inWork.setId(Integer.valueOf(advice.getAlertId()));
				CodeDTO outDto = adviceControllerEnd.changeStatus(inWork);
				assertEquals(outDto.getCode(), CodeDTO.OK);
				assertEquals(outDto.getMessage(), advice.getAlertId());
			}
			
			teamId.setId(idTeam+288);
			adviceControllerEnd.changeStatus(teamId);
			
			assertEquals(true, true);
			
		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	
	@Test
	public void testKOAdvices(){
		try {
			int idTeam = 288;
			
			Mockito.doThrow(new TransactionException(new Exception("Bad Thing"))).
					when(workService).changeStatus(any(Integer.class), any(Integer.class));
			IntDTO inWork = new IntDTO();
			inWork.setId(idTeam);
			CodeDTO outDto = adviceControllerEnd.changeStatus(inWork);
			assertEquals(outDto.getCode(), CodeDTO.KO);
			assertTrue(outDto.getMessage().startsWith(String.valueOf(inWork.getId())));

		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	
	
	private Collection<Work> getMockWorks(){
		Collection<Work> works = new ArrayList<Work>();

		Work work = new Work();
		work.setIdWork(1);
		works.add(work);
		
		return works;
	}
}
