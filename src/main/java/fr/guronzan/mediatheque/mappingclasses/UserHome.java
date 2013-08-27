package fr.guronzan.mediatheque.mappingclasses;

// Generated 27 août 2013 13:58:41 by Hibernate Tools 4.0.0

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class User.
 * 
 * @see fr.guronzan.mediatheque.mappingclasses.User
 * @author Hibernate Tools
 */
public class UserHome {

	private static final Log log = LogFactory.getLog(UserHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (final Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(final User transientInstance) {
		log.debug("persisting User instance");
		try {
			this.sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (final RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(final User instance) {
		log.debug("attaching dirty User instance");
		try {
			this.sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (final RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(final User instance) {
		log.debug("attaching clean User instance");
		try {
			this.sessionFactory.getCurrentSession().lock(instance,
					LockMode.NONE);
			log.debug("attach successful");
		} catch (final RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(final User persistentInstance) {
		log.debug("deleting User instance");
		try {
			this.sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (final RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public User merge(final User detachedInstance) {
		log.debug("merging User instance");
		try {
			final User result = (User) this.sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (final RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public User findById(final Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			final User instance = (User) this.sessionFactory
					.getCurrentSession().get(
							"fr.guronzan.mediatheque.mappingclasses.User", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (final RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<User> findByExample(final User instance) {
		log.debug("finding User instance by example");
		try {
			final List<User> results = this.sessionFactory
					.getCurrentSession()
					.createCriteria(
							"fr.guronzan.mediatheque.mappingclasses.User")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (final RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
