package fr.guronzan.mediatheque.mappingclasses;

import java.util.Date;

import org.hibernate.Session;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.guronzan.mediatheque.HibernateUtil;

public class UserTest {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testCreateDatabase() {
		this.logger.info("Hibernate many to many (XML Mapping)");
		final Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		final User user = new User("user1", "prenom1", "password", new Date());
		final Movie movie = new Movie("film1");

		user.addMovie(movie);
		session.save(user);

		session.getTransaction().commit();

		this.logger.info("Done");
	}
}
