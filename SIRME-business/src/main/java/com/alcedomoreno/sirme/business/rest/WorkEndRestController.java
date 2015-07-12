package com.alcedomoreno.sirme.business.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alcedomoreno.sirme.business.data.Advice;
import com.alcedomoreno.sirme.business.data.FirextFile;
import com.alcedomoreno.sirme.business.data.Photo;
import com.alcedomoreno.sirme.business.data.Question;
import com.alcedomoreno.sirme.business.data.QuestionGroup;
import com.alcedomoreno.sirme.business.data.Reply;
import com.alcedomoreno.sirme.business.data.ReplyGroup;
import com.alcedomoreno.sirme.business.data.ReplyType;
import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.data.Team;
import com.alcedomoreno.sirme.business.data.TypeWork;
import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.business.dto.CodeDTO;
import com.alcedomoreno.sirme.business.dto.QuestionDTO;
import com.alcedomoreno.sirme.business.dto.QuestionGroupDTO;
import com.alcedomoreno.sirme.business.dto.ReplyDTO;
import com.alcedomoreno.sirme.business.dto.ReplyGroupDTO;
import com.alcedomoreno.sirme.business.dto.ReplyTypeDTO;
import com.alcedomoreno.sirme.business.dto.ReportDTO;
import com.alcedomoreno.sirme.business.dto.WorkDTO;
import com.alcedomoreno.sirme.business.services.AdviceService;
import com.alcedomoreno.sirme.business.services.ApplicationsService;
import com.alcedomoreno.sirme.business.services.ConfigService;
import com.alcedomoreno.sirme.business.services.CustomerService;
import com.alcedomoreno.sirme.business.services.TeamService;
import com.alcedomoreno.sirme.business.services.UpdatedService;
import com.alcedomoreno.sirme.business.services.WorkService;
import com.alcedomoreno.sirme.business.util.FileUtil;
import com.alcedomoreno.sirme.business.util.ServiceConstants;
import com.alcedomoreno.sirme.core.util.MyLogger;

@Controller("workEndRestController")
public class WorkEndRestController {
	
	private static Logger log = LoggerFactory.getLogger( WorkEndRestController.class );
	private static final String CLASS_NAME = "WorkEndRestController";

	@Resource(name = ServiceConstants.ADVICE_SERVICE)
	protected AdviceService adviceService;
	
	@Resource
	protected ConfigService cfgService;
	
	@Resource(name = ServiceConstants.TEAM_SERVICE)
	protected TeamService teamService;
	
	@Resource(name = ServiceConstants.CUSTOMER_SERVICE)
	protected CustomerService customerService;
	
	@Resource(name = ServiceConstants.WORK_SERVICE)
	protected WorkService worksService;
	
	@Resource(name = ServiceConstants.UPDATED_SERVICE)
	protected UpdatedService updateService;
	
