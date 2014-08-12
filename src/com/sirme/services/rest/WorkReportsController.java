package com.sirme.services.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sirme.bussiness.Question;
import com.sirme.bussiness.QuestionGroup;
import com.sirme.bussiness.Reply;
import com.sirme.bussiness.ReplyGroup;
import com.sirme.bussiness.ReplyType;
import com.sirme.bussiness.Report;
import com.sirme.bussiness.Work;
import com.sirme.services.IQuestionService;
import com.sirme.services.ITeamService;
import com.sirme.services.IWorkService;
import com.sirme.services.rest.dto.QuestionDTO;
import com.sirme.services.rest.dto.QuestionGroupDTO;
import com.sirme.services.rest.dto.ReplyDTO;
import com.sirme.services.rest.dto.ReplyGroupDTO;
import com.sirme.services.rest.dto.ReplyTypeDTO;
import com.sirme.services.rest.dto.ReportContainerDTO;
import com.sirme.util.SpringConstants;

@Controller("workReportsRestController")
public class WorkReportsController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TeamsRestController.class);

	@Resource(name = SpringConstants.TEAM_SERVICE)
	protected ITeamService teamService;

	@Resource(name = SpringConstants.WORK_SERVICE)
	protected IWorkService worksService;

	@Resource(name = SpringConstants.QUESTION_SERVICE)
	protected IQuestionService questionService;

	@RequestMapping(method = RequestMethod.GET, value = "/workReport/{work}/{report}")
	public @ResponseBody
	ReportContainerDTO getReport(@PathVariable String work,
			@PathVariable String report) {

		ReportContainerDTO reportContainerDTO = new ReportContainerDTO();

		Report objReport = null;

		// obtener trabajo
		Work currentWork = questionService.getWork(Integer.parseInt(work));

		// hay un reporte de ese tipo ya rellenado?
		for (Report rep : currentWork.getReports()) {
			// localizar el TIPO de reporte pedido
			if (rep.getFileReport().equals(report)) {
				objReport = rep;
			}
		}

		if (objReport != null) {
			// hay un reporte iniciado
			reportContainerDTO.setReportData(objReport);

		} else {

			// obtener schema del reporte
			for (Report rep : questionService.getAllWithQuestions()) {

				if (rep.getFileReport().equals(report)) {

					/*
					 * // datos del report ReportSchemeDTO reportDTO = new
					 * ReportSchemeDTO();
					 * reportDTO.setIdReport(rep.getIdReport());
					 * reportDTO.setNameReport(rep.getNameReport());
					 * 
					 * // obtener todos los grupos de preguntas
					 * List<QuestionGroupDTO> questionGroupDTOList = new
					 * ArrayList<QuestionGroupDTO>(); for (QuestionGroup
					 * questionGroup : rep.getQuestionGroups()) {
					 * 
					 * QuestionGroupDTO groupDTO = new QuestionGroupDTO();
					 * 
					 * groupDTO.setIdQuestionGroup(questionGroup
					 * .getIdQuestionGroup());
					 * groupDTO.setNameQuestionGroup(questionGroup
					 * .getNameQuestionGroup());
					 * groupDTO.setTimes(questionGroup.getTimes());
					 * 
					 * questionGroupDTOList.add(groupDTO);
					 * 
					 * // obtener preguntas del grupo List<QuestionDTO>
					 * questionsDTO = new ArrayList<QuestionDTO>(); for
					 * (Question question : questionGroup.getQuestions()) {
					 * 
					 * QuestionDTO questionDTO = new QuestionDTO();
					 * questionDTO.setIdQuestion(question.getIdQuestion());
					 * questionDTO.setOrder(question.getOrder());
					 * questionDTO.setQuestion(question.getQuestion());
					 * ReplyTypeDTO replyTypeDTO = new ReplyTypeDTO();
					 * replyTypeDTO.setIdReplyType(question.getReplyType()
					 * .getIdReplyType());
					 * replyTypeDTO.setNameReplyType(question
					 * .getReplyType().getNameReplyType());
					 * 
					 * questionDTO.setReplyType(replyTypeDTO);
					 * 
					 * questionsDTO.add(questionDTO); }
					 * groupDTO.setQuestions(questionsDTO);
					 * 
					 * } reportDTO.setQuestionGroups(questionGroupDTOList);
					 */

					reportContainerDTO.setReportScheme(rep);
				}

			}
		}

		/*
		 * 
		 * // obtener respuestas
		 * 
		 * for (Report rep : currentWork.getReports()) {
		 * 
		 * // localizar el TIPO de reporte pedido if
		 * (rep.getFileReport().equals(report)) {
		 * 
		 * 
		 * // ahora obtener los datos del reporte para el trabajo // determinado
		 * List<ReplyGroupDTO> repliesGroupsList = new
		 * ArrayList<ReplyGroupDTO>();
		 * 
		 * for (ReplyGroup replyGroup : rep.getReplyGroups()) {
		 * 
		 * ReplyGroupDTO dto = processReplyGroup(replyGroup);
		 * 
		 * repliesGroupsList.add(dto); }
		 * 
		 * reportContainerDTO.setReplyGroups(repliesGroupsList);
		 * 
		 * 
		 * //reportContainerDTO.
		 * 
		 * //reportContainerDTO.setReport(rep);
		 * 
		 * return rep; }
		 * 
		 * }
		 */

		return reportContainerDTO;

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/workReport/{work}/{report}")
	public @ResponseBody
	String updateReport(@PathVariable String work, @PathVariable String report,
			@RequestBody ReportContainerDTO reportDTO) throws Exception {
		try {

			LOGGER.debug("Actualizando trabajo {}", work);

			Work currentWork = questionService.getWork(Integer.parseInt(work));

			Report reportToUpdate = null;

			for (Report rep : currentWork.getReports()) {

				if (rep.getFileReport().equals(report)) {

					reportToUpdate = rep;

					// hack por problemas con los transformadores
					for (ReplyGroup replyGroup : reportDTO.getReportData()
							.getReplyGroups()) {

						replyGroup.setWork(currentWork);
					}
					/*
					 * rep.getReplyGroups().clear();
					 * 
					 * List<ReplyGroup> repliesGroupsList = new
					 * ArrayList<ReplyGroup>();
					 * 
					 * for (ReplyGroupDTO replyGroupDTO : reportDTO
					 * .getReplyGroups()) {
					 * 
					 * if (Integer.parseInt(work) == currentWork.getIdWork()) {
					 * 
					 * ReplyGroup replygroup = processReplyGroup(replyGroupDTO);
					 * 
					 * repliesGroupsList.add(replygroup);
					 * 
					 * } }
					 * 
					 * rep.setReplyGroups(repliesGroupsList);
					 */

				}

			}

			currentWork.getReports().remove(reportToUpdate);

			currentWork.getReports().add(reportDTO.getReportData());

			questionService.update(currentWork);

			worksService.update(currentWork);

			return "";

		} catch (Exception e) {
			LOGGER.error("Error actualizando trabajo ", e);
			throw e;
		}
	}
/*
	private ReplyGroupDTO processReplyGroup(ReplyGroup replyGroup) {

		ReplyGroupDTO replyGroupDTO = new ReplyGroupDTO();
		replyGroupDTO.setIdReplyGroup(replyGroup.getIdReplyGroup());
		replyGroupDTO.setNameReplyGroup(replyGroup.getNameReplyGroup());

		QuestionGroupDTO questionGroupDTO = new QuestionGroupDTO();
		replyGroupDTO.setQuestionGroup(questionGroupDTO);

		QuestionGroup questionGroup = replyGroup.getQuestionGroup();
		questionGroupDTO.setIdQuestionGroup(questionGroup.getIdQuestionGroup());
		questionGroupDTO.setNameQuestionGroup(questionGroup
				.getNameQuestionGroup());
		questionGroupDTO.setTimes(questionGroup.getTimes());

		// obtener preguntas del grupo

		List<QuestionDTO> questionsDTO = new ArrayList<QuestionDTO>();
		questionGroupDTO.setQuestions(questionsDTO);

		for (Question question : questionGroup.getQuestions()) {

			QuestionDTO questionDTO = new QuestionDTO();

			questionDTO.setIdQuestion(question.getIdQuestion());
			questionDTO.setOrder(question.getOrder());
			questionDTO.setQuestion(question.getQuestion());
			ReplyTypeDTO replyTypeDTO = new ReplyTypeDTO();
			replyTypeDTO.setIdReplyType(question.getReplyType()
					.getIdReplyType());
			replyTypeDTO.setNameReplyType(question.getReplyType()
					.getNameReplyType());

			questionDTO.setReplyType(replyTypeDTO);

			questionsDTO.add(questionDTO);
		}

		List<ReplyDTO> repliesDTOList = new ArrayList<ReplyDTO>();

		replyGroupDTO.setReplies(repliesDTOList);

		if (replyGroup.getReplies() != null) {
			for (Reply reply : replyGroup.getReplies()) {

				ReplyDTO replyDTO = new ReplyDTO();
				replyDTO.setIdReply(reply.getIdReply());

				Question question = reply.getQuestion();
				QuestionDTO questionDTO = new QuestionDTO();
				questionDTO.setIdQuestion(question.getIdQuestion());
				questionDTO.setOrder(question.getOrder());
				questionDTO.setQuestion(question.getQuestion());

				ReplyTypeDTO replyTypeDTO = new ReplyTypeDTO();
				replyTypeDTO.setIdReplyType(question.getReplyType()
						.getIdReplyType());
				replyTypeDTO.setNameReplyType(question.getReplyType()
						.getNameReplyType());
				replyDTO.setReply(reply.getReply());

				replyDTO.setQuestion(questionDTO);

				repliesDTOList.add(replyDTO);

				if (reply.getReplyGroup() != null) {
					ReplyGroupDTO child = processReplyGroup(reply
							.getReplyGroup());

					replyDTO.setReplyGroup(child);
				}

			}
		}
		return replyGroupDTO;

	}
*/
	private ReplyGroup processReplyGroup(ReplyGroupDTO replyGroupDTO) {

		ReplyGroup replyGroup = new ReplyGroup();
		replyGroup.setIdReplyGroup(replyGroupDTO.getIdReplyGroup());
		replyGroup.setNameReplyGroup(replyGroupDTO.getNameReplyGroup());

		QuestionGroup questionGroup = new QuestionGroup();
		questionGroup.setQuestions(new ArrayList<Question>());

		replyGroup.setQuestionGroup(questionGroup);
		questionGroup.setReport(new Report());

		/*
		 * 
		 * QuestionGroupDTO questionGroupDTO = replyGroupDTO.getQuestionGroup();
		 * 
		 * QuestionGroup questionGroup = new QuestionGroup();
		 * 
		 * questionGroup.setIdQuestionGroup(questionGroupDTO.getIdQuestionGroup()
		 * ); questionGroup.setNameQuestionGroup(questionGroupDTO
		 * .getNameQuestionGroup());
		 * questionGroup.setTimes(questionGroupDTO.getTimes());
		 * 
		 * 
		 * 
		 * 
		 * // obtener preguntas del grupo List<Question> questions = new
		 * ArrayList<Question>(); for (QuestionDTO questionDTO :
		 * questionGroupDTO.getQuestions()) {
		 * 
		 * Question question = new Question();
		 * question.setIdQuestion(questionDTO.getIdQuestion());
		 * question.setOrder(questionDTO.getOrder());
		 * question.setQuestion(questionDTO.getQuestion());
		 * 
		 * ReplyType replyType = new ReplyType();
		 * 
		 * replyType.setIdReplyType(questionDTO.getReplyType()
		 * .getIdReplyType());
		 * replyType.setNameReplyType(questionDTO.getReplyType()
		 * .getNameReplyType());
		 * 
		 * question.setReplyType(replyType);
		 * 
		 * questions.add(question); }
		 * 
		 * questionGroup.setQuestions(questions);
		 * 
		 * replyGroup.setQuestionGroup(questionGroup);
		 */
		List<Reply> repliesList = new ArrayList<Reply>();

		replyGroup.setReplies(repliesList);

		if (replyGroupDTO.getReplies() != null) {

			for (ReplyDTO replyDTO : replyGroupDTO.getReplies()) {

				Reply reply = new Reply();
				reply.setIdReply(replyDTO.getIdReply());

				QuestionDTO questionDTO = replyDTO.getQuestion();
				Question question = new Question();

				question.setIdQuestion(questionDTO.getIdQuestion());
				question.setOrder(questionDTO.getOrder());
				question.setQuestion(questionDTO.getQuestion());

				ReplyType replyType = new ReplyType();
				question.setReplyType(replyType);
				/*
				 * question.setReplyType(replyType);
				 * replyType.setIdReplyType(questionDTO.getReplyType()
				 * .getIdReplyType());
				 * replyType.setNameReplyType(questionDTO.getReplyType()
				 * .getNameReplyType());
				 * 
				 * reply.setQuestion(question);
				 * reply.setReply(replyDTO.getReply());
				 * 
				 * repliesList.add(reply);
				 */

//				if (replyDTO.getReplyGroup() != null) {
//					ReplyGroup child = processReplyGroup(replyDTO
//							.getReplyGroup());
//
//					reply.setReplyGroup(child);
//				}

			}
		}
		return replyGroup;

	}
}
