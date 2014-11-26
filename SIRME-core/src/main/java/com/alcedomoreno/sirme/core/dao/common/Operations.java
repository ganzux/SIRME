package com.alcedomoreno.sirme.core.dao.common;

import java.io.Serializable;
import java.util.List;
/**
 * Interfaz con operaciones CRUD sobre bd
 * @param <T> entidad con la que se implementa al interfaz
 */
public interface Operations<T extends Serializable> {

	/**
	 * Recupera una entidad por identificador
	 * @param id identificador que se va a buscar
	 * @return entidad recuperada
	 */		
	T findOne(final Serializable id);

	/**
	 * Recupera todas las entidades de una tabla
	 * @return lista total
	 */
    List<T> findAll();

    /**
     * Crea una entidad
     * @param entity entidad que se va a crear
     */
    void create(final T entity);

    /**
     * Actualiza una entidad
     * @param entity entidad que se va a actualizar
     * @return entidad actualizada
     */
    T update(final T entity);

    /**
     * Borra una entidad
     * @param entity entidad que se va a borrar
     */
    void delete(final T entity);

    /**
     * Borra una entidad por identificador
     * @param entityId midentificador de la entidad que se va a borrar
     */
    void deleteById(final Integer entityId);
    
    /**
     * Saca de la sesión de la caché de la sesión la entidad
     * @param entity entidad sobre la que se realizará la operación
     */
    void evict(final T entity);

    /**
     * Devuelve el tamaño de la Entidad
     * @return número de elementos que existen
     */
    public long count();
    
    /**
     * Recupera todas las entidades de una tabla peginándola
     * @param rowId El registro de inicio (El índice empieza en el 0)
     * @param size El tamaño de salida de la lista
     * @return lista paginada
     */
    List<T> findAll(int rowId, int size);
    
}
