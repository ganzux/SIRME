package com.sirme.util;

import org.slf4j.Logger;

public class MyLogger {
	
	public static void debug( Logger log, String className, String methodName, Object... params ) {
		log.debug( getString( className, methodName, params ) );
	}
	
	public static void info( Logger log, String className, String methodName, Object... params ) {
		log.info( getString( className, methodName, params ) );
	}

	public static void warn( Logger log, String className, String methodName, Object... params ) {
		log.warn( getString( className, methodName, params ) );
	}

	public static void error( Logger log, String className, String methodName, Object... params ) {
		log.error( getString( className, methodName, params ) );
	}

	private static String getString( String className, String methodName, Object... params ) {
		StringBuffer sb = new StringBuffer();
		
		sb.append( " " );
		sb.append( "[" );
		sb.append( className + "." + methodName );
		sb.append( "] " );
		
		if ( null != params && params.length>0 )
			for ( Object param:params )
				sb.append( param ).append( " | " );
		
		// Se retorna la cadena menos los 2 últimos caracteres (espacio en blanco y barra separadora)
		return sb.substring( 0, sb.length()-2 );
	}
}