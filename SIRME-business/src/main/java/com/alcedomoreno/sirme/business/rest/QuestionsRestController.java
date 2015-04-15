package com.alcedomoreno.sirme.business.rest;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.dto.ReportDTO;
import com.alcedomoreno.sirme.business.services.QuestionService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Controller("allQuestionsRestController")
public class QuestionsRestController {
	
	private static Logger log = LoggerFactory.getLogger( QuestionsRestController.class );
	private static final String CLASS_NAME = "QuestionsRestController";

	@Resource(name = ServiceConstants.QUESTION_SERVICE)
	protected QuestionService questionService;
//	TODO :(
//	@Resource( name=BeanNameUtil.APP_BEAN)
//	private ApplicationBean applicationBean;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Collection<ReportDTO> getAllQuestionsGroups() {
		
		MyLogger.info(log, CLASS_NAME, "Petición de preguntas desde una terminal");
		Collection<ReportDTO> reports = null;

		try{
			reports = new ArrayList<ReportDTO>();
//			applicationBean.addRestQuestions();
			for (Report report : questionService.getAllWithQuestions())
				reports.add(new ReportDTO(report));
		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "Error de Petición de preguntas desde una terminal", e.getMessage());
			
		}

		return reports;

	}
}
