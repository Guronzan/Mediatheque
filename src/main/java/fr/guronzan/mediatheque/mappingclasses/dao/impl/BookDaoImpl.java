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

import fr.guronzan.mediatheque.mappingclasses.dao.BookDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Book;

@Repository("bookDao")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class BookDaoImpl extends GenericDaoImpl<Book, Integer> implements
		BookDao {

	@Autowired
	public BookDaoImpl(
			@Qualifier("sessionFactory") final SessionFactory sessionFactory) {
		super(sessionFactory, Book.class);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public Book getBookById(final int id) {
		final StringBuffer hql = new StringBuffer("select book from Book book ");
		hql.append(" where book.book_id=:id ");
		final Query query = getSession().createQuery(hql.toString());

		query.setInteger("id", id);
		final List<Book> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public Book getBookByTitle(final String title) {
		final StringBuffer hql = new StringBuffer("select book from Book book ");
		hql.append(" where book.title=:title ");
		final Query query = getSession().createQuery(hql.toString());

		query.setString("title", title);
		final List<Book> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public Collection<Book> getBooksByAuthor(final String authorName) {
		final StringBuffer hql = new StringBuffer(
				"select movie from Movie movie ");
		hql.append(" where movie.directorName=:name ");
		final Query query = getSession().createQuery(hql.toString());

		query.setString("name", authorName);
		final Collection<Book> movies = query.list();
		if (movies.isEmpty()) {
			return new LinkedList<>();
		}
		return movies;
	}
}