package com.alcedomoreno.sirme.business.validator;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Validator {
	
	public static final int TYPE_TEXT = 1;		// TEXTO
	public static final int TYPE_NUMBER = 2;	// NUMERO ENTERO >= 0
	public static final int TYPE_DATE = 3;		// FECHA dd/mm/yyyy
	public static final int TYPE_SN = 4;		// Sí | No
	public static final int TYPE_BM = 5;		// Bien | Mal
	public static final int TYPE_DECIMAL = 6;	// Numero Decimal
	public static final int TYPE_MONTHYEAR = 7;	// mm/yyyy
	public static final int TYPE_YEAR = 8;		// yyyy
	public static final int TYPE_AUTOINC = 9;	// Auntoincrementable con respecto al reporte
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("d/M/y");
	
	private static final Map<String,String> snValues;
	private static final Map<String,String> bmValues;
	
	static{
		snValues = new HashMap<String,String>();
		bmValues = new HashMap<String,String>();
		
		snValues.put("sí", "sí");
		snValues.put("si", "si");
		snValues.put("no", "no");
		bmValues.put("bien", "bien");
		bmValues.put("mal", "mal");
	}
	
	private Validator(){}
	private static Validator instance;
	
	public static Validator getInstance(){
		if ( instance == null )
			instance = new Validator();
		return instance;
	}
	
	public String validate( String chain, int type ) throws ValidationException{
		
		String convertedChain = chain;
		
		if ( chain != null && !chain.trim().isEmpty() ){
			switch ( type ){
				case TYPE_TEXT:
				break;
				
				case TYPE_NUMBER:
				case TYPE_AUTOINC:
					try{
						int n = Integer.valueOf( chain );
						if ( n < 0 )
							throw new ValidationException(chain + " es menor que 0");
					} catch ( Exception e ){
						throw new ValidationException(chain + " no es un numero entero valido");
					}
				break;
				
				case TYPE_DATE:
					try{
						formatter.parse( chain );
					} catch ( Exception e ){
						throw new ValidationException(chain + " no es una fecha valida");
					}
				break;
				
				case TYPE_SN:
					try{
						if ( !snValues.containsKey( chain.trim().toLowerCase() ) )
							throw new ValidationException(chain + " tiene que ser 'Sí' o 'No'");
						if ( chain.equalsIgnoreCase("no") )
							convertedChain = "No";
						else
							convertedChain = "Sí";
					} catch ( Exception e ){
						throw new ValidationException(chain + " tiene que ser 'Sí' o 'No'");
					}
				break;
				
				case TYPE_BM:
					try{
						if ( !bmValues.containsKey( chain.trim().toLowerCase() ) )
							throw new ValidationException(chain + " tiene que ser 'Bien' o 'Mal'");
						if ( chain.equalsIgnoreCase("bien") )
							convertedChain = "Bien";
						else
							convertedChain = "Mal";
					} catch ( Exception e ){
						throw new ValidationException(chain + " tiene que ser 'Bien' o 'Mal'");
					}
				break;
				
				case TYPE_DECIMAL:
					try{
						String[] ed = chain.split("\\.");
						Integer.valueOf( ed[0] );
						Integer.valueOf( ed[1] );
						if ( ed.length>2 )
							throw new ValidationException(chain + " tiene que ser un numero decimal");
					} catch ( Exception e ){
						throw new ValidationException(chain + " tiene que ser un numero decimal");
					}
				break;
				
				case TYPE_MONTHYEAR:
					try{
						String[] ed = chain.split("/");
						int m = Integer.valueOf( ed[0] );
						int y = Integer.valueOf( ed[1] );
						if ( ed.length>2 )
							throw new ValidationException(chain + " tiene que ser Mes/Año");
						if ( m < 1 || m > 12 )
							throw new ValidationException(chain + " tiene que ser Mes/Año (mes no valido)");
						if ( y < 2000 || y > 2050 )
							throw new ValidationException(chain + " tiene que ser Mes/Año (año no alido)");
					} catch ( Exception e ){
						throw new ValidationException(chain + " tiene que ser Mes/Año");
					}
				break;

				case TYPE_YEAR:
					try{
						int y = Integer.valueOf( chain );
						if ( y < 2000 || y > 2050 )
							throw new ValidationException(chain + " tiene que ser un Año valido (4 digitos)");
					} catch ( Exception e ){
						throw new ValidationException(chain + " tiene que ser un Año valido (4 digitos)");
					}
				break;

				default:
					throw new ValidationException("Tipo de dato (" + type + ") no valido.");
			}
		}
		
		return convertedChain;
	}
}
