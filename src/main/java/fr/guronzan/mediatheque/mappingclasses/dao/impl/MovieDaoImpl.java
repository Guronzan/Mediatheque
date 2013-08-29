package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.guronzan.mediatheque.mappingclasses.dao.MovieDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;

@Repository("movieDao")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class MovieDaoImpl extends GenericDaoImpl<Movie, Integer> implements
		MovieDao {

	@Autowired
	public MovieDaoImpl(
			@Qualifier("sessionFactory") final SessionFactory sessionFactory) {
		super(sessionFactory, Movie.class);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public Movie getMovieById(final int id) {
		final StringBuffer hql = new StringBuffer(
				"select movie from Movie movie ");
		hql.append(" where movie.movie_id=:id ");
		final Query query = getSession().createQuery(hql.toString());

		query.setInteger("id", id);
		final List<Movie> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public Movie getMovieByTitle(final String title) {
		final StringBuffer hql = new StringBuffer(
				"select movie from Movie movie ");
		hql.append(" where movie.title=:title ");
		final Query query = getSession().createQuery(hql.toString());

		query.setString("name", title);
		final List<Movie> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public Collection<Movie> getMoviesByDirector(final String directorName) {
		final StringBuffer hql = new StringBuffer(
				"select movie from Movie movie ");
		hql.append(" where movie.directorName=:name ");
		final Query query = getSession().createQuery(hql.toString());

		query.setString("name", directorName);
		final Collection<Movie> movies = query.list();
		if (movies.isEmpty()) {
			return new LinkedList<>();
		}
		return movies;
	}
}