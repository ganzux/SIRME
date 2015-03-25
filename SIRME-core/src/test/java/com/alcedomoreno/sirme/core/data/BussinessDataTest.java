package com.alcedomoreno.sirme.core.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

public class BussinessDataTest {

	@Test
	public void testConstructor(){
		try {
			AddressData data0 = new AddressData(1);
			assertNotNull(data0);
			AddressData data1 = new AddressData(1);
			assertEquals(data1.getIdaddress(), 1);
			
			CustomerData customer0 = new CustomerData();
			assertNotNull(customer0);
			CustomerData customer1 = new CustomerData(288);
			assertEquals(customer1.getIdCustomer(), 288);
			
			WorkData work0 = new WorkData();
			assertNotNull(work0);
			WorkData work1 = new WorkData(144);
			assertEquals(work1.getIdWork(), 144);
			
			PhotoData photo = new PhotoData();
			assertNotNull(photo);
			photo = new PhotoData(144);
			assertEquals(photo.getIdPhoto(), 144);
			
			ContactData contact = new ContactData();
			assertNotNull(contact);
			contact = new ContactData(77);
			assertEquals(contact.getIdContact(), 77);
			
			ManagerData manager = new ManagerData ();
			assertNotNull(manager);
			manager = new ManagerData(75);
			assertEquals(manager.getIdManager(), 75);
			
			ManagerContactData contact2 = new ManagerContactData();
			assertNotNull(contact2);
			contact2 = new ManagerContactData(75);
			assertEquals(contact2.getIdManagerContact(), 75);
		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	public void testAddressGetSet(){
		try {
			AddressData data = new AddressData(1);

			data.setAddress("address");
			assertEquals(data.getAddress(), "address");

			data.setIdaddress(28);
			assertEquals(data.getIdaddress(), 28);

			data.setProv("prov");
			assertEquals(data.getProv(), "prov");

			data.setLocation("location");
			assertEquals(data.getLocation(), "location");

			data.setPobl("pobl");
			assertEquals(data.getPobl(), "pobl");

			data.setPostalCode(98765);
			assertEquals(data.getPostalCode(), new Integer(98765));

			data.setAmount("amount");
			assertEquals(data.getAmount(), "amount");

			data.setMonthMask("monthMask");
			assertEquals(data.getMonthMask(), "monthMask");

			data.setOther("other");
			assertEquals(data.getOther(), "other");
			
			// Customer
			CustomerData customer = new CustomerData();
			data.setCustomer(customer);
			assertEquals(data.getCustomer(), customer);

			// Works
			assertNull(data.getWorks());
			Collection<WorkData> works = new ArrayList<WorkData>();
			data.setWorks(works);
			assertNotNull(data.getWorks());

		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	public void testCustomerGetSet(){
		try {
			CustomerData data = new CustomerData(1);

			data.setActive(false);
			assertEquals(data.getActive(), false);
			
			data.setCifCustomer("cifCustomer");
			assertEquals(data.getCifCustomer(), "cifCustomer");
			
			data.setCodeCustomer(288);
			assertEquals(data.getCodeCustomer(), new Integer(288));
			
			Date dateCreated = new Date(995544l);
			data.setDateCreated(dateCreated);
			assertEquals(data.getDateCreated(), dateCreated);
			
			data.setIdCustomer(5);
			assertEquals(data.getIdCustomer(), 5);
			
			data.setMainAddress("mainAddress");
			assertEquals(data.getMainAddress(), "mainAddress");
			
			data.setMainMail("mainMail");
			assertEquals(data.getMainMail(), "mainMail");
			
			data.setMainPhone("mainPhone");
			assertEquals(data.getMainPhone(), "mainPhone");
			
			data.setMainPobl("mainPobl");
			assertEquals(data.getMainPobl(), "mainPobl");
			
			data.setMainPostalCode("mainPostalCode");
			assertEquals(data.getMainPostalCode(), "mainPostalCode");
			
			data.setMainProv("mainProv");
			assertEquals(data.getMainProv(), "mainProv");
			
			data.setNameCustomer("nameCustomer");
			assertEquals(data.getNameCustomer(), "nameCustomer");
			
			data.setTypeCustomer(-9);
			assertEquals(data.getTypeCustomer(), new Integer(-9));
			
			// Manager
			ManagerData manager = new ManagerData();
			assertNull(data.getManager());
			data.setManager(manager);
			assertEquals(data.getManager(), manager);
			
			// Comercial
			UserData commercial = new UserData();
			assertNull(data.getCommercial());
			data.setCommercial(commercial);
			assertEquals(data.getCommercial(), commercial);
			
			// Contacts
			assertNull(data.getContacts());
			Set<ContactData> contacts = new LinkedHashSet<ContactData>();
			data.setContacts(contacts);
			assertEquals(data.getContacts() , contacts);
			
			// Address
			Collection<AddressData> address = new ArrayList<AddressData>();
			assertNull(data.getAddress());
			data.setAddress(address);
			assertEquals(data.getAddress(), address);

		} catch (Exception e){
			fail(e.getMessage());
		}
    }

	@Test
	public void testWorkGetSet(){
		try {
			WorkData work = new WorkData();
			
			work.setAlbaran(288);
			assertEquals(work.getAlbaran(), 288);
			
			work.setData("data");
			assertEquals(work.getData(), "data");
			
			work.setDate(new Date(1000));
			assertEquals(work.getDate(), new Date(1000));
			
			work.setDateCreated(new Date(69));
			assertEquals(work.getDateCreated(), new Date(69));
			
			work.setIdWork(3);
			assertEquals(work.getIdWork(), 3);
			
			work.setMemo("memo");
			assertEquals(work.getMemo(), "memo");
			
			work.setSignName("signName");
			assertEquals(work.getSignName(), "signName");

			work.setSignpath("signpath");
			assertEquals(work.getSignpath(), "signpath");

			work.setStatus(9);
			assertEquals(work.getStatus(), 9);

			work.setTypeWork(7);
			assertEquals(work.getTypeWork(), 7);

			work.setYear(2015);
			assertEquals(work.getYear(), 2015);

			// Address
			AddressData address = new AddressData();
			assertNull(work.getAddress());
			work.setAddress(address);
			assertEquals(work.getAddress(), address);

			// Photos
			Set<PhotoData> photos = null;
			assertNotNull(work.getPhotos());
			work.setPhotos(photos);
			assertEquals(work.getPhotos(), photos);
			
			// Team
			TeamData team = new TeamData();
			assertNull(work.getTeam());
			work.setTeam(team);
			assertEquals(work.getTeam(), team);

			// ReplyGroups
			Set<ReplyGroupData> replyGroups = null;
			assertNotNull(work.getReplyGroups());
			work.setReplyGroups(replyGroups);
			assertEquals(work.getReplyGroups(), replyGroups);

		} catch (Exception e){
			fail(e.getMessage());
		}
    }

	@Test
	public void testPhotoGetSet(){
		try {
			PhotoData photo = new PhotoData();

			photo.setIdPhoto(10);
			assertEquals(photo.getIdPhoto(), 10);
			
			photo.setComments("comments");
			assertEquals(photo.getComments(), "comments");

			photo.setDateCreated(new Date(7));
			assertEquals(photo.getDateCreated(), new Date(7));

			photo.setPath("path");
			assertEquals(photo.getPath(), "path");
			
			WorkData work = new WorkData();
			assertNull(photo.getWork());
			photo.setWork(work);
			assertEquals(photo.getWork(), work);
			
		} catch (Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testContactGetSet(){
		try {
			ContactData contact = new ContactData();

			contact.setDataContact("dataContact");
			assertEquals(contact.getDataContact(), "dataContact");
			
			contact.setNameContact("nameContact");
			assertEquals(contact.getNameContact(), "nameContact");
			
			// Customer
			assertNull(contact.getCustomer());
			CustomerData customer = new CustomerData();
			contact.setCustomer(customer);
			assertEquals(contact.getCustomer(), customer);
			
			contact.setIdContact(4);
			assertEquals(contact.getIdContact(), 4);

		} catch (Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testManagerGetSet(){
		try {
			ManagerData manager = new ManagerData ();

			manager.setIdManager(8);
			assertEquals(manager.getIdManager(), 8);

			manager.setMailManager("mailManager");
			assertEquals(manager.getMailManager(), "mailManager");

			manager.setNameManager("nameManager");
			assertEquals(manager.getNameManager(), "nameManager");

			manager.setPhoneManager("phoneManager");
			assertEquals(manager.getPhoneManager(), "phoneManager");

			// Contacts
			assertNull(manager.getContacts());
			Set<ManagerContactData> contacts = new LinkedHashSet<ManagerContactData>();
			manager.setContacts(contacts);
			assertEquals(manager.getContacts() , contacts);
			
			// Customers
			assertNotNull(manager.getCustomers());
			Set<CustomerData> customers = new LinkedHashSet<CustomerData>();
			manager.setCustomers(customers);
			assertEquals(manager.getCustomers() , customers);

		} catch (Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testManagerContactDataGetSet(){
		try {
			ManagerContactData manager = new ManagerContactData ();

			manager.setIdManagerContact(8);
			assertEquals(manager.getIdManagerContact(), 8);

			manager.setDataContact("dataContact");
			assertEquals(manager.getDataContact(), "dataContact");

			manager.setNameContact("nameContact");
			assertEquals(manager.getNameContact(), "nameContact");

			// Manager
			ManagerData manager2 = new ManagerData();
			assertNull(manager.getManager());
			manager.setManager(manager2);
			assertEquals(manager.getManager(), manager2);

		} catch (Exception e){
			fail(e.getMessage());
		}
		
		try {
			PKWorkData pKWorkData = new PKWorkData();
			
			pKWorkData = new PKWorkData(3, 2);
			assertEquals(pKWorkData.getIdWork(), 3);
			assertEquals(pKWorkData.getYear(), 2);
			
			pKWorkData.setIdWork(6);
			assertEquals(pKWorkData.getIdWork(), 6);
			
			pKWorkData.setYear(2016);
			assertEquals(pKWorkData.getYear(), 2016);
		} catch (Exception e){
			fail(e.getMessage());
		}
		
	}
	
	
}
