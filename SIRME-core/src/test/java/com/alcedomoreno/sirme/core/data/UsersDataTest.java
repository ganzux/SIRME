package com.alcedomoreno.sirme.core.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

public class UsersDataTest {

	@Test
	public void testConstructor(){
		try {
			UserData data0 = new UserData();
			assertNotNull(data0);

			ProfileData data1 = new ProfileData();
			assertNotNull(data1);

			RoleData data2 = new RoleData();
			assertNotNull(data2);

			PermissionData data3 = new PermissionData();
			assertNotNull(data3);
			
			TeamData data4 = new TeamData();
			assertNotNull(data4);
			data4 = new TeamData(5);
			assertNotNull(data4);
		} catch (Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetSetUser(){
		try {
			UserData data = new UserData();

			data.setAddedDateUser(new Date(1));
			assertEquals(data.getAddedDateUser(), new Date(1));

			data.setCodeUser("codeUser");
			assertEquals(data.getCodeUser(), "codeUser");

			assertNotNull(data.getCustomers());
			Set<CustomerData> customers = new LinkedHashSet<CustomerData>();
			data.setCustomers(customers);
			assertEquals(data.getCustomers(), customers);

			data.setEmailUser("emailUser");
			assertEquals(data.getEmailUser(), "emailUser");

			assertEquals(data.isEnabled(), false);
			data.setEnabled(true);
			assertEquals(data.isEnabled(), true);

			assertNull(data.getExpirationDateUser());
			data.setExpirationDateUser(new Date(9));
			assertEquals(data.getExpirationDateUser(), new Date(9));

			assertNull(data.getExpirationDateUserPassword());
			data.setExpirationDateUserPassword(new Date(8));
			assertEquals(data.getExpirationDateUserPassword(), new Date(8));

			data.setFirstSurnameUser("firstSurnameUser");
			assertEquals(data.getFirstSurnameUser(), "firstSurnameUser");

			data.setIdUser(55);
			assertEquals(data.getIdUser(), 55);
			assertEquals(data.getIdUser(), data.getORMID());

			assertNull(data.getLastAccess());
			data.setLastAccess(new Date(8889));
			assertEquals(data.getLastAccess(), new Date(8889));

			assertEquals(data.isLocked(), false);
			data.setLocked(true);
			assertEquals(data.isLocked(), true);

			data.setNameUser("nameUser");
			assertEquals(data.getNameUser(), "nameUser");

			assertEquals(data.isOutOpen(), false);
			data.setOutOpen(true);
			assertEquals(data.isOutOpen(), true);

			data.setPasswordUser("passwordUser");
			assertEquals(data.getPasswordUser(), "passwordUser");

			data.setPhoneNumberUser("phoneNumberUser");
			assertEquals(data.getPhoneNumberUser(), "phoneNumberUser");

			assertNotNull(data.getProfiles());
			Set<ProfileData> profiles = new LinkedHashSet<ProfileData>();
			data.setProfiles(profiles);
			assertEquals(data.getProfiles(), profiles);

			assertNotNull(data.getRoles());
			Set<RoleData> roles = new LinkedHashSet<RoleData>();
			data.setRoles(roles);
			assertEquals(data.getRoles(), roles);

			data.setSecondSurnameUser("secondSurnameUser");
			assertEquals(data.getSecondSurnameUser(), "secondSurnameUser");

		} catch (Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetSetRole(){
		try {
			RoleData data = new RoleData();

			data.setCodeRole("codeRole");
			assertEquals(data.getCodeRole(), "codeRole");
			assertEquals(data.getCodeRole(), data.getAuthority());

			data.setDescriptionRole("descriptionRole");
			assertEquals(data.getDescriptionRole(), "descriptionRole");

			data.setIdRole(55);
			assertEquals(data.getIdRole(), 55);
			assertEquals(data.getIdRole(), data.getORMID());

			data.setURLSuccessLogin("uRLSuccessLogin");
			assertEquals(data.getURLSuccessLogin(), "uRLSuccessLogin");

			assertNotNull(data.getPermissions());
			Set<PermissionData> permissions = new LinkedHashSet<PermissionData>();
			data.setPermissions(permissions);
			assertEquals(data.getPermissions(), permissions);


		} catch (Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetSetProfile(){
		try {
			ProfileData data = new ProfileData();

			data.setCodeProfile("code");
			assertEquals(data.getCodeProfile(), "code");
			assertEquals(data.getCodeProfile(), data.getAuthority());

			data.setDescriptionProfile("description");
			assertEquals(data.getDescriptionProfile(), "description");

			data.setIdProfile(288);
			assertEquals(data.getIdProfile(), 288);
			assertEquals(data.getIdProfile(), data.getORMID());
			assertEquals(data.getIdProfile(), data.hashCode());
			assertEquals(String.valueOf(data.getIdProfile()), data.toString());

		} catch (Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetSetTeam(){
		try {
			TeamData data = new TeamData(9);
			
			data.setIdTeam(88);
			assertEquals(data.getIdTeam(), 88);

			assertFalse(data.isCanUploadPhotos());
			data.setCanUploadPhotos(true);
			assertTrue(data.isCanUploadPhotos());

			assertFalse(data.isEnabled());
			data.setEnabled(true);
			assertTrue(data.isEnabled());

			data.setNameTeam("nameTeam");
			assertEquals(data.getNameTeam(), "nameTeam");

			data.setPhoneNumber("phoneNumber");
			assertEquals(data.getPhoneNumber(), "phoneNumber");

		} catch (Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testUserEquals(){
		try {
			UserData user = new UserData();

			assertEquals(true, user.equals(user));

			assertEquals(false, user.equals(new ApplicationData()));

			assertEquals(true, user.equals(new UserData()));

			user.setIdUser(5);
			assertEquals(false, user.equals(new UserData()));
		} catch (Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testRoleEquals(){
		try {
			RoleData role = new RoleData();

			assertEquals(true, role.equals(role));

			assertEquals(false, role.equals(new ApplicationData()));

			assertEquals(true, role.equals(new RoleData()));

			role.setIdRole(5);
			assertEquals(false, role.equals(new RoleData()));
		} catch (Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testProfileEquals(){

		try {
			ProfileData profile = new ProfileData();

			assertEquals(true, profile.equals(profile));

			assertEquals(false, profile.equals(new ApplicationData()));

			assertEquals(true, profile.equals(new ProfileData()));

			profile.setIdProfile(88);
			assertEquals(false, profile.equals(new ProfileData()));
		} catch (Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testPermissionEquals(){
		try {

			PermissionData permission = new PermissionData();

			assertEquals(true, permission.equals(permission));

			assertEquals(false, permission.equals(new ApplicationData()));

			assertEquals(true, permission.equals(new PermissionData()));

			permission.setIdPermission(8);
			assertEquals(false, permission.equals(new PermissionData()));
		} catch (Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testDummyBean(){
		try {
			DummyData data = new DummyData();
			assertNotNull(data);

			DummyData data1 = new DummyData(8);
			assertNotNull(data1);
			assertEquals(data1.getIdDummy(), 8);

			data1.setIdDummy(7);
			assertEquals(data1.getIdDummy(), 7);

			data1.setText("text");
			assertEquals(data1.getText(), "text");
		} catch (Exception e){
			fail(e.getMessage());
		}
	}
}
