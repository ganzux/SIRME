package com.alcedomoreno.sirme.core.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.core.AppTestConfig;
import com.alcedomoreno.sirme.core.dao.common.GenericDao;
import com.alcedomoreno.sirme.core.data.QuestionData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=AppTestConfig.class)
public class GenericHibernateDaoTest {

	@Autowired
	GenericDao<QuestionData> genericDao;

	@Test
	@Transactional
	public void loadAllTest(){

		try {
			genericDao.loadAll(QuestionData.class);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	public void loadByIdTest(){

		try {
			QuestionData question = genericDao.findById(QuestionData.class, 1);
			assertEquals(question.getIdQuestion(), 1);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
