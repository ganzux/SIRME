package com.alcedomoreno.sirme.web.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.alcedomoreno.sirme.business.data.Report;
import com.alcedomoreno.sirme.business.data.Work;
import com.alcedomoreno.sirme.web.jasper.GenerateReport;

public class ZipUtil {
	///////////////////////////////////////////////////////////////
	//                         Singleton                         //
	///////////////////////////////////////////////////////////////
	
	private static ZipUtil instance;

	private ZipUtil() {
	}

	public static synchronized ZipUtil getInstance() {
		if (instance == null)
			instance = new ZipUtil();

		return instance;
	}
	///////////////////////////////////////////////////////////////
	//                    Fin del Singleton                      //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                          Metodos                          //
	///////////////////////////////////////////////////////////////

	public ByteArrayOutputStream fillZip( Work work ) throws Exception{
		Map<String,ByteArrayOutputStream> map = getPDFs( work );
		return createZipByteArray( map );
	}
		
		
	private Map<String,ByteArrayOutputStream> getPDFs( Work work ) throws Exception{
		
		Map<String,ByteArrayOutputStream> pdfs = new HashMap<String,ByteArrayOutputStream>();

		if ( work != null ){
			ByteArrayOutputStream albaran = GenerateReport.getInstance().fillPDF( new Report(Report.REPORT_ALBARAN), work );
			pdfs.put("albaran.pdf", albaran);
			if ( work.getReports() != null )
				for ( Report report : work.getReports() ){
					ByteArrayOutputStream pdf = GenerateReport.getInstance().fillPDF( report, work );
					pdfs.put(report.getNameReport()+".pdf", pdf);
				}
		}

		return pdfs;
	}

	private ByteArrayOutputStream createZipByteArray(Map<String,ByteArrayOutputStream> map) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream( byteArrayOutputStream );

        try {
        	 Iterator<Entry<String, ByteArrayOutputStream>> it = map.entrySet().iterator();
        	    while ( it.hasNext() ) {
        	        Entry<String, ByteArrayOutputStream> pairs = (Entry<String, ByteArrayOutputStream>) it.next();

        	        ZipEntry zipEntry = new ZipEntry( pairs.getKey() );
                    zipOutputStream.putNextEntry( zipEntry );
                    zipOutputStream.write( pairs.getValue().toByteArray() );
                    zipOutputStream.closeEntry();

        	        it.remove();
        	    }

        } finally {
            zipOutputStream.close();
        }

        return byteArrayOutputStream;
    }

	///////////////////////////////////////////////////////////////
	//                      Fin de Metodos                       //
	///////////////////////////////////////////////////////////////
}