	@Resource(name = ServiceConstants.APPLICATION_SERVICE)
	protected ApplicationsService appService;


	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody CodeDTO closeWorkToLoad(@RequestBody WorkDTO data) {
		
		MyLogger.info(log, CLASS_NAME, "closeWorkToLoad", data);
		Work w = get(data);

		try{
			// Por seguridad, comprobamos todos los id de questionGroup y de questions
			if (w.getReports() != null) {
				for (Report r:w.getReports()) {
					if (r.getReplyGroups() != null) {
						for (ReplyGroup rg : r.getReplyGroups()) {
							if (rg.getQuestionGroup() != null) {
								if (!appService.existsQuestionGriup(rg.getQuestionGroup().getIdQuestionGroup())) {
									throw new Exception("No existe el QuestionGroup con id " + rg.getQuestionGroup().getIdQuestionGroup());
								}
								if (rg.getReplies() != null){
									for (Reply reply:rg.getReplies()){
										if (!appService.existsQuestion(reply.getQuestion().getIdQuestion())){
											throw new Exception("No existe la Question con id " + reply.getQuestion().getIdQuestion());
										}
									}
								}
							}
						}
					}
				}
			}
			
			log.info("Dato de entrada: " + data);
			
			Team team = teamService.get(data.getTeam(),data.getPassword());
			if (team != null) {
				MyLogger.info(log, CLASS_NAME, "closeWorkToLoad", "Equipo de Trabajo encontrado");
				w.setTeam(team);

				Advice advice = adviceService.get(data.getAlertId());

				if (advice != null){
					MyLogger.info(log, CLASS_NAME, "closeWorkToLoad", "Trabajo encontrado");

					w.setTypeWork(new TypeWork(1));
					w.setDate(new Date());
					w.setMemo(advice.getWorkText());

					// Guardamos la firma en el directorio de ficheros
					if (advice.getSign() != null){
						MyLogger.info(log, CLASS_NAME, "closeWorkToLoad", "Tratando Firma...", advice.getSign().getName());
						w = saveFirextFile(true, advice.getSign(), w);
						Thread.sleep(2);
						MyLogger.info(log, CLASS_NAME, "closeWorkToLoad", "Tratando Firma OK", advice.getSign().getName());
					} else {
						MyLogger.info(log, CLASS_NAME, "closeWorkToLoad", "No esiste Firma", advice);
						throw new Exception("La firma del cliente es obligatoria");
					}

					if (advice.getPictures() != null){
						for (FirextFile ff:advice.getPictures()){
							MyLogger.info(log, CLASS_NAME, "closeWorkToLoad", "Tratando Foto ", ff.getName());
							w = saveFirextFile(false, ff, w);
							Thread.sleep(2);
							MyLogger.info(log, CLASS_NAME, "closeWorkToLoad", "Tratando Foto OK", ff.getName());
						}
					} else
						MyLogger.info(log, CLASS_NAME, "closeWorkToLoad", "No hay imágenes", advice);
					
					w.setStatus(Work.STATUS_RECIBIDO);

					Work oldWork = null;
					try{
						oldWork = worksService.get(Integer.valueOf(data.getAlertId()));
					} catch (Exception e){
						throw new Exception("Ese Aviso no ha sido descargado, es nuevo");
					}
					w.setIdWork(Integer.valueOf(data.getAlertId()));
					w.setAlbaran(oldWork.getAlbaran());
					w.setDate(oldWork.getDate());
					w.setDateCreated(oldWork.getDateCreated());
					w.setDateReceived(new Date());
					w.setYear(oldWork.getYear());
					w.setCustomer(oldWork.getCustomer());
					w.setAddress(oldWork.getAddress());

					worksService.update(w);

					updateService.refreshDate();
					
				} else
					throw new Exception("Ese Trabajo no existe");
			} else
				throw new Exception("No existe ese equipo de Trabajo");

			MyLogger.info(log, CLASS_NAME, "closeWork", data.getAlertId(), "Eliminando de cola de trabajos...");
			adviceService.close( data.getAlertId(), new Advice(data) );
			MyLogger.info(log, CLASS_NAME, "closeWork", data.getAlertId(), "Eliminando de cola de trabajos OK");
		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "closeWork", e.getMessage());
			removeOldPictures( w );
			return new CodeDTO( CodeDTO.KO, e.getMessage());
		} finally{
			try {
				adviceService.close(data.getAlertId(), null);
			} catch (Exception e) {
				MyLogger.error(log, CLASS_NAME, "removeOldPicturesAndWork", "Error Cerrando trabajo", e.getMessage() );
			}
		}

