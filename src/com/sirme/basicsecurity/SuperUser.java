package com.sirme.basicsecurity;

import java.util.ArrayList;

public abstract class SuperUser {

	private static final java.util.List<Integer> idSuperUsers = new ArrayList<Integer>();
	
	static{
		idSuperUsers.add( 1 );
	}

	public static final boolean isSU(int id){
		return idSuperUsers.contains( id );
	}

}