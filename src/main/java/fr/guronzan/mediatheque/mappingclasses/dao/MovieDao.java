package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.guronzan.mediatheque.mappingclasses.domain.Movie;

@Transactional(propagation = Propagation.MANDATORY)
public interface MovieDao extends GenericDao<Movie, Integer> {

	public Movie getMovieById(int id);

	Collection<Movie> getMoviesByDirector(String directorName);

	public Movie getMovieByTitle(final String title);

}