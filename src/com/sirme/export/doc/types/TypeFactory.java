package com.sirme.export.doc.types;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hwpf.extractor.WordExtractor;


public abstract class TypeFactory {
	
	private static Map<String,Class<?extends FirextDoc>> typeFile = new HashMap<String, Class<?extends FirextDoc>>();
	static{
		typeFile.put("CERTIFICADO  DE  REVISI�N DE EXTINTORES DE C.I.".trim().replaceAll("  ", " ").toLowerCase(), Extintor.class);

//		typeFile.put("CERTIFICADO  DE REVISI�N DE EQUIPOS DE MANGUERA".trim().replaceAll("  ", " ").toLowerCase(), Manguera.class);
//
//		typeFile.put("Certificado revisi�n sistema de detecci�n de incendios".trim().replaceAll("  ", " ").toLowerCase(), Incendios.class);
//		typeFile.put("Certificado revisi�n sist. de detecci�n de incendios".trim().replaceAll("  ", " ").toLowerCase(), Incendios.class);
//
//		typeFile.put("Certificado revisi�n sistema de detecci�n de Mon�xido".trim().replaceAll("  ", " ").toLowerCase(), Monoxido.class);
//		typeFile.put("Certificado revisi�n sist. detecci�n de Mon�xido".trim().replaceAll("  ", " ").toLowerCase(), Monoxido.class);
//
//		typeFile.put("Certificado  DE  REVISI�N DE Alumbrado de Emergencia".trim().replaceAll("  ", " ").toLowerCase(), Alumbrado.class);

	}

	public static FirextDoc getType(WordExtractor wordExtract){
		Class<?extends FirextDoc> type = null;
		String [] dataArray = wordExtract.getParagraphText();
		
		for ( int i=13;i<22;i++){
			String name = dataArray[i];
			if ( name!=null ){
				
				name = name.trim().replaceAll("  ", " ").toLowerCase();
				
				if ( typeFile.get( name )!=null ){
					
					 type = typeFile.get( name );
					 
					 FirextDoc instance;
					try {
						instance = (FirextDoc)type.newInstance();
						return instance;
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
				}
			}
			
		}

		return null;
	}

}