		return new CodeDTO( CodeDTO.OK, String.valueOf(data.getAlertId()) );

	}
	
	private Work saveFirextFile( boolean sign, FirextFile firextFile, Work w ) throws Exception{
		String original = firextFile.getOriginalFileName();
		String extWitDot = original.substring( original.lastIndexOf(".") );
		String signFileName = String.valueOf( System.currentTimeMillis() + extWitDot );
		signFileName = cfgService.getPhotoDirectory() + signFileName;

		// FIRMA
		if ( sign ){
			w.setSignName( firextFile.getName() );
			w.setSignpath( signFileName );
		}

		// FOTO
		else{
			Collection<Photo> photos = w.getPhotos();
			if ( photos == null )
				photos = new ArrayList<Photo>();
			Photo photo = new Photo();

			photo.setPath( signFileName );
			photo.setComments( firextFile.getName() );
			photo.setWork( w );
			
			photos.add( photo );
			
			w.setPhotos( photos );
		}

		FileUtil.getInstance().saveFile(firextFile.getInputStream(), signFileName);

		MyLogger.info(log, CLASS_NAME, "closeWork", "Fichero Almacenado", signFileName);

		return w;
	}
	
	private void removeOldPictures(Work w){
		try{
			if ( FileUtil.getInstance().removeFile( w.getSignpath() ) )
				MyLogger.info(log, CLASS_NAME, "removeOldPicturesAndWork", "Firma Eliminada", w.getSignpath());
			else
				MyLogger.error(log, CLASS_NAME, "removeOldPicturesAndWork", "Error Eliminando Firma", w.getSignpath());

			if ( w.getPhotos() != null )
				for ( Photo p:w.getPhotos() ){
					if ( FileUtil.getInstance().removeFile( p.getPath() ) )
						MyLogger.info(log, CLASS_NAME, "removeOldPicturesAndWork", "Foto Eliminada", p.getPath());
					else
						MyLogger.error(log, CLASS_NAME, "removeOldPicturesAndWork", "Error Eliminando Foto", p.getPath());
				}
			
			
		} catch ( Exception e ){
			MyLogger.error(log, CLASS_NAME, "removeOldPicturesAndWork", "Error Eliminando", e.getMessage() );
		} finally{
			
		}
	}

	private Work get(WorkDTO dto){
		Work work = new Work();
		
		work.setIdWork(Integer.valueOf(dto.getAlertId()));
		
		List<Report> reports = new ArrayList<Report>();
		if (dto.getReports() != null && !dto.getReports().isEmpty() ) {
			for (ReportDTO rep : dto.getReports()) {
				Report r = get(rep);
				reports.add(r);
			}
		}
		work.setReports(reports);
		
		return work;
	}
	
	private Report get(ReportDTO dto){
		Report report = new Report();

		report.setIdReport(dto.getIdReport());
		report.setNameReport(dto.getNameReport());
		
		List<ReplyGroup> replyGroups = null;
		List<QuestionGroup> questionGroups = null;

		if ( dto.getReplyGroups() != null ){
			replyGroups = new ArrayList<ReplyGroup>();
			for (ReplyGroupDTO q : dto.getReplyGroups()) {
				ReplyGroup rg = get(q, report);
				replyGroups.add(rg);
			}
		}
		
		if ( dto.getQuestionGroups() != null ){
			questionGroups = new ArrayList<QuestionGroup>();
			for (QuestionGroupDTO q : dto.getQuestionGroups()) {
				QuestionGroup qg = get(q, report);
				questionGroups.add(qg);
			}
		}
		
		report.setReplyGroups(replyGroups);
		report.setQuestionGroups(questionGroups);
		
		return report;
	}
	
	private ReplyGroup get(ReplyGroupDTO dto, Report report){
		ReplyGroup replyGroup = new ReplyGroup();
		
		replyGroup.setIdReplyGroup(dto.getIdReplyGroup());
		replyGroup.setNameReplyGroup(dto.getNameReplyGroup());
		
		List<Reply> replies = new ArrayList<Reply>();
		
		if(dto.getQuestionGroup() != null){
			replyGroup.setQuestionGroup(get(dto.getQuestionGroup(), null));
		}
		
		if (dto.getReplies() != null && !dto.getReplies().isEmpty()) {
			for (ReplyDTO replyDto : dto.getReplies()) {
				Reply reply = get(replyDto);
				reply.setReplyGroup(replyGroup);
				replies.add(reply);
			}
		}
		
		replyGroup.setReplies(replies);

		return replyGroup;
	}
	
	private QuestionGroup get(QuestionGroupDTO dto, Report report){
		
		QuestionGroup questionGroup = new QuestionGroup();
		
		questionGroup.setIdQuestionGroup(dto.getIdQuestionGroup());
		questionGroup.setNameQuestionGroup(dto.getNameQuestionGroup());
		questionGroup.setTimes(dto.getTimes());
		questionGroup.setReport(report);

		List<Question> questions = null;
		
		if ( dto.getQuestions() != null ){
			questions = new ArrayList<Question>();
			for (QuestionDTO questionDto : dto.getQuestions()) {
				Question question = get(questionDto);
				questions.add(question);
			}
		}
		
		questionGroup.setQuestions(questions);
			
		return questionGroup;
	}
	
	private Question get(QuestionDTO dto) {
		
		Question question = new Question();

		question.setIdQuestion(dto.getIdQuestion());
		question.setQuestion(dto.getQuestion());
		question.setOrder(dto.getOrder());
		question.setReplyType(get(dto.getReplyType()));
		
		return question;
	}
	
	private ReplyType get(ReplyTypeDTO dto) {
		ReplyType replyType = new ReplyType();
		replyType.setIdReplyType(dto.getIdReplyType());
		replyType.setNameReplyType(dto.getNameReplyType());
		
		return replyType;
	}
	
	private Reply get(ReplyDTO dto) {
		Reply reply = new Reply();
		reply.setIdReply(dto.getIdReply());
		reply.setReply(dto.getReply());
		reply.setQuestion(get(dto.getQuestion()));
		return reply;
	}


}

