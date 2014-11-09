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

import com.alcedomoreno.sirme.business.data.Team;
import com.alcedomoreno.sirme.business.dto.ChangePassWordInDTO;
import com.alcedomoreno.sirme.business.dto.ChangePassWordOutDTO;
import com.alcedomoreno.sirme.business.services.TeamService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Controller("passwordRestController")
public class ChangePasswordRestController {
	
	private static Logger log = LoggerFactory.getLogger( ChangePasswordRestController.class );
	private static final String CLASS_NAME = "ChangePasswordRestController";

	@Resource(name = ServiceConstants.TEAM_SERVICE)
	protected TeamService teamService;
//	TODO :(
//	@Resource( name=BeanNameUtil.APP_BEAN)
//	private ApplicationBean applicationBean;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	ChangePassWordOutDTO changePassword(@RequestBody ChangePassWordInDTO loginInData) {
		
		MyLogger.info(log, CLASS_NAME, "changePassword", "Petición de cambio de password desde terminal", loginInData);

		ChangePassWordOutDTO logonDTO = new ChangePassWordOutDTO();
		logonDTO.setError("Team Not Found");
		logonDTO.setOperation("KO");
		logonDTO.setTeam( loginInData.getTeam() );

		try{
			Collection<Team> teams = teamService.getAll();
	
			for (Team team : teams) {
	
				if (team.getNameTeam().equals(loginInData.getTeam()) && team.getPassWord().equals(loginInData.getPassword())) {
					
					MyLogger.info(log, CLASS_NAME, "changePassword", "Equipo encontrado", loginInData);
					
					teamService.updateMD5Password(team.getIdTeam(), loginInData.getNewPassword(), loginInData.getNewPassword());
					
					MyLogger.info(log, CLASS_NAME, "changePassword", "password cambiado", loginInData);
	
					logonDTO.setError( null );
					logonDTO.setOperation("OK");
					logonDTO.setTeam( loginInData.getTeam() );
					
//					applicationBean.addRestPassword( team );
	
					break;
				}
	
			}
		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "changePassword", "Error de cambio de password desde terminal", e.getMessage());
			logonDTO.setError( e.getMessage() );
			logonDTO.setOperation("KO");
			logonDTO.setTeam( loginInData.getTeam() );
		}

		return logonDTO;

	}
}
