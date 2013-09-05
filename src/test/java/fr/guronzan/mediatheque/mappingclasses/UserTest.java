package fr.guronzan.mediatheque.mappingclasses;

import java.util.Date;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import fr.guronzan.mediatheque.mappingclasses.dao.MovieDao;
import fr.guronzan.mediatheque.mappingclasses.dao.UserDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@Slf4j
public class UserTest extends SpringTests {

    @Resource
    private UserDao userDaoImpl;

    @Resource
    private MovieDao movieDao;

    @Test
    @Transactional
    public void testCreateDatabase() {
        log.info("Hibernate many to many (XML Mapping)");
        final Movie movie = new Movie("Movie41");
        this.movieDao.createOrUpdate(movie);
        this.userDaoImpl.create(new User("user1", "prenom1", "nick", String
                .valueOf(DigestUtils.md5DigestAsHex("password".getBytes())),
                new Date()));

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
        log.info("Done");
    }
}
