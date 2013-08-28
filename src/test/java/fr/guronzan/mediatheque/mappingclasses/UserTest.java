package fr.guronzan.mediatheque.mappingclasses;

import org.hibernate.Session;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.guronzan.mediatheque.HibernateUtil;
import fr.guronzan.mediatheque.mappingclasses.dao.impl.UserDaoImpl;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

public class UserTest {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testCreateDatabase() {
		this.logger.info("Hibernate many to many (XML Mapping)");
		final Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		// final User user = new User("user2", "prenom2", "password", new
		// Date());
		// final Movie movie = new Movie("film2");
		//
		// user.addMovie(movie);
		// session.save(user);

		final UserDaoImpl userDaoImpl = new UserDaoImpl(
				session.getSessionFactory());
		final User userFound = userDaoImpl
				.getUserByFullName("user1", "prenom1");
		final User userNotFound = userDaoImpl.getUserByFullName("user2",
				"prenom1");

		session.getTransaction().commit();

		this.logger.info("Done");
	}
}
