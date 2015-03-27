package com.alcedomoreno.sirme.core.dao;

import static org.junit.Assert.assertNotEquals;

import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.alcedomoreno.sirme.core.AppTestConfig;
import com.alcedomoreno.sirme.core.data.QuestionGroupData;
import com.alcedomoreno.sirme.core.data.ReplyGroupData;
import com.alcedomoreno.sirme.core.data.ReportData;
import com.alcedomoreno.sirme.core.data.WorkData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=AppTestConfig.class)
public class QuestionsTest extends TestCase{

	@Autowired
	private QuestionsDao questionsDao;
	
	@Autowired
	WorkDao workDao;

	@Test
	@Transactional
	public void testQuestions(){
		try {
			QuestionsDaoImpl impl = new QuestionsDaoImpl();
			assertNotNull(impl);

			Collection<ReportData> allTheReports = questionsDao.getAll();

			assertEquals(12, allTheReports.size());

			for (ReportData report : allTheReports){
				assertNotNull(report.getFileReport());
				assertNotNull(report.getNameReport());
				assertNotNull(report.getTitleReport());
				assertNotNull(report.getIdReport());
				assertNotNull(report.getQuestionGroups());

				for (QuestionGroupData questionGrData : report.getQuestionGroups()){
					assertNotNull(questionGrData);
					assertNotNull(questionGrData.getReport());
					assertEquals(report , questionGrData.getReport());
					assertNotNull(questionGrData.getNameQuestionGroup());
					assertNotNull(questionGrData.getIdQuestionGroup());
					assertNotNull(questionGrData.getQuestions());
				}

			}

			assertTrue( true );
		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	@Transactional
	public void tesGetWork(){
		try {
			WorkData wd = questionsDao.getWork(1);
			assertEquals(1, wd.getIdWork());
			
			wd = questionsDao.getWork(0);
			assertNull(wd);
			
			wd = questionsDao.getWorkByAddress(1);
			assertEquals(3, wd.getIdWork());
			
			wd = questionsDao.getWorkByAddress(0);
			assertNull(wd);
		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	@Transactional
	public void tesDeletesWork(){
		
		try {
			WorkData work = new WorkData(3);
			workDao.delete(work);
			fail("Never arrive here because the work has dependencies, remove them first!!!");
		} catch (Exception e){
			assertTrue(true);
		}
		
		try {
			WorkData wd = questionsDao.getWork(3);
			assertEquals(3, wd.getIdWork());
			assertEquals(4, wd.getReplyGroups().size());
			
			for (ReplyGroupData rgd : wd.getReplyGroups()){
				assertEquals(rgd.getReplies().size(), 4 - rgd.getIdReplyGroup());
			}
			
			int replies = questionsDao.deleteRepliesFromWork(3);
			assertEquals(replies, 6);

			int repliesGroup = questionsDao.deleteReplyGroupsFromWork(3);
			assertEquals(repliesGroup, 4);

			workDao.delete(wd);

		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	@Transactional
	public void tesUpdateWork(){
		WorkData wd = questionsDao.getWork(3);
		wd.setData("new Data");
		wd.setReplyGroups(null);
		questionsDao.update(wd);
		
		WorkData newWd = questionsDao.getWork(3);
		assertEquals(wd.getMemo(), newWd.getMemo());
		assertEquals(wd.getStatus(), newWd.getStatus());
		assertEquals(wd.getTypeWork(), newWd.getTypeWork());
		assertEquals("new Data", newWd.getData());
		assertNull(newWd.getReplyGroups());
	}

}
