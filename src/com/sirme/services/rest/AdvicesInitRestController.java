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

import com.sirme.bussiness.Work;
import com.sirme.services.IWorkService;
import com.sirme.services.rest.dto.AdviceDTO;
import com.sirme.services.rest.dto.IntDTO;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Controller("getAdvicesInitRestController")
public class AdvicesInitRestController {
	
	private static Logger log = LoggerFactory.getLogger( AdvicesInitRestController.class );
	private static final String CLASS_NAME = "AdvicesInitRestController";

	@Resource(name = SpringConstants.WORK_SERVICE)
	protected IWorkService worksService;

	@RequestMapping( produces="application/json", method = RequestMethod.POST )
	public @ResponseBody Collection<AdviceDTO> getAdvices(@RequestBody IntDTO teamId ) {
		
		MyLogger.info(log, CLASS_NAME, "getAdvices", "IN", teamId.getId());
		Collection<AdviceDTO> allAdvices = new ArrayList<AdviceDTO>();

		try{			
			Collection<Work> works = worksService.getOpenAdvicesOrWorksFromTeam( teamId.getId(), new Date(), false );
			
			if ( works != null && !works.isEmpty() )
				for ( Work w:works )
					allAdvices.add( new AdviceDTO(w) );

		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "getAdvices", teamId.getId(), e.getMessage());
		}

		MyLogger.info(log, CLASS_NAME, "getAdvices", "OUT", teamId.getId());
		return allAdvices;

	}
}
