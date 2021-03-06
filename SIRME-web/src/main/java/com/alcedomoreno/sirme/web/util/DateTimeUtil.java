package com.alcedomoreno.sirme.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeUtil {

	private static Logger log = LoggerFactory.getLogger( DateTimeUtil.class );
	///////////////////////////////////////////////////////////////
	//                         Singleton                         //
	///////////////////////////////////////////////////////////////
	
	private static DateTimeUtil instance;
	
	private DateTimeUtil(){
		log.info("New Instance");
	}
	
	public static synchronized DateTimeUtil getInstance(){
		if ( instance==null )
			instance = new DateTimeUtil();
		return instance;
	}
	///////////////////////////////////////////////////////////////
	//                    Fin del Singleton                      //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                          Metodos                          //
	///////////////////////////////////////////////////////////////
	
	public boolean isCorrectTime(String time){
		boolean correct = false;
		log.debug("IN|" + time);
		try{
			String[] hms = time.split(":");
			@SuppressWarnings("unused")
			int h = Integer.valueOf( hms[0] );
			int m = Integer.valueOf( hms[1] );
			int s = Integer.valueOf( hms[2] );
			if ( m<60 && s<60 )
				correct = true;
		} catch(Exception e){
			correct = false;
		}
		log.debug("OUT|" + correct);
		return correct;
	}
	
	public String getCurrentDate(String patternString){
		return formatDate( new Date(),patternString);
	}
	
	public long convertTimeHMSToSeconds(String timeHMS){
		long seconds = 0;
		
		try{
			String[] h1=timeHMS.split(":");  

			int hour=Integer.parseInt(h1[0]);  
			int minute=Integer.parseInt(h1[1]);  
			int second=Integer.parseInt(h1[2]);  

			seconds = second + (60 * minute) + (3600 * hour);  
		} catch( Exception e){}

		return seconds;
	}
	
	public String convertMiliSecsToParsedHHMMSS(long millis){
		String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
	            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
	            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	    return hms;
	}
	
	public String convertMiliSecsToParsed2MSS(long millis){
		String ms = String.format("%02d:%02d", 
	            TimeUnit.MILLISECONDS.toMinutes(millis),
	            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	    return ms;
	}
	
	public String convertMiliSecsToParsed3MSS(long millis){
		String ms = String.format("%03d:%02d", 
	            TimeUnit.MILLISECONDS.toMinutes(millis),
	            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	    return ms;
	}
	
	public String formatDate(Date date, String pattern){
		log.debug("IN|" + pattern);
		String out = "";
		try{
			if (pattern!=null && !pattern.trim().isEmpty()){
				DateFormat dateFormat = new SimpleDateFormat(pattern);
				out = dateFormat.format(date);
			}
		} catch(Exception e){}
		log.debug("OUT|" + out);
		return out;
	}
	
	public Date getDate(String dateStr){
		Date date = null;

		try {
			String splitType = null;
			if (dateStr.contains(":")){
				splitType = ":";
			} else if (dateStr.contains("-")){
				splitType = "-";
			} else if (dateStr.contains("/")){
				splitType = "/";
			} else if (dateStr.contains(" ")){
				splitType = " ";
			}
			else if (dateStr.contains(".")){
				splitType = "\\.";
			}
	
			String[] splitted = dateStr.split(splitType);
			String day = splitted[0];
			String month = splitted[1];
			String year = splitted[2];

			String dayPattern = "";
			for (int i = 0;i < day.length();i++){
				dayPattern += "d";
			}
			String monthPattern = "";
			for (int i = 0;i < month.length();i++){
				monthPattern += "M";
			}
			String yearPattern = "";
			for (int i = 0;i < year.length();i++){
				yearPattern += "y";
			}

			String myFormattedDate = day + "-" + month + "-" + year;

			DateFormat dateFormat = new SimpleDateFormat(dayPattern + "-" + monthPattern + "-" + yearPattern);

			date = dateFormat.parse(myFormattedDate);
		} catch (ParseException e) {
			log.error("Fecha no reconocida: " + dateStr);
		}

		return date;
		
	}
	///////////////////////////////////////////////////////////////
	//                      Fin de Metodos                       //
	///////////////////////////////////////////////////////////////
}
