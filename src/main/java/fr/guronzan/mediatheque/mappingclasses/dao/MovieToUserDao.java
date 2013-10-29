package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.MovieToUser;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

@Transactional(propagation = Propagation.MANDATORY)
public interface MovieToUserDao extends GenericDao<MovieToUser, Integer> {

    Collection<Movie> getMoviesByOwner(final int ownerID);

    void removeAll();

    /**
     * @param user
     * @param movieByTitle
     */
    void add(final User user, final Movie movie);
}