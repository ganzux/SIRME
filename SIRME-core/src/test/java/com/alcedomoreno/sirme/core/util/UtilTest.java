package com.alcedomoreno.sirme.core.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testWorkStatus(){
		try {
			Work work = new Work ();
			
			work.setStatus(Work.STATUS_ABIERTO);
			assertEquals(work.getStatusCode(), Work.STATUS_ABIERTO);
			assertEquals(work.getStatusState(), "11");
			assertEquals(work.getStatusString(), "Abierto");
	
			work.setStatus(Work.STATUS_DESCARGADO);
			assertEquals(work.getStatusCode(), Work.STATUS_DESCARGADO);
			assertEquals(work.getStatusState(), "21");
			assertEquals(work.getStatusString(), "Descargado");
			
			work.setStatus(Work.STATUS_RECIBIDO);
			assertEquals(work.getStatusCode(), Work.STATUS_RECIBIDO);
			assertEquals(work.getStatusState(), "31");
			assertEquals(work.getStatusString(), "Recibido");
			
			work.setStatus(Work.STATUS_CERRADO);
			assertEquals(work.getStatusCode(), Work.STATUS_CERRADO);
			assertEquals(work.getStatusState(), "0");
			assertEquals(work.getStatusString(), "Cerrado");
	
			work.setStatus(Work.STATUS_PTE_ENTREGA );
			assertEquals(work.getStatusCode(), Work.STATUS_PTE_ENTREGA );
			assertEquals(work.getStatusState(), "41");
			assertEquals(work.getStatusString(), "Pendiente de Entrega");
		} catch (Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testWorkStatusFailed(){
		try {
			Work work = new Work ();
			work.setStatus("controlledfail!");

			assertEquals(work.getStatusCode(), 0);
			
		} catch (Exception e){
			fail(e.getMessage());
		}
		
		try {
			Work work = new Work ();
			work.setStatus(Work.STATUS_RECIBIDO + "0");
			
			assertNull(work.getStatusString());
			assertTrue(work.getStatusState().isEmpty());
			
		} catch (Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testConstants(){
		try {
			// TODO abstract class??
			DAOConstants daoC = new DAOConstants();
			assertEquals(daoC.CUSTOMER_DAO, DAOConstants.CUSTOMER_DAO);
			assertEquals(daoC.TEAM_DAO, DAOConstants.TEAM_DAO);
			assertEquals(daoC.WORK_DAO, DAOConstants.WORK_DAO);
			assertEquals(daoC.QUESTION_DAO, DAOConstants.QUESTION_DAO);
			assertEquals(daoC.OTHER_DAO, DAOConstants.OTHER_DAO);
			assertEquals(daoC.USER_DAO, DAOConstants.USER_DAO);
			assertEquals(daoC.PERMISSION_DAO, DAOConstants.PERMISSION_DAO);
			assertEquals(daoC.ROLE_DAO, DAOConstants.ROLE_DAO);
			assertEquals(daoC.CUSTOMER_DAO, DAOConstants.CUSTOMER_DAO);
			assertEquals(daoC.PROFILE_DAO, DAOConstants.PROFILE_DAO);
			assertEquals(daoC.APPLICATION_DAO, DAOConstants.APPLICATION_DAO);
			
		} catch (Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testTypeWork(){
		try {
			TypeWork typeWork = new TypeWork ();

			typeWork.setIdTypeWork(288);
			assertEquals(typeWork.getIdTypeWork(), 288);
			
			typeWork = new TypeWork (TypeWork.getIdTypeWork(TypeWork.TYPE_PARTE));
			assertEquals(typeWork.getNameTypeWork(), TypeWork.TYPE_PARTE);
			
			typeWork = new TypeWork (TypeWork.getIdTypeWork(TypeWork.TYPE_AVISO));
			assertEquals(typeWork.getNameTypeWork(), TypeWork.TYPE_AVISO);
		} catch (Exception e){
			fail(e.getMessage());
		}

	}
}
