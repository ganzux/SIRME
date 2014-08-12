package com.sirme.export.doc.types;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hwpf.extractor.WordExtractor;


public abstract class TypeFactory {
	
	private static Map<String,Class<?extends FirextDoc>> typeFile = new HashMap<String, Class<?extends FirextDoc>>();
	static{
		typeFile.put("CERTIFICADO  DE  REVISIÓN DE EXTINTORES DE C.I.".trim().replaceAll("  ", " ").toLowerCase(), Extintor.class);

//		typeFile.put("CERTIFICADO  DE REVISIÓN DE EQUIPOS DE MANGUERA".trim().replaceAll("  ", " ").toLowerCase(), Manguera.class);
//
//		typeFile.put("Certificado revisión sistema de detección de incendios".trim().replaceAll("  ", " ").toLowerCase(), Incendios.class);
//		typeFile.put("Certificado revisión sist. de detección de incendios".trim().replaceAll("  ", " ").toLowerCase(), Incendios.class);
//
//		typeFile.put("Certificado revisión sistema de detección de Monóxido".trim().replaceAll("  ", " ").toLowerCase(), Monoxido.class);
//		typeFile.put("Certificado revisión sist. detección de Monóxido".trim().replaceAll("  ", " ").toLowerCase(), Monoxido.class);
//
//		typeFile.put("Certificado  DE  REVISIÓN DE Alumbrado de Emergencia".trim().replaceAll("  ", " ").toLowerCase(), Alumbrado.class);

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
