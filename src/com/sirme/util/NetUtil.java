package com.sirme.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetUtil {
	
	private static Logger log = LoggerFactory.getLogger( NetUtil.class );
	
	///////////////////////////////////////////////////////////////
	//                         Constantes                        //
	///////////////////////////////////////////////////////////////
	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private static Pattern pattern;
	
	private static String HTTP_INIT		= "http://";
	private static String URL_PORT_SEP	= ":";
	private static String URL_SLASH		= "/";
    ///////////////////////////////////////////////////////////////
	//                     Fin de Constantes                     //
	///////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////
	//                         Singleton                         //
	///////////////////////////////////////////////////////////////
	
	private static NetUtil instance;
	
	private NetUtil(){
	}
	
	public static synchronized NetUtil getInstance(){
		if ( instance==null ){
			instance = new NetUtil();
			pattern = Pattern.compile( IPADDRESS_PATTERN );
		}
		return instance;
	}
	///////////////////////////////////////////////////////////////
	//                    Fin del Singleton                      //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                          Métodos                          //
	///////////////////////////////////////////////////////////////

	public String getCorrectPathWithOutProtocol(String...strings){

		StringBuilder sb = new StringBuilder();

		// Concatenación de todos los parámetros con "/" en el medio
		if ( strings!=null ){
			// Metemos el primero tal cual
			sb.append( strings[0] );

			for (int i=1;i<strings.length;i++){
				// Comienzan y terminan con Slash
				if ( sb.toString().endsWith( URL_SLASH ) && strings[i].startsWith( URL_SLASH ) )
					sb.append( strings[i].substring(1) );
				// No tienen Slash
				else if ( !sb.toString().endsWith( URL_SLASH ) && !strings[i].startsWith( URL_SLASH ) )
					sb.append( URL_SLASH ).append( strings[i] );
				// Restp de casos, ok
				else
					sb.append( strings[i] );
			}
		}

		return sb.toString();
	}
	
	public String getCorrectPathWithProtocol(String...strings){

		StringBuilder sb = new StringBuilder();
		
		if ( strings!=null && strings.length>0 ){
			if ( !strings[0].equalsIgnoreCase( HTTP_INIT ) )
				sb.append( HTTP_INIT );
			
			if ( strings[0].startsWith(URL_SLASH) )
				// Eliminación de la última "/"
				sb = new StringBuilder( sb.substring( 0, sb.length()-1 ) );
			sb.append( getCorrectPathWithOutProtocol( strings ) );
		}

		return sb.toString();
	}

	public String getHostName(String host, String port){
		StringBuilder sb = new StringBuilder();

		if ( host!=null && port!=null ){
			// Empiezan y terminan con URL_PORT_SEP
			if ( host.endsWith( URL_PORT_SEP ) && port.startsWith( URL_PORT_SEP ) )
				sb.append( host ).append( port.substring(1) );
			// No lo tiene ninguno
			else if ( !host.endsWith( URL_PORT_SEP ) && !port.startsWith( URL_PORT_SEP ) )
				sb.append( host ).append( URL_PORT_SEP ).append( port );
			// Lo tiene alguno
			else
				sb.append( host ).append( port );
		}

		return sb.toString();
	}
	
	public boolean isReachable(String host){
		 log.info( "IN",host );
	     boolean isReachable = false;
	     try {
	         Process proc = new ProcessBuilder("ping", host).start();

	         int exitValue = proc.waitFor();
	         log.debug( "Exit Value:" + exitValue );
	         if(exitValue == 0)
	             isReachable = true;
	     } catch (IOException e) {
	    	 log.error( host,e.getMessage() );
	     } catch (InterruptedException e) {
	    	 log.error( host,e.getMessage() );
	     }
	     log.info( "OUT",host,String.valueOf(isReachable) );
	     return isReachable;
	 }

	public boolean isCorrectIpv4(String ip){
		boolean correct = false;
		Matcher matcher;
		
		if ( ip!=null ){
			matcher = pattern.matcher(ip);
			correct = matcher.matches();
		}
		
		return correct;
	}
	///////////////////////////////////////////////////////////////
	//                      Fin de Métodos                       //
	///////////////////////////////////////////////////////////////

}