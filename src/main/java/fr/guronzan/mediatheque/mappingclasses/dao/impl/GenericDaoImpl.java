package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import fr.guronzan.mediatheque.mappingclasses.dao.GenericDao;
import fr.guronzan.mediatheque.mappingclasses.domain.AbstractPersistentObject;

@Transactional
/**
 * 
 * @author rodriguesgu
 *
 * @param <T>
 * @param <K> K : PrimaryKey
 */
public class GenericDaoImpl<T, K extends Serializable> extends
        HibernateDaoSupport implements GenericDao<T, K> {

    private final Class<T> type;

    public GenericDaoImpl(final SessionFactory sessionFactory,
            final Class<T> type) {
        super.setSessionFactory(sessionFactory);
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
        T value = (T) getSession().get(this.type, id);
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
        final Criteria crit = getSession().createCriteria(this.type);
        return crit.list();
    }

    @Override
    public void createOrUpdate(final T o) {
        if (o instanceof AbstractPersistentObject) {
            if (((AbstractPersistentObject) o).isCreation()) {
                getSession().saveOrUpdate(o);
            } else {
                getSession().merge(o);
            }
        } else {
            throw new IllegalArgumentException(
                    "this method support only AbstractPersistentObject");
        }
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
