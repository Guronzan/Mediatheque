package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import fr.guronzan.mediatheque.mappingclasses.dao.MovieToUserDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.MovieToUser;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

@Repository("movieToUserDao")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class MovieToUserDaoImpl extends GenericDaoImpl<MovieToUser, Integer>
        implements MovieToUserDao {

    @Autowired
    public MovieToUserDaoImpl(
            @Qualifier("sessionFactory") final SessionFactory sessionFactory) {
        super(sessionFactory, MovieToUser.class);
    }

    @Override
    public Collection<Movie> getMoviesByOwner(final int ownerID) {
        final StringBuffer hql = new StringBuffer(
                "select movieToUser from MovieToUser movieToUser ");
        hql.append(" where movieToUser.ownerID=:ownerId ");
        final Query query = getSession().createQuery(hql.toString());

        query.setInteger("ownerId", ownerID);
        return query.list();
    }

    @Override
    public void removeAll() {
        final Collection<MovieToUser> movieToUsers = getAll();
        for (final MovieToUser movie : movieToUsers) {
            delete(movie);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.guronzan.mediatheque.mappingclasses.dao.MovieToUserDao#add(fr.guronzan
     * .mediatheque.mappingclasses.domain.User,
     * fr.guronzan.mediatheque.mappingclasses.domain.Movie)
     */
    @Override
    public void add(final User user, final Movie movie) {
        final MovieToUser movieToUser = new MovieToUser(user, movie);
        create(movieToUser);
    }

}