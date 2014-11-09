package com.alcedomoreno.sirme.business.transform.comparators;

import java.util.Comparator;

import com.alcedomoreno.sirme.business.data.Reply;

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
