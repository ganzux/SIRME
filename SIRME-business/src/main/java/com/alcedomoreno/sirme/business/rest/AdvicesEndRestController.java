package com.alcedomoreno.sirme.business.rest;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.business.dto.CodeDTO;
import com.alcedomoreno.sirme.business.dto.IntDTO;
import com.alcedomoreno.sirme.business.services.UpdatedService;
import com.alcedomoreno.sirme.business.services.WorkService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Controller("getAdvicesEndRestController")
public class AdvicesEndRestController {
	
	private static Logger log = LoggerFactory.getLogger( AdvicesEndRestController.class );
	private static final String CLASS_NAME = "AdvicesEndRestController";

	@Resource(name = ServiceConstants.WORK_SERVICE)
	protected WorkService worksService;
	
	@Resource(name = ServiceConstants.UPDATED_SERVICE)
	protected UpdatedService updateService;

	@RequestMapping( produces="application/json", method = RequestMethod.POST )
	public @ResponseBody CodeDTO changeStatus(@RequestBody IntDTO workInDTO ) {
		
		MyLogger.info(log, CLASS_NAME, "changeStatus", "IN", workInDTO.getId());

		try{
			worksService.changeStatus(workInDTO.getId(), Work.STATUS_DESCARGADO);
			updateService.refreshDate();
		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "changeStatus", workInDTO.getId(), e.getMessage());
			return new CodeDTO( CodeDTO.KO, workInDTO.getId() + "-" + e.getMessage());
		}

		MyLogger.info(log, CLASS_NAME, "changeStatus", "OUT", workInDTO.getId());
		return new CodeDTO( CodeDTO.OK, String.valueOf(workInDTO.getId() ) );

	}
}
