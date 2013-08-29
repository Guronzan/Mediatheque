package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.Collection;

import fr.guronzan.mediatheque.mappingclasses.domain.Movie;

public interface MovieDao extends GenericDao<Movie, Integer> {

	public Movie getMovieById(int id);

	Collection<Movie> getMoviesByDirector(String directorName);

	public Movie getMovieByTitle(final String title);

}