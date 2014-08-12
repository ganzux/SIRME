package com.sirme.export.doc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import com.sirme.bussiness.Customer;
import com.sirme.bussiness.Question;
import com.sirme.bussiness.QuestionGroup;
import com.sirme.bussiness.Reply;
import com.sirme.bussiness.ReplyGroup;
import com.sirme.bussiness.Report;
import com.sirme.bussiness.TypeWork;
import com.sirme.bussiness.Work;
import com.sirme.export.doc.types.FirextDoc;
import com.sirme.export.doc.types.TypeFactory;
import com.sirme.export.doc.types.bean.Cabecera;


public class DocExporter {

	private static DocExporter instance;
	private DocExporter(){}
	public static DocExporter getInstance(){
		if ( instance == null )
			instance = new DocExporter();
		return instance;
	}

	public Customer getCustomerFromDoc( String fileName ) throws IOException{
		Customer c = new Customer();
		c.setCodeCustomer( fileName.split(" ")[0] );
		return c;
	}

	public Work getWorkFromDoc( InputStream file, String fileName, String contentType ) throws Exception{
	
		Work work = new Work();
		work.setTypeWork( new TypeWork(1) );
		
		HWPFDocument doc = new HWPFDocument( file );
		WordExtractor wordExtract = new WordExtractor(doc);

		FirextDoc documentoFirext = TypeFactory.getType( wordExtract );

		Cabecera cabecera = documentoFirext.getCabecera( wordExtract );
		
		work.setDate( cabecera.getDate() );
		
		Calendar cal = Calendar.getInstance();
		cal.setTime( work.getDate() );
		
		work.setStatus( String.valueOf(Work.STATUS_CERRADO) );
		work.setAlbaran( Integer.valueOf(cabecera.getAlbaran()) );
		work.setYear( cal.get(Calendar.YEAR) );
		
		Collection<String[]> cuerpo = documentoFirext.getCuerpo( wordExtract );
		
		Report report = new Report();
		report.setIdReport( Report.REPORT_EXTINTORES );
		List<ReplyGroup> replyGroups = new ArrayList<ReplyGroup>();
		for ( String[] s:cuerpo ){
			QuestionGroup qg = new QuestionGroup();
			qg.setIdQuestionGroup( 1 );

			ReplyGroup replyGroup = new ReplyGroup();
			replyGroup.setQuestionGroup( qg );

			replyGroup.setNameReplyGroup( s[5] );
			
			replyGroup.addReply( generateReply(Question.QU_EXT_1_REF, s[5], replyGroup) );
			replyGroup.addReply( generateReply(Question.QU_EXT_3_UBICACION, s[0], replyGroup) );
			replyGroup.addReply( generateReply(Question.QU_EXT_4_FABRICACION, s[14], replyGroup) );


			replyGroup.setWork( work );
			
			replyGroups.add( replyGroup );
		}
		
		report.setReplyGroups(replyGroups);
		work.addReport( report );
	
		return work;
	}
	
	private Reply generateReply(int idQuestion, String replyStr, ReplyGroup replyGroup){
		Reply reply = new Reply();
		
		reply.setReply( replyStr );
		reply.setReplyGroup( replyGroup );
		
		Question question = new Question();
		question.setIdQuestion( idQuestion );
		reply.setQuestion(question);
		
		return reply;
	}
}
