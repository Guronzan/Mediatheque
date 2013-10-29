package fr.guronzan.mediatheque.webservice;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import fr.guronzan.mediatheque.mappingclasses.dao.BookDao;
import fr.guronzan.mediatheque.mappingclasses.dao.CDDao;
import fr.guronzan.mediatheque.mappingclasses.dao.MovieDao;
import fr.guronzan.mediatheque.mappingclasses.dao.MovieToUserDao;
import fr.guronzan.mediatheque.mappingclasses.dao.UserDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Book;
import fr.guronzan.mediatheque.mappingclasses.domain.CD;
import fr.guronzan.mediatheque.mappingclasses.domain.DomainObject;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.MovieToUser;
import fr.guronzan.mediatheque.mappingclasses.domain.User;
import fr.guronzan.mediatheque.mappingclasses.domain.types.DataType;

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

    @Resource
    private MovieToUserDao movieToUserDao;

    public Integer addUser(final User user) {
        return this.userDao.create(user);
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

    public Integer addBook(final Book book) {
        return this.bookDao.create(book);
    }

    public Integer addMovie(final Movie movie) {
        return this.movieDao.create(movie);
    }

    public boolean containsMovie(final String title, final Integer season) {
        return this.movieDao.contains(title, season);
    }

    public boolean containsCD(final String title) {
        return this.cdDao.contains(title);
    }

    public Integer addCD(final CD cd) {
        return this.cdDao.create(cd);
    }

    public void cleanDB() {
        this.bookDao.removeAllBooks();
        this.cdDao.removeAllCDs();
        this.movieDao.removeAllMovies();
        this.userDao.removeAllUsers();
    }

    public void updateUser(final String currentUser,
            final String selectedElement, final DataType dataType) {
        final User user = getUserFromNickName(currentUser);
        switch (dataType) {
        case BOOK:
            final Book book = this.bookDao.getBookByTitle(selectedElement
                    .split(" - ")[0]);
            assert book != null;
            user.addBook(book);
            break;
        case MOVIE:
            final String[] split = selectedElement.split(" - ");
            Movie movie;
            if (split.length == 2) {
                movie = this.movieDao.getMovieByTitle(split[0]);
            } else {
                movie = this.movieDao.getMovieByTitleAndSeason(split[0],
                        Integer.parseInt(split[1]));
            }
            assert movie != null;
            this.movieToUserDao.add(user, movie);
            break;
        case MUSIC:
            final CD cd = this.cdDao
                    .getCdByTitle(selectedElement.split(" - ")[0]);
            assert cd != null;
            user.addCD(cd);
            break;
        default:
            throw new IllegalArgumentException("Unknow state : "
                    + dataType.getValue());
        }
        updateUser(user);
    }

    public List<? extends DomainObject> getAllNotOwned(final DataType dataType,
            final String currentUserNick) {
        switch (dataType) {
        case BOOK: {
            final List<Book> all = this.bookDao.getAll();
            final List<Book> books = new LinkedList<>();
            for (final Book book : all) {
                for (final User owner : book.getOwners()) {
                    if (owner.getNickName().equals(currentUserNick)) {
                        continue;
                    }
                    books.add(book);
                }
            }
            return books;
        }
        case MOVIE: {
            final List<Movie> all = this.movieDao.getAll();
            final List<Movie> movies = new LinkedList<>();
            for (final Movie movie : all) {
                for (final MovieToUser movieToUser : movie.getOwners()) {
                    if (movieToUser.getUser().getNickName()
                            .equals(currentUserNick)) {
                        continue;
                    }
                    movies.add(movie);
                }
            }
            return movies;
        }
        case MUSIC: {
            final List<CD> all = this.cdDao.getAll();
            final List<CD> cds = new LinkedList<>();
            for (final CD cd : all) {
                for (final User owner : cd.getOwners()) {
                    if (owner.getNickName().equals(currentUserNick)) {
                        continue;
                    }
                    cds.add(cd);
                }
            }
            return cds;
        }
        default:
            throw new IllegalArgumentException("Unknow state : "
                    + dataType.getValue());
        }
    }

    public Collection<? extends DomainObject> getAll(final DataType dataType) {
        switch (dataType) {
        case BOOK:
            return this.bookDao.getAll();
        case MOVIE:
            return this.movieDao.getAll();
        case MUSIC:
            return this.cdDao.getAll();
        default:
            throw new IllegalArgumentException("Unknow state : "
                    + dataType.getValue());
        }
    }

}
