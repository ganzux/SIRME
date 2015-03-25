package com.alcedomoreno.sirme.core.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

public class AccessDataTest {

	@Test
	public void testConstructor(){
		try {
			ApplicationData data0 = new ApplicationData();
			assertNotNull(data0);
			
			PermissionData data1 = new PermissionData();
			assertNotNull(data1);
		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	public void testGetSet(){
		try {
			ApplicationData data = new ApplicationData();

			data.setCodeApplication("code");
			assertEquals(data.getCodeApplication(), "code");

			data.setIdApplication(28);
			assertEquals(data.getIdApplication(), 28);
			assertEquals(data.getIdApplication(), data.getORMID());
			assertEquals(String.valueOf(data.getIdApplication()), data.toString());
			
			data.setLevelApplication((byte) 8);
			assertEquals(data.getLevelApplication(), (byte) 8);
			
			data.setNameApplication("name");
			assertEquals(data.getNameApplication(), "name");
			
			// Permissions
			assertNotNull(data.getPermissions());
			Set<PermissionData> permissions = new LinkedHashSet<PermissionData>();
			PermissionData permi0 = new PermissionData();
			
			permi0.setCodePermission("code");
			assertEquals(permi0.getCodePermission(), "code");
			assertEquals(permi0.getCodePermission(), permi0.getAuthority());
			assertEquals(permi0.toString(), permi0.getAuthority());
			permi0.setDescriptionPermission("desc");
			assertEquals(permi0.getDescriptionPermission(), "desc");
			permi0.setIdPermission(5);
			assertEquals(permi0.getIdPermission(), 5);
			permi0.setApplication(data);
			assertEquals(permi0.getApplication(), data);
			
			permissions.add(permi0);
			data.setPermissions(permissions);
			assertNotNull(data.getPermissions());
			assertEquals(data.getPermissions(), permissions);

			// Main App
			assertNull(data.getMainApplication());
			data.setMainApplication(data);
			assertEquals(data.getMainApplication(), data);
			
			// Childs
			assertNotNull(data.getChildApplications());
			Set<ApplicationData> childs = new LinkedHashSet<ApplicationData>();
			childs.add(data);
			data.setChildApplications(childs);
			assertNotNull(data.getChildApplications());
			assertEquals(data.getChildApplications(), childs);
			
			/*
			data.set;
			assertEquals(data.get, "");
			 */
			

		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	public void testPermissionEquals(){
		PermissionData permi0 = new PermissionData();
		
		assertEquals(true, permi0.equals(permi0));
		
		assertEquals(false, permi0.equals(new ApplicationData()));
		
		assertEquals(true, permi0.equals(new PermissionData()));
		
		permi0.setIdPermission(5);
		assertEquals(false, permi0.equals(new PermissionData()));
	}

}
