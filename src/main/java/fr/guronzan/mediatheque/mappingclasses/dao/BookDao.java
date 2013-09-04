package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.Collection;

import fr.guronzan.mediatheque.mappingclasses.domain.Book;

public interface BookDao extends GenericDao<Book, Integer> {

	Book getBookById(int id);

	Book getBookByTitle(String name);

	Collection<Book> getBooksByAuthor(String name);

}