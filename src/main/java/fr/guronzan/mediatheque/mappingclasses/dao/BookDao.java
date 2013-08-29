package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.Collection;

import fr.guronzan.mediatheque.mappingclasses.domain.Book;

public interface BookDao extends GenericDao<Book, Integer> {

	public Book getBookById(int id);

	public Book getBookByTitle(String name);

	public Collection<Book> getBooksByAuthor(String name);

}