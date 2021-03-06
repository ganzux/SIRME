package com.alcedomoreno.sirme.business.rest;


import java.util.Collection;

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
import com.alcedomoreno.sirme.business.dto.WorkInfoDTO;
import com.alcedomoreno.sirme.business.dto.WorkInfoRequestDTO;
import com.alcedomoreno.sirme.business.services.WorkService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.dao.WorkDaoImpl;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Controller("worksController")
public class WorksRestController {

	@Resource(name = ServiceConstants.WORK_SERVICE)
	protected WorkService worksService;
	
	private static Logger log = LoggerFactory.getLogger( WorkDaoImpl.class );
	private static final String CLASS_NAME = "WorksRestController";

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	WorkInfoDTO retrieveWorksByTeam(@RequestBody WorkInfoRequestDTO worksRequest) {

		Work work = worksService.get(worksRequest.getWorkId());
		
		Collection<Report> reports = work.getReports();
		
		
		
		WorkInfoDTO workDto = new WorkInfoDTO();

		try{ workDto.setAlbaran( work.getCodeWork() ); } catch ( Exception e ){ MyLogger.error( log , CLASS_NAME, "retrieveWorksByTeam", "getCodeWork", e.getMessage()); }
		try{ workDto.setClientId (work.getCustomer().getIdCustomer() ); } catch ( Exception e ){ MyLogger.error( log , CLASS_NAME, "retrieveWorksByTeam", "getCustomer().getIdCustomer", e.getMessage()); }
		try{ workDto.setClientName( work.getCustomer().getNameCustomer() ); } catch ( Exception e ){ MyLogger.error( log , CLASS_NAME, "retrieveWorksByTeam", "getCustomer().getNameCustomer", e.getMessage()); }
		try{ workDto.setDate( work.getDate().getTime() ); } catch ( Exception e ){ MyLogger.error( log , CLASS_NAME, "retrieveWorksByTeam", "getDate().getTime", e.getMessage()); }
		try{ workDto.setWorkId( work.getIdWork() ); } catch ( Exception e ){ MyLogger.error( log , CLASS_NAME, "retrieveWorksByTeam", "getIdWork", e.getMessage()); }
		try{ workDto.setAddress( work.getCustomer().getServiceAddress() ); } catch ( Exception e ){ MyLogger.error( log , CLASS_NAME, "retrieveWorksByTeam", "getCustomer().getServiceAddress", e.getMessage()); }
		try{ workDto.setProv( work.getCustomer().getServiceProv() ); } catch ( Exception e ){ MyLogger.error( log , CLASS_NAME, "retrieveWorksByTeam", "getCustomer().getServiceProv", e.getMessage()); }
		try{ workDto.setPobl( work.getCustomer().getServicePobl() ); } catch ( Exception e ){ MyLogger.error( log , CLASS_NAME, "retrieveWorksByTeam", "getCustomer().getServicePobl", e.getMessage()); }
		try{ workDto.setPostalCode( work.getCustomer().getServicePostalCode() ); } catch ( Exception e ){ MyLogger.error( log , CLASS_NAME, "retrieveWorksByTeam", "getCustomer().getServicePostalCode", e.getMessage()); }
		try{ workDto.setTelf( work.getCustomer().getMainPhone() ); } catch ( Exception e ){ MyLogger.error( log , CLASS_NAME, "retrieveWorksByTeam", "getCustomer().getMainPhone", e.getMessage()); }
		try{ workDto.setNotes( work.getMemo() ); } catch ( Exception e ){ MyLogger.error( log , CLASS_NAME, "retrieveWorksByTeam", "getMemo", e.getMessage()); }

		
		
		return workDto;

	}

}
