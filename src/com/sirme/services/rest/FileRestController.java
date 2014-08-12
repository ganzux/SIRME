package com.sirme.services.rest;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sirme.bussiness.FirextFile;
import com.sirme.services.IAdviceService;
import com.sirme.services.rest.dto.CodeDTO;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Controller("fileRestController")
public class FileRestController {
	
	private static Logger log = LoggerFactory.getLogger( FileRestController.class );
	private static final String CLASS_NAME = "FileRestController";

	@Resource(name = SpringConstants.ADVICE_SERVICE)
	protected IAdviceService adviceService;

	@RequestMapping(method = RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA)
	public @ResponseBody CodeDTO upload(@RequestParam("name") String name, 
										 @RequestParam("alertId") String alertId, 
										 @RequestParam("file") MultipartFile file) {

		MyLogger.info(log, CLASS_NAME, "upload", "Subida de Fichero", "IN", name, alertId);

		int photoNumber = 0;

		try{
			adviceService.addImage( alertId, new FirextFile(name,file) );
			photoNumber = adviceService.getPictureSize( alertId );

		} catch (Exception e){
			MyLogger.info(log, CLASS_NAME, "REST", "Subida de Fichero", name, alertId,  e.getMessage());
			return new CodeDTO( CodeDTO.KO, e.getMessage());
		}

		MyLogger.info(log, CLASS_NAME, "upload", "Subida de Fichero", "OUT", name, alertId);
		return new CodeDTO( CodeDTO.OK, String.valueOf(photoNumber) );

	}

}
