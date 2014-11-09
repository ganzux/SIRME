package com.alcedomoreno.sirme.web.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import com.alcedomoreno.sirme.business.data.Customer;
import com.alcedomoreno.sirme.business.data.Manager;

public class ManagerDocExtract {

	private static ManagerDocExtract instance;
	private ManagerDocExtract(){}
	public static ManagerDocExtract getInstance(){
		if ( instance == null )
			instance = new ManagerDocExtract();
		return instance;
	}

	public Manager getCustomerFromDOC( InputStream file ) throws IOException{

		Manager manager = new Manager();
		WordExtractor extractor = null;
		
		HWPFDocument document = new HWPFDocument( file );
        extractor = new WordExtractor(document);
        String fileData = extractor.getText();
        
        manager.setNameManager( fileData.split("\r\n")[0] );
        fileData = fileData.substring( fileData.indexOf("\r\n") );

        String addresess = fileData.substring( fileData.indexOf("\r\n"), fileData.toLowerCase().indexOf("tel") );
        for ( String data:addresess.split("\r\n") )
        	if ( !data.isEmpty() )
        		manager.addContact("Dirección",data);
        
        fileData = fileData.substring( fileData.toLowerCase().indexOf("tel") );

        String phones = "";
        if( fileData.toLowerCase().indexOf("mail") != -1 ){
        	phones = fileData.substring( fileData.toLowerCase().indexOf("tel"), fileData.toLowerCase().indexOf("mail")-3 );
        	fileData = fileData.substring( fileData.toLowerCase().indexOf("mail") );
        }
        else{
        	phones = fileData.substring( fileData.toLowerCase().indexOf("tel"), fileData.toLowerCase().indexOf("comunidad")-3 );
        	fileData = fileData.substring( fileData.toLowerCase().indexOf("comunidad") );
        }
        manager = getPhone( manager,phones );
        
        String mails = "";
        if ( fileData.toLowerCase().indexOf("*comunidad") != -1 ){
        	mails = fileData.substring( fileData.toLowerCase().indexOf("mail"), fileData.toLowerCase().indexOf("*comunidad")-3 );
        	fileData = fileData.substring( fileData.toLowerCase().indexOf("*comunidad") );
        }
        else if ( fileData.toLowerCase().indexOf("* comunidad") != -1 ){
        	mails = fileData.substring( fileData.toLowerCase().indexOf("mail"), fileData.toLowerCase().indexOf("* comunidad")-3 );
        	fileData = fileData.substring( fileData.toLowerCase().indexOf("* comunidad") );
        }
        boolean mail = false;
        	for ( String data:mails.split("\r\n") ){
        		if ( !data.isEmpty() ){
        			if ( data.indexOf(";") != -1 ){
        				if ( !mail ){
        					manager.setMailManager( cleanString( data.substring( data.indexOf(";") +1 ) ) );
        					mail = true;
        				} else
        					manager.addContact("Mail", cleanString( data.substring( data.indexOf(";") +1 ) ) );
        			}
        			else{
        				if ( !mail ){
    					manager.setMailManager( cleanString( data.substring( data.indexOf(";") +1 ) ) );
    					mail = true;
    				} else
        				manager.addContact("Mail", cleanString( data ) );
        			}
        		}
        	}


        String comunities = fileData;
        for ( String data:comunities.split("\r\n") ){
    		if ( data.indexOf("(") != -1 && data.indexOf(")") != -1 ){
    			String codeCustomer = getNumber( data.substring( data.indexOf("(") +1, data.indexOf(")") ) );
    			Customer c = new Customer();
    			c.setCodeCustomer( codeCustomer );
    			manager.addCustomer( c );
    		}
    	}

		return manager;
	}
	
	private Manager getPhone(Manager manager, String chain){
		
		if ( chain.trim().toLowerCase().startsWith("teléfono") || chain.trim().toLowerCase().startsWith("telefono") ){
			String tlf = "";
			if ( chain.indexOf(";") != chain.lastIndexOf(";") )
				tlf = ( cleanString( chain.substring( chain.indexOf(";")+1, chain.lastIndexOf(";")-3 )) );
			else
				tlf = ( cleanString( chain.substring( chain.indexOf(";")+1 ) ) );
			manager = countNumbers( tlf, manager );
		}
		
		String fax = chain.substring( chain.lastIndexOf(";")+1 );
		if ( !fax.trim().isEmpty() && chain.indexOf(";") != chain.lastIndexOf(";") )
			manager.addContact("Fax", cleanString( fax ) );

		return manager;
	}
	
	private String getNumber(String chain){
		String ss = "";

		for (int i = 0; i < chain.length(); i++)
		    if ( Character.isDigit( chain.charAt(i) ) )
		    	ss += chain.charAt(i);
		return ss;
	}
	
	private Manager countNumbers(String chain, Manager manager){
		boolean phone = false;
		String currentPhone = "";

		for (int i = 0; i < chain.length(); i++)
		    if ( Character.isDigit( chain.charAt(i) ) ){
		    	currentPhone += chain.charAt(i);
		    	if ( currentPhone.length() == 9 ){
		    		if ( !phone ){
		    			manager.setPhoneManager( cleanString( currentPhone ) );
		    			phone = true;
		    		}
		    		else
		    			manager.addContact("Teléfono", cleanString( currentPhone ) );
		    		currentPhone = "";
		    	}
		    }

		return manager;
	}
	
	private String cleanString(String s) {
		try{
			return new String ( s.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\r\n", "").replaceAll("\\xE2", "").replaceAll("\\x80", "").replaceAll("\\x8B", "").replaceAll("mailto:", "").replaceAll("mail:", "").trim().getBytes(), "UTF-8" ).replaceAll("[^\\x20-\\x7e]", "");
		} catch ( Exception e){
			return new String ( s.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\r\n", "").replaceAll("\\xE2", "").replaceAll("\\x80", "").replaceAll("\\x8B", "").replaceAll("mailto:", "").replaceAll("mail:", "").trim() ).replaceAll("[^\\x20-\\x7e]", "");
		}
	}
}
