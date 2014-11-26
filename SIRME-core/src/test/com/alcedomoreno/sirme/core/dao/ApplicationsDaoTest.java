package com.alcedomoreno.sirme.core.dao;

import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.alcedomoreno.sirme.core.AppTestConfig;
import com.alcedomoreno.sirme.core.data.ApplicationData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=AppTestConfig.class)
public class ApplicationsDaoTest extends TestCase{
	
	@Autowired
	private ApplicationsDao applicationsDao;

	@Test
	public void testApp(){
		try {
			ApplicationData root = applicationsDao.getRootApplication();
			Collection<ApplicationData> childs = applicationsDao.getChildApplications(root.getIdApplication());

			assertTrue( true );
		} catch (Exception e){
			e.printStackTrace();
		}
    }
}
