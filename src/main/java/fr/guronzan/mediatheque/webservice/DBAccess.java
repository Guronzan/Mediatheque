package fr.guronzan.mediatheque.webservice;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import fr.guronzan.mediatheque.mappingclasses.dao.BookDao;
import fr.guronzan.mediatheque.mappingclasses.dao.CDDao;
import fr.guronzan.mediatheque.mappingclasses.dao.MovieDao;
import fr.guronzan.mediatheque.mappingclasses.dao.UserDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Book;
import fr.guronzan.mediatheque.mappingclasses.domain.CD;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

@Service
public class DBAccess {

	@Resource
	private UserDao userDao;

	@Resource
	private MovieDao movieDao;

	@Resource
	private CDDao cdDao;

	@Resource
	private BookDao bookDao;

	public void addUser(final User user) {
		this.userDao.create(user);
	}

	public void updateUser(final User user) {
		this.userDao.saveOrUpdate(user);
	}

	public void deleteUser(final User user) {
		this.userDao.delete(user);
	}

	public User getUserFromID(final int id) {
		return this.userDao.get(id);
	}

	public User getUserFromFullName(final String name, final String forName) {
		return this.userDao.getUserByFullName(name, forName);
	}

	public User getUserFromNickName(final String nickName) {
		return this.userDao.getUserByNickName(nickName);
	}

	public Collection<User> getAllUsers() {
		return this.userDao.getUsers();
	}

	public boolean checkPasswordFromID(final int userId, final String password) {
		final User user = this.userDao.get(userId);
		return user.checkPassword(password);
	}

	public boolean checkPasswordFromFullName(final String name,
			final String forName, final String password) {
		final User user = this.userDao.getUserByFullName(name, forName);
		return user.checkPassword(password);
	}

	public boolean containsUser(final String nickName) {
		return this.userDao.contains(nickName);
	}

	public boolean containsBook(final String text, final Integer tomeValue) {
		return this.bookDao.contains(text, tomeValue);
	}

	public void addBook(final Book book) {
		this.bookDao.create(book);
	}

	public void addMovie(final Movie movie) {
		this.movieDao.create(movie);
	}

	public boolean containsMovie(final String title, final Integer season) {
		return this.movieDao.contains(title, season);
	}

	public boolean containsCD(final String title) {
		return this.cdDao.contains(title);
	}

	public void addCD(final CD cd) {
		this.cdDao.create(cd);
	}
}
