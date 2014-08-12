package com.sirme.bussiness;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ViewTotalizer {

	private String nameQuestion;
	private Map<String,Integer> mapReplies;

	public String getNameQuestion() {
		return nameQuestion;
	}
	public void setNameQuestion(String nameQuestion) {
		this.nameQuestion = nameQuestion;
	}
	public Map<String, Integer> getMapReplies() {
		return mapReplies;
	}
	public void setMapReplies(Map<String, Integer> mapReplies) {
		this.mapReplies = mapReplies;
	}
	public ViewTotalizer(String nameQuestion) {
		super();
		this.nameQuestion = nameQuestion;
		this.mapReplies = new HashMap<String,Integer>();
	}
	
	public Collection<String> getReplies(){
		Collection<String> replies = new ArrayList<String>();
		
		for ( Entry<String, Integer> e: mapReplies.entrySet() )
			replies.add( e.getKey() + " - " + e.getValue() );
		
		return replies;
	}
	
}
