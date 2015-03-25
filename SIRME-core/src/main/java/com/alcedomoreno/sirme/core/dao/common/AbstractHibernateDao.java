package com.alcedomoreno.sirme.core.dao.common;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.alcedomoreno.sirme.core.dao.ApplicationsDaoImpl;
import com.google.common.base.Preconditions;

/**
 * Clase que implementa todos los mÃ©todos genÃ©ricos
 * @param <T> tipo con el que se extenderÃ¡ la clase
 */
@SuppressWarnings("unchecked")
public class AbstractHibernateDao<T extends Serializable> extends HibernateDaoSupport implements Operations<T> {

	///////////////////////////////////////////////////////////////
	//                         Attributes                        //
	///////////////////////////////////////////////////////////////

	private static Logger log = LoggerFactory.getLogger( ApplicationsDaoImpl.class );
	private static final String CLASS_NAME = "ApplicationsDaoImpl";

	private Class<T> clazz;

	@Autowired
    private SessionFactory sessionFactory;

	///////////////////////////////////////////////////////////////
	//                      End of Attributes                    //
	///////////////////////////////////////////////////////////////

	@Autowired
	public void init(SessionFactory factory) {
	    setSessionFactory(factory);
	}

	protected final void setClazz(final Class<T> clazzToSet) {
        clazz = Preconditions.checkNotNull(clazzToSet);
    }
	@Override
	public T findOne(Serializable id) {
		return (T) getCurrentSession().get(clazz, id);
	}
	@Override
	public List<T> findAll() {

		return getCurrentSession().createQuery("from " + clazz.getName()).list();
	}
	@Override
	public void create(T entity) {
		Preconditions.checkNotNull(entity);
        // getCurrentSession().persist(entity);
		getCurrentSession().saveOrUpdate(entity);
	}
	@Override
	public T update(T entity) {
		Preconditions.checkNotNull(entity);
        return (T) getCurrentSession().merge(entity);
	}
	@Override
	public void delete(T entity) {
		Preconditions.checkNotNull(entity);
		getCurrentSession().delete(entity);
	}
	@Override
	public void deleteById(Integer entityId) {
		final T entity = findOne(entityId);
        Preconditions.checkState(entity != null);
        delete(entity);
	}
    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
	@Override
	public void evict(T entity) {
		Preconditions.checkNotNull(entity);
		getCurrentSession().evict(entity);
	}


	@Override
	public Integer count() {
		return (Integer) getCurrentSession().createCriteria( clazz.getName() )
				.setProjection( Projections.rowCount() ).uniqueResult();
	}
	
	@Override
	public List<T> findAll(int rowId, int size) {

		Query query = getCurrentSession().createQuery("FROM " + clazz.getName());
        query.setFirstResult( rowId );
        query.setMaxResults( size );

		return query.list();
	}
}
