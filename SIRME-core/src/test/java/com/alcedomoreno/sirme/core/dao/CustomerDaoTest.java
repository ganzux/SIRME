package com.alcedomoreno.sirme.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.core.AppTestConfig;
import com.alcedomoreno.sirme.core.data.AddressData;
import com.alcedomoreno.sirme.core.data.CustomerData;
import com.alcedomoreno.sirme.core.data.ManagerData;
import com.google.common.collect.Iterables;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=AppTestConfig.class)
public class CustomerDaoTest {

	@Autowired
	WorkDao workDao;

	@Autowired
	CustomersDao customerDao;


	@Test
	@Transactional
	public void basicTest() {
		try {
			CustomersDaoImpl impl = new CustomersDaoImpl();
			assertNotNull(impl);
			
			Collection<CustomerData> customers = customerDao.getAllCustomers();
			assertNotNull(customers);
			assertEquals(customers.size(), 2);
			
			Collection<ManagerData> namagers = customerDao.getAllManagers();
			assertNotNull(namagers);
			assertEquals(namagers.size(), 1);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void saveManager(){
		try {
			ManagerData manager = new ManagerData();
			manager.setIdManager(0);
			manager.setMailManager("mailManager");
			manager.setNameManager("nameManager");
			manager.setPhoneManager("phoneManager");
			
			int id = customerDao.save(manager);
			
			assertEquals(id, 2);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void saveCustomer(){
		try {
			CustomerData customer = new CustomerData();
			customer.setIdCustomer(0);
			customer.setCifCustomer("cifCustomer2");
			customer.setActive(false);
			customer.setCodeCustomer(new Integer(288));
			customer.setDateCreated(new Date());
			customer.setMainAddress("mainAddress");
			customer.setMainMail("mainMail");
			customer.setMainPhone("mainPhone");
			customer.setMainPobl("mainPobl");
			customer.setMainPostalCode("mainPostalCode");
			customer.setNameCustomer("nameCustomer2");
			customer.setTypeCustomer(1);
			customer.setCommercial(null);

			customerDao.save(customer);
			
			Collection<CustomerData> customers = customerDao.getAllCustomers();
			assertEquals(customers.size(), 3);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void getCustomerTest(){
		try {
			CustomerData filter = new CustomerData();
			CustomerData customer = customerDao.getCustomer(filter);
			assertNull(customer);
			
			filter = new CustomerData();
			filter.setCifCustomer("");
			filter.setCodeCustomer(0);
			customer = customerDao.getCustomer(filter);
			assertNull(customer);
			
			filter = new CustomerData();
			filter.setIdCustomer(1);
			customer = customerDao.getCustomer(filter);
			assertEquals(customer.getNameCustomer(), "nameCustomer");
			
			filter = new CustomerData();
			filter.setCodeCustomer(1);
			customer = customerDao.getCustomer(filter);
			assertEquals(customer.getNameCustomer(), "nameCustomer");
			
			filter = new CustomerData();
			filter.setCifCustomer("cifCustomer");
			customer = customerDao.getCustomer(filter);
			assertEquals(customer.getNameCustomer(), "nameCustomer");
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void updateManagerDataTest(){
		try {
			ManagerData manager = customerDao.getManager(1);
			manager.setNameManager("Batman");
			customerDao.update(manager);
			
			ManagerData newManager = customerDao.getManager(1);
			assertEquals(newManager.getNameManager(), "Batman");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void updateAddressDataTest(){
		try {
			AddressData address = customerDao.getAddressById(1);
			address.setAddress("Wayne Mansion");
			customerDao.update(address);
			
			AddressData newAdd = customerDao.getAddressById(1);
			assertEquals(newAdd.getAddress(), "Wayne Mansion");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void updateCustomerDataTest(){
		try {
			CustomerData customer = customerDao.getCustomer(1);
			customer.setNameCustomer("new nameCustomer");
			customerDao.update(customer);
			
			CustomerData newCustomer = customerDao.getCustomer(1);
			assertEquals(newCustomer.getNameCustomer(), "new nameCustomer");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void deleteCustomerDataTest(){
		try {
			 customerDao.deleteCustomer(2);
			 fail("Never should arrive here");
		} catch (Exception e) {
			assertTrue(true);
		}
		
		try {
			Collection<CustomerData> customers = customerDao.getAllCustomers();
			int oldSize = customers.size();
			
			CustomerData customer = customerDao.getCustomer("2");
			
			for (AddressData address : customer.getAddress()){
				customerDao.deleteAddress(address);
			}
			int reg = customerDao.deleteCustomer(2);
			
			assertEquals(reg, 1);
			
			customers = customerDao.getAllCustomers();
			
			assertEquals(customers.size(), oldSize - reg);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void deleteManagerDataTest(){
		try {
			Collection<ManagerData> managers = customerDao.getAllManagers();
			int oldSize = managers.size();

			int reg = customerDao.deleteManager(1);
			int reg2 = customerDao.deleteManagerContacts(1);
			int reg3 = customerDao.deleteContacts(1);
			
			assertEquals(reg, 1);
			assertEquals(reg2, 0);
			assertEquals(reg3, 0);
			
			managers = customerDao.getAllManagers();
			
			assertEquals(managers.size(), oldSize - reg);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void deleteAddressTest(){
		try {
			CustomerData customer = customerDao.getCustomer(2);

			Collection<Integer> ids = new ArrayList<Integer>();
			for (AddressData address : customer.getAddress()){
				ids.add(address.getIdaddress());
			}
			
			Collection<Integer> notIds = new ArrayList<Integer>();
			notIds.add(1);
			
			int count = customerDao.deleteAddresses(customer.getIdCustomer(), notIds);

			assertEquals(count, ids.size());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void getManagerTest(){
		try {
			ManagerData manager = customerDao.getManager(0);
			assertNull(manager);
			
			manager = customerDao.getManager(1);
			assertNotNull(manager);
			assertEquals(manager.getMailManager(), "batman@superheroes.com");
			assertEquals(manager.getNameManager(), "Bruce Wayne");
			assertEquals(manager.getPhoneManager(), "288");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Transactional
	public void saveAddress(){
		try {
			AddressData address = new AddressData();
			address.setAddress("address");
			address.setAmount("amount");
			address.setIdaddress(0);
			address.setLocation("location");
			address.setMonthMask("12");
			address.setOther("other");
			address.setPobl("pobl");
			address.setPostalCode(28413);
			address.setProv("prov");

			CustomerData cd = customerDao.getCustomer(1);
			cd.getAddress().add(address);
			address.setCustomer(cd);

			customerDao.save(address);
			
			CustomerData customer = customerDao.getCustomer(1);

			assertEquals(Iterables.get(customer.getAddress(), 1), address);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void removeAddress(){
		try {
			CustomerData cd = customerDao.getCustomer(2);

			assertEquals(cd.getAddress().size(), 1);

			int x = customerDao.deleteAddress(Iterables.get(cd.getAddress(), 0));

			assertEquals(x, 1);
		
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void removeAllAddress(){
		try {
			CustomerData cd = customerDao.getCustomer(2);

			assertEquals(cd.getAddress().size(), 1);

			int x = customerDao.deleteAddress(2);

			assertEquals(x, 1);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	

}
