package com.sirme.transform;

import java.util.HashMap;
import java.util.Map;

import com.sirme.bussiness.IBusinessObject;


public class TransformFactory {
	
	private static Map<String,ITransformator> entities;
	
	public static Map<String, ITransformator> getEntities() {
		if (entities == null) {
			entities = new HashMap<String, ITransformator>();
		}
		
		return entities;
	}
	
    public static ITransformator getTransformator(Class<? extends IBusinessObject> businessClass) {
    	
    	String businessClassName = null;
    	@SuppressWarnings("unused")
		String transformatorClassName = null;
    	
    	try {
	    	
	    	// Obtenemos el nombre de la clase de tipo entidad de negocio
	    	businessClassName = businessClass.getName();
	    	
	    	// Si la clase esta en la cache de transformadores, la devolvemos
	    	if (getEntities().containsKey(businessClassName)) {
	    		return (ITransformator) getEntities().get(businessClassName);
	    	}
	    	
	    	// Obtenemos la clase transformadora
	    	Class<?> transformatorClass = businessClass.newInstance().getTransformator();
	    	
	    	// Le asignamos su nombre (por cuestiones de logging)
	    	transformatorClassName = transformatorClass.getName();
	    	
	    	// Obtenemos un objeto de la clase transformadora
	    	Object objTransformator = transformatorClass.newInstance();
	    	
	    	// Si el objeto obtenido implementa la interfaz ITransformator, se mete en cache y se devuelve
	    	if (objTransformator instanceof ITransformator) {
	    		getEntities().put(businessClassName, (ITransformator) objTransformator);
	    		return (ITransformator) objTransformator;
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

