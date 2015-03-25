package com.alcedomoreno.sirme.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.core.AppTestConfig;
import com.alcedomoreno.sirme.core.data.RoleData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=AppTestConfig.class)
public class AbstractDaoTest {

	@Autowired
	RolesDao rolDao;

	 
	@Test
	@Transactional
	public void findOneTest() {

		try {
			rolDao.findOne(1);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		try {
			rolDao.findOne(null);
		} catch (Exception e) {
			assertTrue(e.getClass().equals(IllegalArgumentException.class));
		}
	}

	@Test
	@Transactional
	public void findAllTest() {

		try {
			rolDao.findAll();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void createTest() {

		try {
			RoleData rol = new RoleData();
			rol.setIdRole(234);
			rol.setDescriptionRole("descripcion234");
			rol.setCodeRole("rolPrueba");
			
			rolDao.create(rol);
			
			assertTrue(rolDao.findOne(234).getCodeRole().equals("rolPrueba"));
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		try {
			rolDao.create(null);
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	@Test
	@Transactional
	public void updateTest() {

		try {
			RoleData rol = new RoleData();
			rol.setIdRole(234);
			rol.setDescriptionRole("descripcion234");
			rol.setCodeRole("rolPrueba");
			
			rolDao.create(rol);
			
			assertTrue(rolDao.findOne(234).getCodeRole().equals("rolPrueba"));
			
			rol.setCodeRole("rolPrueba2");
			
			rolDao.update(rol);
			
			RoleData role2 = rolDao.findOne(234);
			
			assertTrue(role2.getCodeRole().equals("rolPrueba2"));
			 
		} catch (Exception e) {
			fail(e.getMessage());
		}
		try {
			
			rolDao.update(null);
			
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	@Test
	@Transactional
	public void deleteTest() {
		
		try {
			RoleData rol = new RoleData();
			rol.setIdRole(234);
			rol.setCodeRole("code");
			
			rolDao.delete(rol);
			
			assertNull(rolDao.findOne(234));
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void deleteByIdTest() {
		
		try {
			rolDao.deleteById(1); 
			
			assertNull(rolDao.findOne(1));
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		try {
			rolDao.deleteById(0); 
			fail("Impossible to arrive here!");
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	@Transactional
	public void evictTest() {
		
		try {
			RoleData rol = new RoleData();
			rol.setIdRole(234);
			rol.setDescriptionRole("descripcion234");
			rol.setCodeRole("rolPrueba");
			
			rolDao.create(rol);
			
			assertTrue(rolDao.findOne(234).getCodeRole().equals("rolPrueba"));
			
			rol.setCodeRole("CHANGED!!!!!");
			
			rolDao.evict(rol);

			rol = rolDao.findOne(234);
			
			assertNull(rol);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void countTest() {
		
		try {
			
			long count = rolDao.count();
			
			assertEquals(count, 4);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void findAllPaginatedTest() {
		
		try {
			List<RoleData> roles = rolDao.findAll(0, 1);
			assertEquals(roles.size(), 1);
			
			roles = rolDao.findAll(0, 4);
			assertEquals(roles.size(), 4);
			
			roles = rolDao.findAll(3, 4);
			assertEquals(roles.size(), 1);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
