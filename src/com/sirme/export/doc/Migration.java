package com.sirme.export.doc;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import com.sirme.export.doc.types.FirextDoc;
import com.sirme.export.doc.types.TypeFactory;
import com.sirme.export.doc.types.bean.Cabecera;

public class Migration {

	public static void main(String[] args) {
	    Collection<File> all = new ArrayList<File>();
	    addTree( new File("D:\\fm") ,all);
	    
	    for ( File docFile:all ){
	    	try{
	    		System.out.println("Tratando el fichero " + docFile.getAbsolutePath() + "\\" + docFile.getName() );

	    		FileInputStream finStream=new FileInputStream(docFile.getAbsolutePath());
	    		HWPFDocument doc=new HWPFDocument(finStream);
	    		WordExtractor wordExtract=new WordExtractor(doc);
	    		
	    		
	    		FirextDoc documentoFirext = TypeFactory.getType( wordExtract );
	 
	    		Cabecera cabecera = documentoFirext.getCabecera( wordExtract );
	    		System.out.println( cabecera );
	    		
	    		finStream.close();

	    		System.out.println("\t\t…XITO");
	    	} catch (Exception e){
	    		System.out.println("\t\tERROR");
	    	}
	    }

	}
	

	
	
	
	
	
	/** RECORRER ¡RBOL DE DIRECTORIOS PARA SACAR LOS DOC **/
	private static void addTree(File file, Collection<File> all) {
	    File[] children = file.listFiles();
	    if (children != null) {
	        for (File child : children) {
	        	if ( "doc".equalsIgnoreCase( getExtension(child)) )
	        		all.add(child);
	            addTree(child, all);
	        }
	    }
	}
	
	private static String getExtension(File file){
		String st = "";

		try{
			st = file.getName().substring( file.getName().lastIndexOf(".")+1 );
		} catch (Exception e){}

		return st;
	}
	/** FIN DE RECORRER ¡RBOL DE DIRECTORIOS PARA SACAR LOS DOC **/
}
