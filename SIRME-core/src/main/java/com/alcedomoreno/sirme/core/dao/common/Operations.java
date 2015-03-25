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
     * Saca de la sesiÃ³n de la cachÃ© de la sesiÃ³n la entidad
     * @param entity entidad sobre la que se realizarÃ¡ la operaciÃ³n
     */
    void evict(final T entity);

    /**
     * Devuelve el tamaÃ±o de la Entidad
     * @return nÃºmero de elementos que existen
     */
    public Integer count();
    
    /**
     * Recupera todas las entidades de una tabla peginÃ¡ndola
     * @param rowId El registro de inicio (El Ã­ndice empieza en el 0)
     * @param size El tamaÃ±o de salida de la lista
     * @return lista paginada
     */
    List<T> findAll(int rowId, int size);
    
}
