package com.sirme.transform.comparators;

import java.util.Comparator;

import com.sirme.data.ReplyGroupData;

public class ReplyGroupDataComparator implements Comparator<ReplyGroupData>{

	@Override
	public int compare(ReplyGroupData c1, ReplyGroupData c2) {
		try{
			return Integer.valueOf( c1.getNameReplyGroup() ).compareTo( Integer.valueOf(c2.getNameReplyGroup() ) );
		} catch ( Exception e){
			return 0;
		}
	}

}