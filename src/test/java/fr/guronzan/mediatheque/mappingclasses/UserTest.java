package fr.guronzan.mediatheque.mappingclasses;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import fr.guronzan.mediatheque.mappingclasses.dao.impl.UserDaoImpl;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class UserTest extends SpringTests {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private UserDaoImpl userDaoImpl;

	@Test
	@Transactional
	public void testCreateDatabase() {
		this.logger.info("Hibernate many to many (XML Mapping)");
		// final Session session =
		// HibernateUtil.getSessionFactory().openSession();
		//
		// session.beginTransaction();

		// final User user = new User("user2", "prenom2", "password", new
		// Date());
		// final Movie movie = new Movie("film2");
		//
		// user.addMovie(movie);
		// session.save(user);

		final User userFound = this.userDaoImpl.getUserByFullName("user1",
				"prenom1");
		final Movie movie = new Movie("film5");
		userFound.addMovie(movie);
		this.userDaoImpl.createOrUpdate(userFound);

		final User userNotFound = this.userDaoImpl.getUserByFullName("user2",
				"prenom1");
		assertThat(userNotFound, is(nullValue()));

		this.logger.info("Done");
	}
}
