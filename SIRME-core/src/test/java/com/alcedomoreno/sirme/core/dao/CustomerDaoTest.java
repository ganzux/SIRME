package com.alcedomoreno.sirme.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import com.alcedomoreno.sirme.core.data.QuestionData;
import com.alcedomoreno.sirme.core.data.QuestionGroupData;
import com.alcedomoreno.sirme.core.data.ReplyData;
import com.alcedomoreno.sirme.core.data.ReplyGroupData;
import com.alcedomoreno.sirme.core.data.ReportData;
import com.alcedomoreno.sirme.core.data.WorkData;
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
			assertEquals(namagers.size(), 0);
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
			
			assertEquals(id, 1);
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
