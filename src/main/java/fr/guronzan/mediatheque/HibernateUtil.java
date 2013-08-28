package fr.guronzan.mediatheque;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

	private static SessionFactory sessionFactory = buildSessionFactory();
	// private static ServiceRegistry serviceRegistry;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HibernateUtil.class);

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			final Configuration configuration = new Configuration();
			configuration.configure();
			// Pour hibernate4
			// serviceRegistry = new ServiceRegistryBuilder().applySettings(
			// configuration.getProperties()).buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory();
			return sessionFactory;
		} catch (final Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			ex.printStackTrace();
			LOGGER.error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}

}