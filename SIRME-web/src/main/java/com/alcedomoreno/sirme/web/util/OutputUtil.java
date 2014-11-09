package com.alcedomoreno.sirme.web.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputUtil {
	private static Logger log = LoggerFactory.getLogger( DateTimeUtil.class );
	///////////////////////////////////////////////////////////////
	//                         Singleton                         //
	///////////////////////////////////////////////////////////////
	
	private static OutputUtil instance;
	
	private OutputUtil(){
		log.info("New Instance");
	}
	
	public static synchronized OutputUtil getInstance(){
		if ( instance==null )
			instance = new OutputUtil();
		return instance;
	}
	///////////////////////////////////////////////////////////////
	//                    Fin del Singleton                      //
	///////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////
	//                          Métodos                          //
	///////////////////////////////////////////////////////////////

	public static void getPDFFile(HttpServletResponse response, ByteArrayOutputStream data, String fileName, boolean attach) {
	   getFile(response, data, fileName, attach, "pdf");
	}
	
	public static void getZipFile(HttpServletResponse response, ByteArrayOutputStream data, String fileName, boolean attach) {
	   getFile(response, data, fileName, attach, "zip");
	}

	private static void getFile(HttpServletResponse response, ByteArrayOutputStream data, String fileName, boolean attach, String fileType) {

		try{
		    String fileExtension = "";
		    String mimeType = "";
		    String inlineOrDownload = (attach?"attachment":"inline");

		    if ( "excel".equalsIgnoreCase(fileType) ){
				fileExtension = "xls";
				mimeType = "application/vnd.ms-excel";
		    } else if ( "pdf".equalsIgnoreCase(fileType) ){
				fileExtension = "pdf";
				mimeType = "application/pdf";
		    } else if ( "zip".equalsIgnoreCase(fileType) ){
				fileExtension = "zip";
				mimeType = "application/zip";
		    }

		    response.setContentType( mimeType );
		    response.setHeader("Content-Disposition", inlineOrDownload+"; filename=\""+fileName+"."+fileExtension+"\"");
		    
		    OutputStream out1 = response.getOutputStream();
		    
	            if ( data!=null && out1!=null)
	                data.writeTo( out1 );
	            
	            out1.flush();
		    out1.close();

		    if (!FacesContext.getCurrentInstance().getResponseComplete())
		    	FacesContext.getCurrentInstance().responseComplete();

		} catch (Exception e){
			
		}
    }

	///////////////////////////////////////////////////////////////
	//                      Fin de Métodos                       //
	///////////////////////////////////////////////////////////////
}