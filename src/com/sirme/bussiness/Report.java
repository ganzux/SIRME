package com.sirme.bussiness;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sirme.transform.ITransformator;
import com.sirme.transform.ReportTransform;

public class Report implements IBusinessObject,Cloneable{
	
	public static final int REPORT_ALBARAN	 			= 0;
	
	public static final int REPORT_EXTINTORES 			= 1;
	public static final int REPORT_BIES 				= 2;
	public static final int REPORT_ALUMBRADO_EMERGENCIA	= 3;
	public static final int REPORT_HUMOS_Y_CALOR		= 4;
	public static final int REPORT_DETECCION_INCENDIOS	= 5;
	public static final int REPORT_DETECCION_MONOXIDO	= 6;
	public static final int REPORT_ROCIADORES			= 7;
	public static final int REPORT_HIDRANTES			= 8;
	public static final int REPORT_COLUMNA_SECA			= 9;
	public static final int REPORT_EXTINCON_DE_INCENDIOS= 10;
	public static final int REPORT_CAPANAS_DE_COCINA	= 11;
	public static final int REPORT_GRUPO_DE_PRESION		= 12;

	private int idReport;
	private String nameReport;
	private String titleReport;
	private String fileReport;
	private Collection<QuestionGroup> questionGroups;
	private List<ReplyGroup> replyGroups;
	
	public Report(){
	}
	
	public Report( Report r ){
		idReport 		= r.getIdReport();
		nameReport		= r.getNameReport();
		titleReport		= r.getTitleReport();
		fileReport		= r.getFileReport();
		questionGroups	= r.getQuestionGroups();
		replyGroups		= r.getReplyGroups();
	}
	
	public Report(int reportAlbaran) {
		if ( reportAlbaran == REPORT_ALBARAN ){
			setNameReport("albaran");
			setFileReport("albaran");
		}
	}

	public int getIdReport() {
		return idReport;
	}
	public void setIdReport(int idReport) {
		this.idReport = idReport;
	}
	public String getNameReport() {
		return nameReport;
	}
	public void setNameReport(String nameReport) {
		this.nameReport = nameReport;
	}
	public Collection<QuestionGroup> getQuestionGroups() {
		return questionGroups;
	}
	public void setQuestionGroups(Collection<QuestionGroup> questionGroups) {
		this.questionGroups = questionGroups;
	}

	public String getTitleReport() {
		return titleReport;
	}
	public void setTitleReport(String titleReport) {
		this.titleReport = titleReport;
	}
	public List<ReplyGroup> getReplyGroups() {
		return replyGroups;
	}
	public String getFileReport() {
		return fileReport;
	}
	public void setFileReport(String fileReport) {
		this.fileReport = fileReport;
	}
	public void setReplyGroups(List<ReplyGroup> replyGroups) {
		this.replyGroups = replyGroups;
	}
	@Override
	public Class<? extends ITransformator> getTransformator() {
		return ReportTransform.class;
	}
	
	public int getTableSize(){
		return (replyGroups==null?0:replyGroups.size());
	}

	@JsonIgnore
	public Collection<String> getCountTypes() {
		Collection<String> str = new ArrayList<String>();

		try{
			Map<String,Integer> map = new HashMap<String,Integer>();
			for ( ReplyGroup rg: replyGroups){
				if ( map.get(rg.getQuestionGroup().getNameQuestionGroup()) == null )
					map.put( rg.getQuestionGroup().getNameQuestionGroup(), 0 );
				map.put( rg.getQuestionGroup().getNameQuestionGroup(), map.get(rg.getQuestionGroup().getNameQuestionGroup())+1 );
			}
			
			Iterator<Entry<String,Integer>> it = map.entrySet().iterator();
			while ( it.hasNext() ){
				Entry<String,Integer> entry = it.next(); 
				str.add( entry.getKey() + ": " + entry.getValue() );
			}
		} catch ( Exception e){
			
		}

		return str;
	}

	@JsonIgnore
	public Collection<ViewTotalizer> getTotalizers(){
		
		Map<Integer,ViewTotalizer> questionMap = new HashMap<Integer,ViewTotalizer>();
		// Recorremos todas las respuestas, viendo si la pregunta está en el mapa de totalizadores y añadiéndola
		if ( replyGroups != null )
			for ( ReplyGroup replyGroup: replyGroups )
				if ( replyGroup.getReplies() != null )
					for ( Reply r:replyGroup.getReplies() )
						if ( r.getQuestion() != null ){
							Question q = r.getQuestion();

							// HAY QUE ALMACENAR EL VALOR
							if ( q.getTotalize() != null && q.getTotalize() ){
								
								ViewTotalizer vt = questionMap.get( q.getIdQuestion() );

								if ( vt == null )
									vt = new ViewTotalizer( q.getQuestion() );

								Map<String,Integer> replies = vt.getMapReplies();
								
								String replyStr = r.getReply() == null?"Vacio":r.getReply();
								
								Integer replyNum = replies.get( replyStr );
								if ( replyNum == null )
									replyNum = 1;
								else
									replyNum ++ ;
								
								replies.put( replyStr , replyNum );
								
								vt.setMapReplies( replies );
								
								questionMap.put( q.getIdQuestion(), vt );
							}
						}

		Collection<ViewTotalizer> views = new ArrayList<ViewTotalizer>();
		for ( Entry<Integer,ViewTotalizer> e:questionMap.entrySet() )
			views.add( e.getValue() );

		return views;
	}
}
