package com.alcedomoreno.sirme.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.core.AppTestConfig;
import com.alcedomoreno.sirme.core.data.ApplicationData;
import com.alcedomoreno.sirme.core.data.PermissionData;
import com.alcedomoreno.sirme.core.data.ProfileData;
import com.alcedomoreno.sirme.core.data.RoleData;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=AppTestConfig.class)
public class SecurityDaoTest {

	@Autowired
	UsersDao usersDao;
	
	@Autowired
	RolesDao rolesDao;
	
	@Autowired
	ProfilesDao profilesDao;
	
	@Autowired
	PermissionsDao permissionsDao;
	
	@Autowired
	private ApplicationsDao applicationsDao;
	
	@Autowired
	private OtherDao otherDao;

	@Test
	public void testApplications(){
		try {
			ApplicationData root = applicationsDao.getRootApplication();
			assertNotNull(root);
			
			Collection<ApplicationData> clientes = applicationsDao.getChildApplications(0);
			assertTrue(clientes.isEmpty());
		} catch (Exception e){
			e.printStackTrace();
		}
    }
	 
	@Test
	@Transactional
	public void invoke() {

		try {
			ApplicationsDaoImpl impl = new ApplicationsDaoImpl();
			PermissionsDaoImpl impl1 = new PermissionsDaoImpl();
			ProfilesDaoImpl impl2 = new ProfilesDaoImpl();
			RolesDaoImpl impl3 = new RolesDaoImpl();

			assertNotNull(impl);
			assertNotNull(impl1);
			assertNotNull(impl2);
			assertNotNull(impl3);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void getAllPerm() {

		try {
			Collection<PermissionData> permissions = permissionsDao.getAll();
			assertEquals(permissions.size(), 5);
			
			for (PermissionData permission : permissions){
				PermissionData recovered = permissionsDao.get(permission.getCodePermission());
				assertEquals(recovered, permission);
			}
			
			PermissionData none = permissionsDao.get(null);
			assertNull(none);
			
			ApplicationData root = applicationsDao.getRootApplication();

			permissions = permissionsDao.get(root.getIdApplication());
			assertTrue(permissions.isEmpty());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void testProfilesRecovery() {
		try {
			Collection<ProfileData> profiles = profilesDao.getAll();
			assertEquals(profiles.size(), 1);
			
			ProfileData profile = new ProfileData();
			profile.setCodeProfile("NEW CODE");
			profile.setDescriptionProfile("desc");
			profilesDao.save(profile);
			
			ProfileData recovered = profilesDao.get(1);
			assertEquals(recovered.getCodeProfile(), "CODE");
			assertEquals(recovered.getDescriptionProfile(), "DESC");
			
			recovered = profilesDao.get(0);
			assertNull(recovered);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void testProfilesRecoveryByCode() {
		try {
			ProfileData recovered = profilesDao.getByCode("CODE");
			assertEquals(recovered.getIdProfile(), 1);
			assertEquals(recovered.getDescriptionProfile(), "DESC");
			
			recovered = profilesDao.getByCode(null);
			assertNull(recovered);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void testProfilesDelete() {
		try {
			profilesDao.delete(1);
			assertEquals(profilesDao.getAll().size(), 0);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void testGettingRoles() {
		try {
			Collection<RoleData> allRoles = rolesDao.getAll();
			assertEquals(allRoles.size(), 4);

			for (RoleData role : allRoles){
				RoleData recoveredRole = rolesDao.get(role.getIdRole());
				assertEquals(role.getCodeRole(), recoveredRole.getCodeRole());
				
				RoleData recoveredRoleByCode = rolesDao.getByCode(recoveredRole.getCodeRole());
				assertEquals(role.getIdRole(), recoveredRoleByCode.getIdRole());
			}
			RoleData recoveredRole = rolesDao.get(0);
			assertNull(recoveredRole);
			
			recoveredRole = rolesDao.getByCode(null);
			assertNull(recoveredRole);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void testRemoveRoles() {
		try {
			int deleted = rolesDao.delete(1);
			assertEquals(deleted, 1);
		} catch (Exception e) {
			assertTrue(true);
		}
		
		try {
			Collection<RoleData> allRoles = rolesDao.getAll();
			assertEquals(allRoles.size(), 4);
			
			int deleted = rolesDao.delete(5);
			assertEquals(deleted, 0);
			
			allRoles = rolesDao.getAll();
			assertEquals(allRoles.size(), 4);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void testSaveRemoveRoles() {
	
		try {
			RoleData theRole = new RoleData();
			theRole.setCodeRole("CODE10");
			theRole.setDescriptionRole("desc");
			theRole.setIdRole(0);
			theRole.setURLSuccessLogin("url");
			rolesDao.save(theRole);

			Collection<RoleData> allRoles = rolesDao.getAll();
			assertEquals(allRoles.size(), 5);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}


	@Test
	@Transactional
	public void testDirectQuery() {
		try {
			OtherDaoImpl impl = new OtherDaoImpl();
			assertNotNull(impl);
			
			otherDao.launchQuery("DELETE FROM photos");

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
