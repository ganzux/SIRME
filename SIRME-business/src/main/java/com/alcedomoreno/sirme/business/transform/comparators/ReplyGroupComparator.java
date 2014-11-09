package com.alcedomoreno.sirme.business.transform.comparators;

import java.util.Comparator;

import com.alcedomoreno.sirme.business.data.ReplyGroup;

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