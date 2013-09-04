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
	public Movie getMovieById(final int id) {
		final StringBuffer hql = new StringBuffer(
				"select movie from Movie movie ");
		hql.append(" where movie.movie_id=:id ");
		final Query query = getSession().createQuery(hql.toString());

		query.setInteger("id", id);
		return (Movie) query.uniqueResult();
	}

	@Override
	public Movie getMovieByTitle(final String title) {
		final StringBuffer hql = new StringBuffer(
				"select movie from Movie movie ");
		hql.append(" where movie.title=:title ");
		final Query query = getSession().createQuery(hql.toString());

		query.setString("title", title);
		final List<Movie> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
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