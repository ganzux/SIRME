package com.sirme.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionUtil {

	///////////////////////////////////////////////////////////////
	//                         Singleton                         //
	///////////////////////////////////////////////////////////////
	
	private static CollectionUtil instance;
	
	private CollectionUtil(){}
	
	public static synchronized CollectionUtil getInstance(){
		if ( instance==null )
			instance = new CollectionUtil();
		return instance;
	}
	///////////////////////////////////////////////////////////////
	//                    Fin del Singleton                      //
	///////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////
	//                          Métodos                          //
	///////////////////////////////////////////////////////////////


	// TODO: mejorar el algoritmo para hacerlo recursivo para N niveles
	public void getMapFromObjects(List<String[]> initialList, Map<String,Object> mapa){

		String[] currentElement = null;
		String currentKey = null;

		String[] nextElement = null;
		String nextKey = null;
		int i=0,start,end;

		while ( i < initialList.size() ) {
			start = i;

			currentElement = initialList.get( i++ );
			currentKey = currentElement[0];

			if ( i < initialList.size() ){
				nextElement = initialList.get( i );
				nextKey = nextElement[0];
			}

			Map<String,Object> insideMap = new HashMap<String,Object>(); 
			mapa.put(currentKey, insideMap);

			while ( currentKey.equals(nextKey) && i < initialList.size() ) {
				currentElement = initialList.get( i++ );
				currentKey = currentElement[0];

				if ( i < initialList.size() ){
					nextElement = initialList.get( i );
					nextKey = nextElement[0];
				}
	
			}
			end = i;

			List<String[]> listOfCurrentElements = new ArrayList<String[]>();
			for (int j=start;j<end;j++)
				listOfCurrentElements.add( (initialList.get(j)) );
	
			if ( listOfCurrentElements.get(0).length>1 )
				getMapFromObjects(listOfCurrentElements,insideMap);
			else
				insideMap.put(listOfCurrentElements.get(0)[0], null);
		}
	}

	public List<String> getOrderedNumbers(String[] numbers){

		int[] intsOrdered = new int[numbers.length];

		for (int i = 0; i < numbers.length; i++)
		    try {
		        intsOrdered[i] = Integer.parseInt( numbers[i] );
		    } catch (NumberFormatException nfe) {};
		Arrays.sort( intsOrdered );
		
		String[] stringOrderedAsInt = new String[ intsOrdered.length ];
		for (int i = 0; i < intsOrdered.length; i++)
			stringOrderedAsInt[i] = String.valueOf( intsOrdered[i] );
		
		return Arrays.asList( stringOrderedAsInt );
	}
	
	///////////////////////////////////////////////////////////////
	//                      Fin de Métodos                       //
	///////////////////////////////////////////////////////////////
}
