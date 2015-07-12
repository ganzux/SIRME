package com.alcedomoreno.sirme.business.rest;

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

import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.business.dto.IntDTO;
import com.alcedomoreno.sirme.business.dto.ReportDTO;
import com.alcedomoreno.sirme.business.dto.WorkDTO;
import com.alcedomoreno.sirme.business.services.WorkService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Controller("getWorksInitRestController")
public class WorksInitRestController {
	
	private static Logger log = LoggerFactory.getLogger( WorksInitRestController.class );
	private static final String CLASS_NAME = "WorksInitRestController";

	@Resource(name = ServiceConstants.WORK_SERVICE)
	protected WorkService worksService;

	// TODO
//	@Resource( name=BeanNameUtil.APP_BEAN)
//	private ApplicationBean applicationBean;

	@RequestMapping( produces="application/json", method = RequestMethod.POST )
	public @ResponseBody Collection<WorkDTO> getAdvices(@RequestBody IntDTO teamId ) {
		
		MyLogger.info(log, CLASS_NAME, "getAdvices", "IN", teamId.getId());
		Collection<WorkDTO> worksDto = new ArrayList<WorkDTO>();

		try{			
			Collection<Work> works = worksService.getOpenAdvicesOrWorksFromTeam( teamId.getId(), new Date(), true );
			
			if ( works != null && !works.isEmpty() ){
				for (Work w : works){
					WorkDTO dto = new WorkDTO(w);
					dto.sort();
					worksDto.add(dto);
				}
			}
			
//			applicationBean.addRestWorks( teamId.getId() );

		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "getAdvices", teamId.getId(), e.getMessage());
		}

		MyLogger.info(log, CLASS_NAME, "getAdvices", "OUT", teamId.getId());
		return worksDto;

	}
}
