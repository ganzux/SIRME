package com.sirme.services.rest;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sirme.bussiness.Address;
import com.sirme.bussiness.Advice;
import com.sirme.bussiness.Team;
import com.sirme.services.IAdviceService;
import com.sirme.services.ICustomerService;
import com.sirme.services.ITeamService;
import com.sirme.services.rest.dto.AdviceDTO;
import com.sirme.services.rest.dto.CodeDTO;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Controller("adviceInitRestController")
public class AdvicedInitRestController {
	
	private static Logger log = LoggerFactory.getLogger( AdvicedInitRestController.class );
	private static final String CLASS_NAME = "AdvicedInitRestController";

	@Resource(name = SpringConstants.ADVICE_SERVICE)
	protected IAdviceService adviceService;
	
	@Resource(name = SpringConstants.TEAM_SERVICE)
	protected ITeamService teamService;
	
	@Resource(name = SpringConstants.CUSTOMER_SERVICE)
	protected ICustomerService customerService;

	@RequestMapping( produces="application/json", method = RequestMethod.POST )
	public @ResponseBody CodeDTO openWork(@RequestBody AdviceDTO data ) {
		
		MyLogger.info(log, CLASS_NAME, "openWork", "IN", data);

		try{
			Team team = teamService.get( data.getTeam(),data.getPassword() );
			if ( team != null  ) {
				MyLogger.info(log, CLASS_NAME, "openWork", "Equipo de Trabajo encontrado");

				int idAddress = 0;
				try{
					idAddress = Integer.parseInt( data.getIdClient() );
				} catch( Exception e ){
					throw new Exception("El Id tiene que ser numérico");
				}

				Address address =  customerService.getAddressById( idAddress );
				
				if ( address != null ){

					MyLogger.info(log, CLASS_NAME, "openWork", "Cliente con dirección encontrados");
					MyLogger.info(log, CLASS_NAME, "openWork", data.getAlertId(), "Insertando en cola de trabajos...");
					adviceService.insertAdvice( data.getAlertId(), new Advice(data) );
					MyLogger.info(log, CLASS_NAME, "openWork", data.getAlertId(), "Insertando en cola de trabajos OK");

				} else
					throw new Exception("No se ha encontrado ese Cliente con esa dirección");
			} else
				throw new Exception("No existe ese equipo de Trabajo");
			
		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "openWork", data.getAlertId(), e.getMessage());
			return new CodeDTO( CodeDTO.KO, e.getMessage());
		}

		MyLogger.info(log, CLASS_NAME, "openWork", "OUT", data);
		return new CodeDTO( CodeDTO.OK, String.valueOf(data.getAlertId()));

	}
}
