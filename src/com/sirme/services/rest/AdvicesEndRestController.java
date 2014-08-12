package com.sirme.services.rest;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sirme.bussiness.Work;
import com.sirme.services.IUpdatedService;
import com.sirme.services.IWorkService;
import com.sirme.services.rest.dto.CodeDTO;
import com.sirme.services.rest.dto.IntDTO;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Controller("getAdvicesEndRestController")
public class AdvicesEndRestController {
	
	private static Logger log = LoggerFactory.getLogger( AdvicesEndRestController.class );
	private static final String CLASS_NAME = "AdvicesEndRestController";

	@Resource(name = SpringConstants.WORK_SERVICE)
	protected IWorkService worksService;
	
	@Resource(name = SpringConstants.UPDATED_SERVICE)
	protected IUpdatedService updateService;

	@RequestMapping( produces="application/json", method = RequestMethod.POST )
	public @ResponseBody CodeDTO changeStatus(@RequestBody IntDTO work ) {
		
		MyLogger.info(log, CLASS_NAME, "changeStatus", "IN", work.getId());

		try{
			worksService.changeStatus(work.getId(), Work.STATUS_DESCARGADO);
			updateService.refreshDate();
		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "openWork", work.getId(), e.getMessage());
			return new CodeDTO( CodeDTO.KO, work.getId() + "-" + e.getMessage());
		}

		MyLogger.info(log, CLASS_NAME, "changeStatus", "OUT", work.getId());
		return new CodeDTO( CodeDTO.OK, String.valueOf(work.getId() ) );

	}
}
