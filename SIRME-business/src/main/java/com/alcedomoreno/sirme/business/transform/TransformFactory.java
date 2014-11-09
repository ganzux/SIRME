package com.alcedomoreno.sirme.business.transform;

import java.util.HashMap;
import java.util.Map;

import com.alcedomoreno.sirme.business.data.BusinessObject;


public class TransformFactory {
	
	private static Map<String,Transformator> entities;
	
	public static Map<String, Transformator> getEntities() {
		if (entities == null) {
			entities = new HashMap<String, Transformator>();
		}
		
		return entities;
	}
	
    public static Transformator getTransformator(Class<? extends BusinessObject> businessClass) {
    	
    	String businessClassName = null;
    	@SuppressWarnings("unused")
		String transformatorClassName = null;
    	
    	try {
	    	
	    	// Obtenemos el nombre de la clase de tipo entidad de negocio
	    	businessClassName = businessClass.getName();
	    	
	    	// Si la clase esta en la cache de transformadores, la devolvemos
	    	if (getEntities().containsKey(businessClassName)) {
	    		return (Transformator) getEntities().get(businessClassName);
	    	}
	    	
	    	// Obtenemos la clase transformadora
	    	Class<?> transformatorClass = businessClass.newInstance().getTransformator();
	    	
	    	// Le asignamos su nombre (por cuestiones de logging)
	    	transformatorClassName = transformatorClass.getName();
	    	
	    	// Obtenemos un objeto de la clase transformadora
	    	Object objTransformator = transformatorClass.newInstance();
	    	
	    	// Si el objeto obtenido implementa la interfaz Transformator, se mete en cache y se devuelve
	    	if (objTransformator instanceof Transformator) {
	    		getEntities().put(businessClassName, (Transformator) objTransformator);
	    		return (Transformator) objTransformator;
	    	}
	    	
	    	return null;
    	
    	} catch (IllegalAccessException iaeEx) {
    		// Si hay error, devolvemos null
    		return null;
    		
    	} catch (InstantiationException insEx) {
    		// Si hay error, devolvemos null
    		return null;
    		
    	} catch (Exception e) {
    		// Si hay error, devolvemos null
    		return null;
    	}
    	
        
    }
}

