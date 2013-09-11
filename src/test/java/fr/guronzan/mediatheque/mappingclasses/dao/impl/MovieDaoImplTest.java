package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import fr.guronzan.mediatheque.mappingclasses.SpringTests;
import fr.guronzan.mediatheque.mappingclasses.dao.MovieDao;
import fr.guronzan.mediatheque.mappingclasses.dao.UserDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;

public class MovieDaoImplTest extends SpringTests {

    private static final String TITLE = "title";
    private static final String DIRECTOR = "director";

    @Resource
    private MovieDao movieDao;
    @Resource
    private UserDao userDao;

    @Before
    public void cleanDB() {
        this.movieDao.removeAllMovies();
    }

    private void addNewMovie() {
        final Movie movie = new Movie(TITLE);
        movie.setDirectorName(DIRECTOR);
        movie.setOwnedDVD(true);
        this.movieDao.create(movie);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testAddDuplicate() {
        addNewMovie();
        addNewMovie();
    }

    @Test
    public final void testGetMovieByTitle() {
        assertFalse(this.movieDao.contains(TITLE));
        addNewMovie();
        assertTrue(this.movieDao.contains(TITLE));
        final Movie movieByTitle = this.movieDao.getMovieByTitle(TITLE);
        assertTrue(movieByTitle.isOwnedDVD());
        assertNull(movieByTitle.getSeason());
        assertTrue(movieByTitle.getOwners().isEmpty());

        final User user = new User(
                "name",
                "forName",
                "nick",
                String.valueOf(DigestUtils.md5DigestAsHex("password".getBytes())),
                new Date());
        user.addMovie(movieByTitle);

        final User userByNickName = this.userDao.getUserByNickName("nick");
        assertNull(userByNickName);
        this.userDao.create(user);

        final Movie movieByTitle2 = this.movieDao.getMovieByTitle(TITLE);
        assertThat(movieByTitle2.getOwners().size(), is(1));
        assertTrue(movieByTitle.getOwners().isEmpty());

    }

    @Test
    public final void testGetMoviesByDirector() {
        assertTrue(this.movieDao.getMoviesByDirector(DIRECTOR).isEmpty());
        addNewMovie();
        final List<Movie> moviesByDirector = (List<Movie>) this.movieDao
                .getMoviesByDirector(DIRECTOR);
        assertThat(moviesByDirector.size(), is(1));
        assertThat(moviesByDirector.get(0).getDirectorName(), is(DIRECTOR));
        assertThat(moviesByDirector.get(0).getTitle(), is(TITLE));
        assertNull(moviesByDirector.get(0).getSeason());
        assertTrue(moviesByDirector.get(0).getOwners().isEmpty());

    }

}
