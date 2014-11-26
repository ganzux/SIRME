package com.alcedomoreno.sirme.core.dao.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * Clase genérica para operaciones sobre bd
 * @param <T> entidad con la que se extiende la clase
 */
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GenericHibernateDao<T extends Serializable> extends AbstractHibernateDao<T>
														 implements GenericDao<T> {

	@SuppressWarnings("unchecked")
	@Override
	public List<T> loadAll(Class<T> clazz) {
		return getCurrentSession().createQuery("FROM " + clazz.getName()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Class<T> clazz, Serializable id) {
		return (T) getCurrentSession().get(clazz, id);
	}

}
