package com.sirme.export.doc.types;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.poi.hwpf.extractor.WordExtractor;

import com.sirme.export.doc.types.bean.Cabecera;

public class Extintor implements FirextDoc{

	@Override
	public Cabecera getCabecera(WordExtractor wordExtract) {
		String [] dataArray = wordExtract.getParagraphText();

		Cabecera cabecera = new Cabecera();
		
		cabecera.setCliente( getNextValueFromFirstTag( dataArray, "Cliente") );
		cabecera.setDireccion( getNextValueFromFirstTag( dataArray, "Dirección") );
		cabecera.setFechaRevision( getNextValueFromFirstTag( dataArray, "Fecha de revisión") );
		cabecera.setAlbaran( getNextValueFromFirstTag( dataArray, "Nº Albaran") );
		cabecera.setRevisadoPor( getNextValueFromFirstTag( dataArray, "Revisado por") );

		return cabecera;
		
	}

	@Override
	public Collection<String[]> getCuerpo(WordExtractor wordExtract) {
		
		Collection<String[]> filas = new ArrayList<String[]>();
		String [] dataArray = wordExtract.getParagraphText();

		int j = 62;
		boolean repeat = true;

		do{
			String[] fila = new String[18];
			boolean exists = false;
			
			for ( int i = 0;i < 18 && repeat; i++ ){
				String value = dataArray[ j ];
				fila[ i ] = value;
				j++;
				
				if ( value != null && !value.trim().isEmpty() )
					exists = true;
				
				if ( value.toLowerCase().contains("certifica que han sido realizadas las pruebas oportunas para la emisión del presente informe en el que") )
					repeat = false;
			}

			if ( exists && repeat )
				filas.add( fila );

		} while ( repeat );
		
		return filas;
	}
	
	
	private String getNextValueFromFirstTag( String [] dataArray, String tag ){
		
		int i = 0;
		boolean find = false;
		
		do{
			if ( dataArray[ i ] != null && dataArray[ i ].toLowerCase().contains( tag.toLowerCase() ) ){
				find = true;
				return dataArray[ i + 1 ].trim();
			}
			i++;
		} while ( ! find );
		
		return null;
	}

}
