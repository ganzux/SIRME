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
import com.alcedomoreno.sirme.core.data.QuestionGroupData;
import com.alcedomoreno.sirme.core.data.ReportData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=AppTestConfig.class)
public class QuestionsTest extends TestCase{

	@Autowired
	private QuestionsDao questionsDao;

	@Test
	public void testQuestions(){
		try {
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
			e.printStackTrace();
		}
    }

}
