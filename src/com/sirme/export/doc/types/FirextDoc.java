package com.sirme.export.doc.types;

import java.util.Collection;

import org.apache.poi.hwpf.extractor.WordExtractor;

public interface FirextDoc {

	public com.sirme.export.doc.types.bean.Cabecera getCabecera(WordExtractor wordExtract);
	
	public Collection<String[]> getCuerpo(WordExtractor wordExtract);
}
