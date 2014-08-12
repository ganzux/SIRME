package com.sirme.bussiness;

public class Month{
	
	public static final MonthE getMonthE( String monthName ){
		if ( "ENERO".equalsIgnoreCase( monthName ) )
			return MonthE.ENERO;
		if ( "FEBRERO".equalsIgnoreCase( monthName ) )
			return MonthE.FEBRERO;
		if ( "MARZO".equalsIgnoreCase( monthName ) )
			return MonthE.MARZO;
		if ( "ABRIL".equalsIgnoreCase( monthName ) )
			return MonthE.ABRIL;
		if ( "MAYO".equalsIgnoreCase( monthName ) )
			return MonthE.MAYO;
		if ( "JUNIO".equalsIgnoreCase( monthName ) )
			return MonthE.JUNIO;
		if ( "JULIO".equalsIgnoreCase( monthName ) )
			return MonthE.JULIO;
		if ( "AGOSTO".equalsIgnoreCase( monthName ) )
			return MonthE.AGOSTO;
		if ( "SEPTIEMBRE".equalsIgnoreCase( monthName ) )
			return MonthE.SEPTIEMBRE;
		if ( "OCTUBRE".equalsIgnoreCase( monthName ) )
			return MonthE.OCTUBRE;
		if ( "NOVIEMBRE".equalsIgnoreCase( monthName ) )
			return MonthE.NOVIEMBRE;
		if ( "DICIEMBRE".equalsIgnoreCase( monthName ) )
			return MonthE.DICIEMBRE;
		return null;
	}

	public enum MonthE {
		ENERO ( 1 ),
		FEBRERO ( 2 ),
		MARZO ( 3 ),
		ABRIL ( 4 ),
		MAYO ( 5 ),
		JUNIO ( 6 ),
		JULIO ( 7 ),
		AGOSTO ( 8 ),
		SEPTIEMBRE ( 9 ),
		OCTUBRE ( 10 ),
		NOVIEMBRE ( 11 ),
		DICIEMBRE ( 12 );
		
		private final int value;
	    private MonthE(int value) {
	        this.value = value;
	    }
	
	    public int getValue() {
	        return value;
	    }
	}

}