package com.sirme.transform.comparators;

import java.util.Comparator;

import com.sirme.bussiness.Reply;

public class QuestionComparator implements Comparator<Reply>{

	@Override
	public int compare(Reply r1, Reply r2) {
		try{
			return r1.getQuestion().getOrder().compareTo( r2.getQuestion().getOrder() );
		} catch ( Exception e){
			return 0;
		}
	}

}
