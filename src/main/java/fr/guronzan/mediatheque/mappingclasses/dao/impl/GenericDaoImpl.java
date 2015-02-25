package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import fr.guronzan.mediatheque.mappingclasses.dao.GenericDao;
import fr.guronzan.mediatheque.mappingclasses.domain.DomainObject;

@Transactional
/**
 *
 * @author Guillaume
 *
 * @param <T>
 * @param <K>
 *            K : PrimaryKey
 */
public abstract class GenericDaoImpl<T extends DomainObject, K extends Serializable>
        extends HibernateDaoSupport implements GenericDao<T, K> {

    private final Class<T> type;
    private final SessionFactory sessionFactory;
    protected Session session;

    public GenericDaoImpl(final SessionFactory sessionFactory,
            final Class<T> type) {
        super.setSessionFactory(sessionFactory);
        this.sessionFactory = sessionFactory;
        this.type = type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public K create(final T o) {
        return (K) getSession().save(o);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(final K id) {
        T value = (T) currentSession().get(this.type, id);
        if (value == null) {
            return null;
        }

        if (value instanceof HibernateProxy) {
            Hibernate.initialize(value);
            value = (T) ((HibernateProxy) value).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return getSession().createCriteria(this.type).list();
    }

    protected Session getSession() {
        if (this.session == null || !this.session.isConnected()) {
            this.session = this.sessionFactory.openSession();
        }
        return this.session;
    }

    @Override
    public void saveOrUpdate(final T o) {
        getSession().saveOrUpdate(o);

    }

    @Override
    public void update(final T o) {
        getSession().update(o);
    }

    @Override
    public void delete(final T o) {
        getSession().delete(o);
    }
}
