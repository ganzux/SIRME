package com.alcedomoreno.sirme.business.rest;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alcedomoreno.sirme.business.data.Address;
import com.alcedomoreno.sirme.business.data.Advice;
import com.alcedomoreno.sirme.business.data.Team;
import com.alcedomoreno.sirme.business.dto.AdviceDTO;
import com.alcedomoreno.sirme.business.dto.CodeDTO;
import com.alcedomoreno.sirme.business.services.AdviceService;
import com.alcedomoreno.sirme.business.services.CustomerService;
import com.alcedomoreno.sirme.business.services.TeamService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Controller("adviceInitRestController")
public class AdvicedInitRestController {
	
	private static Logger log = LoggerFactory.getLogger( AdvicedInitRestController.class );
	private static final String CLASS_NAME = "AdvicedInitRestController";

	@Resource(name = ServiceConstants.ADVICE_SERVICE)
	protected AdviceService adviceService;
	
	@Resource(name = ServiceConstants.TEAM_SERVICE)
	protected TeamService teamService;
	
	@Resource(name = ServiceConstants.CUSTOMER_SERVICE)
	protected CustomerService customerService;

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
					adviceService.insert( data.getAlertId(), new Advice(data) );
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
