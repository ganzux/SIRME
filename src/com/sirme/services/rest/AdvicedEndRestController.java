package com.sirme.services.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import com.sirme.bussiness.FirextFile;
import com.sirme.bussiness.Photo;
import com.sirme.bussiness.Team;
import com.sirme.bussiness.TypeWork;
import com.sirme.bussiness.Work;
import com.sirme.services.IAdviceService;
import com.sirme.services.ICustomerService;
import com.sirme.services.ITeamService;
import com.sirme.services.IUpdatedService;
import com.sirme.services.IWorkService;
import com.sirme.services.rest.dto.AdviceDTO;
import com.sirme.services.rest.dto.CodeDTO;
import com.sirme.util.ConfigService;
import com.sirme.util.FileUtil;
import com.sirme.util.MyLogger;
import com.sirme.util.SpringConstants;

@Controller("adviceEndRestController")
public class AdvicedEndRestController {
	
	private static Logger log = LoggerFactory.getLogger( AdvicedEndRestController.class );
	private static final String CLASS_NAME = "AdvicedEndRestController";

	@Resource(name = SpringConstants.ADVICE_SERVICE)
	protected IAdviceService adviceService;
	
	@Resource
	protected ConfigService cfgService;
	
	@Resource(name = SpringConstants.TEAM_SERVICE)
	protected ITeamService teamService;
	
	@Resource(name = SpringConstants.CUSTOMER_SERVICE)
	protected ICustomerService customerService;
	
	@Resource(name = SpringConstants.WORK_SERVICE)
	protected IWorkService worksService;
	
	@Resource(name = SpringConstants.UPDATED_SERVICE)
	protected IUpdatedService updateService;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody CodeDTO closeWork(@RequestBody AdviceDTO data) {
		
		MyLogger.info(log, CLASS_NAME, "closeWork", data);
		Work w = new Work();

		try{
			Team team = teamService.get( data.getTeam(),data.getPassword() );
			if ( team != null  ) {
				MyLogger.info(log, CLASS_NAME, "closeWork", "Equipo de Trabajo encontrado");
				w.setTeam( team );

				int idAddress = 0;
				try{
					idAddress = Integer.parseInt( data.getIdClient() );
				} catch( Exception e ){
					throw new Exception("El Id tiene que ser num�rico");
				}
				Address address =  customerService.getAddressById( idAddress );
				
				if ( address != null ){
					MyLogger.info(log, CLASS_NAME, "closeWork", "Cliente con direcci�n encontrados");
					w.setAddress( address );
					w.setCustomer( address.getCustomer() );
					
					Advice advice = adviceService.getAdvice( data.getAlertId() );

					if ( advice != null ){
						MyLogger.info(log, CLASS_NAME, "closeWork", "Aviso encontrado");

						w.setTypeWork( new TypeWork(2) );
						w.setDateCreated( new Date() );
						w.setDate( new Date() );
						w.setMemo( advice.getWorkText() );

						// Guardamos la firma en el directorio de ficheros
						if ( advice.getSign() != null ){
							MyLogger.info(log, CLASS_NAME, "closeWork", "Tratando Firma...", advice.getSign().getName());
							w = saveFirextFile( true, advice.getSign(), w );
							Thread.sleep( 2 );
							MyLogger.info(log, CLASS_NAME, "closeWork", "Tratando Firma OK", advice.getSign().getName());
						} else{
							MyLogger.info(log, CLASS_NAME, "closeWork", "No esiste Firma", advice);
							throw new Exception("La firma del cliente es obligatoria");
						}

						if ( advice.getPictures() != null ){
							for ( FirextFile ff:advice.getPictures() ){
								MyLogger.info(log, CLASS_NAME, "closeWork", "Tratando Foto ", ff.getName());
								w = saveFirextFile( false, ff, w );
								Thread.sleep( 2 );
								MyLogger.info(log, CLASS_NAME, "closeWork", "Tratando Foto OK", ff.getName());
							}
						} else
							MyLogger.info(log, CLASS_NAME, "closeWork", "No hay im�genes", advice);
						
						w.setStatus( Work.STATUS_RECIBIDO );
						
						if ( data.getDownloaded() ){
							Work oldWork = null;
							try{
								oldWork = worksService.get( Integer.valueOf( data.getAlertId() ) );
							} catch ( Exception e ){
								throw new Exception("Ese Aviso no ha sido descargado, es nuevo");
							}
							w.setIdWork( Integer.valueOf( data.getAlertId() ) );
							w.setAlbaran( oldWork.getAlbaran() );
							w.setDate( oldWork.getDate() );
							w.setDateCreated( oldWork.getDateCreated() );
							w.setYear( oldWork.getYear() );
							worksService.update( w );
						} else
							worksService.save( w );

						updateService.refreshDate();
						
					} else
						throw new Exception("Ese Aviso no existe");

				} else
					throw new Exception("No se ha encontrado ese Cliente con esa direcci�n");

			} else
				throw new Exception("No existe ese equipo de Trabajo");

			MyLogger.info(log, CLASS_NAME, "closeWork", data.getAlertId(), "Eliminando de cola de trabajos...");
			adviceService.closeAdvice( data.getAlertId(), new Advice(data) );
			MyLogger.info(log, CLASS_NAME, "closeWork", data.getAlertId(), "Eliminando de cola de trabajos OK");
		} catch (Exception e){
			MyLogger.error(log, CLASS_NAME, "closeWork", e.getMessage());
			removeOldPictures( w );
			return new CodeDTO( CodeDTO.KO, e.getMessage());
		} finally{
			try {
				adviceService.closeAdvice(data.getAlertId(), null);
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
}
