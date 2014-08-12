package com.sirme.transform.comparators;

import java.util.Comparator;

import com.sirme.bussiness.ReplyGroup;

public class ReplyGroupComparator implements Comparator<ReplyGroup>{

	@Override
	public int compare(ReplyGroup c1, ReplyGroup c2) {
		try{
			return Integer.valueOf( c1.getNameReplyGroup() ).compareTo( Integer.valueOf(c2.getNameReplyGroup() ) );
		} catch ( Exception e){
			return 0;
		}
	}

}