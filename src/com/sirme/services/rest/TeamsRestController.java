package com.sirme.services.rest;

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

import com.sirme.bussiness.Work;
import com.sirme.services.ITeamService;
import com.sirme.services.IWorkService;
import com.sirme.services.rest.dto.TeamWorksListRequestDTO;
import com.sirme.services.rest.dto.WorkDTO;
import com.sirme.services.rest.dto.WorksByTeamDTO;
import com.sirme.util.SpringConstants;

@Controller("teamRestController")
public class TeamsRestController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TeamsRestController.class);

	@Resource(name = SpringConstants.TEAM_SERVICE)
	protected ITeamService teamService;

	@Resource(name = SpringConstants.WORK_SERVICE)
	protected IWorkService worksService;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	WorksByTeamDTO retrieveWorksByTeam(@RequestBody TeamWorksListRequestDTO worksRequest) {

		LOGGER.info("Equipo {} pide trabajos a realizar");

		Collection<Work> works = worksService.getAll();
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
