package com.sirme.services.rest;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sirme.beans.ApplicationBean;
import com.sirme.bussiness.Team;
import com.sirme.services.ITeamService;
import com.sirme.services.rest.dto.LoginInDTO;
import com.sirme.services.rest.dto.LogonDTO;
import com.sirme.util.BeanNameUtil;
import com.sirme.util.ConfigService;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Controller("loginRestController")
public class LoginRestController {

	private static Logger log = LoggerFactory.getLogger( LoginRestController.class );
	private static final String CLASS_NAME = "LoginRestController";

	@Resource(name = SpringConstants.TEAM_SERVICE)
	protected ITeamService teamService;
	
	@Resource( name=BeanNameUtil.APP_BEAN)
	private ApplicationBean applicationBean;
	
	@Resource
	private ConfigService cfg;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody LogonDTO login(@RequestBody LoginInDTO loginInData) {

		MyLogger.info(log, CLASS_NAME, "login", "INIT", "Petición de login desde terminal", loginInData);

		LogonDTO logonDTO = new LogonDTO();

		Team team = teamService.get( loginInData.getTeam(),loginInData.getPassword() );
		
		if ( team != null  ) {
			MyLogger.info(log, CLASS_NAME, "login", "Equipo encontrado", loginInData);
			logonDTO.setTeamName( loginInData.getTeam() );
			logonDTO.setSuccesful( true );
			logonDTO.setTeamId( team.getIdTeam() );
			logonDTO.setCanUploadPhotos( team.isCanUploadPhotos() );
			logonDTO.setServerOnTime( cfg.getTimeUp() );
			
			applicationBean.addRestLogin( team,loginInData.getDeviceId() );
		}
		
		MyLogger.info(log, CLASS_NAME, "login", "END", "Petición de login desde terminal", logonDTO);

		return logonDTO;

	}
}
