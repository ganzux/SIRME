package com.sirme.transform.comparators;

import java.util.Comparator;

import com.sirme.bussiness.Reply;

public class ReplyComparator implements Comparator<Reply>{

	@Override
	public int compare(Reply r1, Reply r2) {
		try{
			return r1.getQuestion().getQuestion().compareTo( r2.getQuestion().getQuestion() );
		} catch ( Exception e){
			return 0;
		}
	}

}
