package fr.guronzan.mediatheque;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HibernateUtil {
	private HibernateUtil() {
		// no instantiation
	}

	private static final SessionFactory SESSION_FACTORY = buildSessionFactory();
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HibernateUtil.class);

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();

		} catch (final Exception ex) {
			LOGGER.error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}
}