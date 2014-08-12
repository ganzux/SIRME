package com.sirme.services.rest;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sirme.bussiness.Report;
import com.sirme.services.IQuestionService;
import com.sirme.services.rest.dto.ReportDTO;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Controller("allQuestionsRestController")
public class QuestionsRestController {
	
	private static Logger log = LoggerFactory.getLogger( QuestionsRestController.class );
	private static final String CLASS_NAME = "QuestionsRestController";

	@Resource(name = SpringConstants.QUESTION_SERVICE)
	protected IQuestionService questionService;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Collection<ReportDTO> getAllQuestionsGroups() {
		
		MyLogger.info(log, CLASS_NAME, "Petición de preguntas desde una terminal");
		Collection<ReportDTO> reports = null;

		try{
			reports = new ArrayList<ReportDTO>();
			for (Report report : questionService.getAllWithQuestions())
				reports.add(  new ReportDTO( report ) );
		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "Error de Petición de preguntas desde una terminal", e.getMessage());
			
		}

		return reports;

	}
}
