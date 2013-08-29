package fr.guronzan.mediatheque.mappingclasses.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import fr.guronzan.mediatheque.mappingclasses.dao.AbstractPersistentObject;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user", catalog = "mediatheque", uniqueConstraints = { @UniqueConstraint(columnNames = "USER_ID") })
public class User extends AbstractPersistentObject {

	private int userId;
	private String name;
	private String forName;
	private String password;
	private Date registrationDate;
	private byte[] avatar;
	private Set<Movie> movies = new HashSet<>(0);
	private Set<Book> books = new HashSet<>(0);
	private Set<CD> cds = new HashSet<>(0);

	public User(final int userId, final String name, final String forName,
			final String password, final Date registrationDate,
			final byte[] avatar, final Set<Movie> movies,
			final Set<Book> books, final Set<CD> cds) {
		this.userId = userId;
		this.name = name;
		this.forName = forName;
		this.password = password;
		this.registrationDate = registrationDate;
		this.avatar = avatar;
		this.movies = movies;
		this.books = books;
		this.cds = cds;
	}

	public User() {
		// Empty constructor
	}

	public User(final String name, final String forName, final String password,
			final Date registrationDate) {
		this.name = name;
		this.forName = forName;
		this.password = password;
		this.registrationDate = registrationDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_ID", unique = true, nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(final int userId) {
		this.userId = userId;
	}

	@Column(name = "NAME", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column(name = "FORNAME", nullable = false, length = 20)
	public String getForName() {
		return this.forName;
	}

	public void setForName(final String forName) {
		this.forName = forName;
	}

	@Column(name = "PASSWORD", nullable = false, length = 30)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Column(name = "REGISTRATION_DATE", nullable = false, length = 10)
	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(final Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "movie_user", catalog = "mediatheque", joinColumns = { @JoinColumn(name = "MOVIE_ID", nullable = false, updatable = true) }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = true) })
	public Set<Movie> getMovies() {
		return this.movies;
	}

	public void setMovies(final Set<Movie> movies) {
		this.movies = movies;
	}

	public void addMovie(final Movie movie) {
		this.movies.add(movie);
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "book_user", catalog = "mediatheque", joinColumns = { @JoinColumn(name = "BOOK_ID", nullable = false, updatable = true) }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = true) })
	public Set<Book> getBooks() {
		return this.books;
	}

	public void setBooks(final Set<Book> books) {
		this.books = books;
	}

	public void addBook(final Book book) {
		this.books.add(book);
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "cd_user", catalog = "mediatheque", joinColumns = { @JoinColumn(name = "CD_ID", nullable = false, updatable = true) }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = true) })
	public Set<CD> getCds() {
		return this.cds;
	}

	public void setCds(final Set<CD> cds) {
		this.cds = cds;
	}

	public void addCD(final CD cd) {
		this.cds.add(cd);
	}

	@Column(name = "AVATAR", nullable = true)
	public byte[] getAvatar() {
		return this.avatar;
	}

	public void setAvatar(final byte[] avatar) {
		this.avatar = avatar;
	}
}