package com.sirme.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.CharacterIterator;
import java.text.Normalizer;
import java.text.StringCharacterIterator;
import java.util.regex.Pattern;


public class StringUtil {

	///////////////////////////////////////////////////////////////
	//                         Constantes                        //
	///////////////////////////////////////////////////////////////
	private static final String serverInit="http://";
	private static final String slashPath="/";
	///////////////////////////////////////////////////////////////
	//                     Fin de Constantes                     //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                         Singleton                         //
	///////////////////////////////////////////////////////////////

	private static StringUtil instance;

	private StringUtil(){
	}

	public static synchronized StringUtil getInstance(){
		if ( instance==null )
			instance = new StringUtil();
		return instance;
	}
	///////////////////////////////////////////////////////////////
	//                    Fin del Singleton                      //
	///////////////////////////////////////////////////////////////
	
	
	///////////////////////////////////////////////////////////////
	//                          Métodos                          //
	///////////////////////////////////////////////////////////////
	
	public String getPathWithProtocol(String... data){
		String[] data2 = null;
		if ( data!=null && data.length>0 && !data[0].toLowerCase().startsWith(serverInit) ){
			int j=0;
			for (String d:data)
				if (d!=null)
					j++;

			data2 = new String[j];
			data2[0] = serverInit + data[0];
			
			int i=1;
			for (String d:data)
				if ( d!=null && !d.equalsIgnoreCase(data[0]) ){
					data2[i] = d;
					i++;
				}
				
		}
		return getPathOfStrings(data2);
	}
	
	public String getPathOfStrings(String... data){
		return getStringWithSeparator(slashPath,data);
	}

	public String getStringWithSeparator(String separator, String... data){
		if (data != null && data.length==2){
			StringBuilder sb = new StringBuilder();

			sb.append( data[0] );
			
			if ( data[0].endsWith(separator) )
				sb = new StringBuilder( sb.substring(0,sb.length()-1) );
			
			if ( data[1]!=null && !data[1].startsWith(separator) )
				sb.append( separator );
			
			sb.append( data[1] );

			return sb.toString();
		} else if  (data.length!=1) {
			String[] data2 = new String[ data.length-1 ];
			for (int i=0;i<data.length-1;i++)
				data2[i] = data[i+1];
			return getPathOfStrings( data[0],getPathOfStrings(data2) );	
		} else
			return String.valueOf( data[0] );
			
	}

	public String removeDoubleWhiteSpaces(String string){
		String returned = null;
		if ( string!=null )
			returned = string.replaceAll("\\s+", " ");
		return returned;
	}

	public String replaceWildCardWithTexts(String chain, char wildCard, String... strings ){
		StringBuilder sb = new StringBuilder();
		String[] chains = strings;
		
		try{
			int i=0;
			CharacterIterator it = new StringCharacterIterator(chain);
			for(char ch = it.first(); ch != CharacterIterator.DONE; ch = it.next()) {
			       if( ch!=wildCard ) 
			    	   sb.append( ch );
			       else{
			    	   sb.append( chains[i++] );
			       }
			}
		} catch (Exception e){
			sb = new StringBuilder( chain );
		}
		
		return sb.toString();
	}

	/**
	 * removeAccent --> Función que elimina los acentos y los caracteres especiales de una cadena de texto.
	 *  @param input
	 *  @return cadena de texto sin acentos ni caracteres especiales
	 */	
	public static String removeAccent(String input) {
		
	    // Descomposición canónica
	    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

	    // Nos quedamos únicamente con los caracteres ASCII eliminando las tildes del String
	    Pattern pattern = Pattern.compile("\\P{ASCII}");

	    return pattern.matcher(normalized).replaceAll("");
	 
	}
	
	public static String getMD5(String pass1) throws Exception{

		MessageDigest messageDigest = MessageDigest.getInstance("MD5");  
		messageDigest.update( pass1.getBytes(),0, pass1.length() );
		String hashedPass = new BigInteger(1,messageDigest.digest()).toString(16);  
		if (hashedPass.length() < 32) {
		   hashedPass = "0" + hashedPass; 
		}
		return hashedPass;
	}

	///////////////////////////////////////////////////////////////
	//                      Fin de Métodos                       //
	///////////////////////////////////////////////////////////////

}
