package com.alcedomoreno.sirme.business.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.business.dto.TeamWorksListRequestDTO;
import com.alcedomoreno.sirme.business.dto.WorkDTO;
import com.alcedomoreno.sirme.business.dto.WorksByTeamDTO;
import com.alcedomoreno.sirme.business.services.TeamService;
import com.alcedomoreno.sirme.business.services.WorkService;
import com.alcedomoreno.sirme.business.util.ServiceConstants;

@Controller("teamRestController")
public class TeamsRestController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TeamsRestController.class);

	@Resource(name = ServiceConstants.TEAM_SERVICE)
	protected TeamService teamService;

	@Resource(name = ServiceConstants.WORK_SERVICE)
	protected WorkService worksService;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	WorksByTeamDTO retrieveWorksByTeam(@RequestBody TeamWorksListRequestDTO worksRequest) {

		LOGGER.info("Equipo {} pide trabajos a realizar");

		Collection<Work> works = worksService.getAll(null);
		List<WorkDTO> worksByTeam = new ArrayList<WorkDTO>();
		WorksByTeamDTO worksByTeamDTO = new WorksByTeamDTO();

		for (Work work : works) {

			if (work.getTeam().getIdTeam() == worksRequest.getTeamId()
					&& work.getStatusCode() != Work.STATUS_CERRADO) {

				LOGGER.info("Work {} obtenido", work.getIdWork());
				
				WorkDTO workDTO = new WorkDTO(work);
				worksByTeam.add(workDTO);

			}
		}

		worksByTeamDTO.setWorks(worksByTeam);
		return worksByTeamDTO;

	}

}
