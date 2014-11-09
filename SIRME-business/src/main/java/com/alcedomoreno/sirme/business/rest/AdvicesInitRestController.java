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

import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.business.dto.AdviceDTO;
import com.alcedomoreno.sirme.business.dto.IntDTO;
import com.alcedomoreno.sirme.business.services.WorkService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Controller("getAdvicesInitRestController")
public class AdvicesInitRestController {
	
	private static Logger log = LoggerFactory.getLogger( AdvicesInitRestController.class );
	private static final String CLASS_NAME = "AdvicesInitRestController";

	@Resource(name = ServiceConstants.WORK_SERVICE)
	protected WorkService worksService;
	
//	TODO :(
//	@Resource( name=BeanNameUtil.APP_BEAN)
//	private ApplicationBean applicationBean;

	@RequestMapping( produces="application/json", method = RequestMethod.POST )
	public @ResponseBody Collection<AdviceDTO> getAdvices(@RequestBody IntDTO teamId ) {
		
		MyLogger.info(log, CLASS_NAME, "getAdvices", "IN", teamId.getId());
		Collection<AdviceDTO> allAdvices = new ArrayList<AdviceDTO>();

		try{			
			Collection<Work> works = worksService.getOpenAdvicesOrWorksFromTeam( teamId.getId(), new Date(), false );
			
			if ( works != null && !works.isEmpty() )
				for ( Work w:works )
					allAdvices.add( new AdviceDTO(w) );
			
//			applicationBean.addRestAdvices( teamId.getId() );

		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "getAdvices", teamId.getId(), e.getMessage());
		}

		MyLogger.info(log, CLASS_NAME, "getAdvices", "OUT", teamId.getId());
		return allAdvices;

	}
}
