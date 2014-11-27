package com.alcedomoreno.sirme.business.rest;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.dto.ReportDTO;
import com.alcedomoreno.sirme.business.services.QuestionService;

@RunWith(MockitoJUnitRunner.class)
public class QuestionRestControllerTest extends TestCase {


	@InjectMocks
	private QuestionsRestController questionRestController;
	
	@Mock
	QuestionService questionService;

	@Test
	public void testAllTheReports(){
		try {
			when(questionService.getAllWithQuestions()).thenReturn(getDummyReports());
			Collection<ReportDTO> allReports = questionRestController.getAllQuestionsGroups();
			assertEquals(allReports.size(), 10);
		} catch (Exception e){
			fail(e.getMessage());
		}
    }
	
	@Test
	public void testAllTheReportsException(){
	
		try{
			when(questionService.getAllWithQuestions()).thenThrow(Exception.class);
			questionRestController.getAllQuestionsGroups();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertTrue(true);
    }
	
	private Collection<Report> getDummyReports(){
		Collection<Report> reports = new ArrayList<Report>();
		
		for (int i = 0 ; i < 10 ; i++){
			Report report = new Report();
			
			report.setIdReport(i);
			report.setNameReport("NAME_" + i);
			report.setTitleReport("TITLE_" + i);
			report.setFileReport("FILE_" + i);
			
			reports.add(report);
		}
		
		return reports;
	}
	
}
