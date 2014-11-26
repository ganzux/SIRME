package com.alcedomoreno.sirme.core.dao.common;

import java.io.Serializable;
import java.util.List;

/**
 * Interfaz con operaciones genéricas sobre bd
 * @param <T> entidad con la que se implementa al interfaz
 */
public interface GenericDao <T extends Serializable> extends Operations<T> {
	/**
	 * Recupera todos los elementos de la clase que se le
	 * pasa como parámetro
	 * @param clazz clase de la que se va a recuperar
	 * @return lista recuperada
	 */
	List<T> loadAll(Class<T> clazz);
	/**
	 * Recupera una entidad por su id y su clase
	 * @param clazz clase que se va a buscar
	 * @param id identificador que se va a buscar
	 * @return entidad recuperada
	 */
	T findById(Class<T> clazz, Serializable id);
	
}
