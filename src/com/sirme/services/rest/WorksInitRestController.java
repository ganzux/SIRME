package com.sirme.services.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sirme.beans.ApplicationBean;
import com.sirme.bussiness.Work;
import com.sirme.services.IWorkService;
import com.sirme.services.rest.dto.IntDTO;
import com.sirme.services.rest.dto.WorkDTO;
import com.sirme.util.BeanNameUtil;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Controller("getWorksInitRestController")
public class WorksInitRestController {
	
	private static Logger log = LoggerFactory.getLogger( WorksInitRestController.class );
	private static final String CLASS_NAME = "WorksInitRestController";

	@Resource(name = SpringConstants.WORK_SERVICE)
	protected IWorkService worksService;

	@Resource( name=BeanNameUtil.APP_BEAN)
	private ApplicationBean applicationBean;

	@RequestMapping( produces="application/json", method = RequestMethod.POST )
	public @ResponseBody Collection<WorkDTO> getAdvices(@RequestBody IntDTO teamId ) {
		
		MyLogger.info(log, CLASS_NAME, "getAdvices", "IN", teamId.getId());
		Collection<WorkDTO> worksDto = new ArrayList<WorkDTO>();

		try{			
			Collection<Work> works = worksService.getOpenAdvicesOrWorksFromTeam( teamId.getId(), new Date(), true );
			
			if ( works != null && !works.isEmpty() )
				for ( Work w:works )
					worksDto.add( new WorkDTO(w) );
			
			applicationBean.addRestWorks( teamId.getId() );

		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "getAdvices", teamId.getId(), e.getMessage());
		}

		MyLogger.info(log, CLASS_NAME, "getAdvices", "OUT", teamId.getId());
		return worksDto;

	}
}
