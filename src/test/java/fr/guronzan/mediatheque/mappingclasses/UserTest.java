package fr.guronzan.mediatheque.mappingclasses;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import fr.guronzan.mediatheque.mappingclasses.dao.MovieDao;
import fr.guronzan.mediatheque.mappingclasses.dao.UserDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class UserTest extends SpringTests {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private UserDao userDaoImpl;

	@Resource
	private MovieDao movieDao;

	@Test
	@Transactional
	public void testCreateDatabase() {
		this.logger.info("Hibernate many to many (XML Mapping)");
		final Movie movie = new Movie("Movie41");
		this.movieDao.createOrUpdate(movie);

		final User userFound = this.userDaoImpl.getUserByFullName("user1",
				"prenom1");
		final Movie movie5 = new Movie("film51");
		userFound.addMovie(movie5);
		this.userDaoImpl.createOrUpdate(userFound);
		final User userNotFound = this.userDaoImpl.getUserByFullName("user2",
				"prenom1");
		assertThat(userNotFound, is(nullValue()));

		final Movie notNullMovie = this.movieDao.getMovieByTitle("Movie41");
		assertThat(notNullMovie, is(notNullValue()));

		this.logger.info("Done");
	}
}
