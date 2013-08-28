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
	private Set<Movie> movies = new HashSet<>(0);

	public User(final int id, final String name, final String forName,
			final String password, final Date registrationDate) {
		this.userId = id;
		this.name = name;
		this.forName = forName;
		this.password = password;
		this.registrationDate = registrationDate;
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
	@JoinTable(name = "movie_user", catalog = "mediatheque", joinColumns = { @JoinColumn(name = "MOVIE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = false) })
	public Set<Movie> getMovies() {
		return this.movies;
	}

	public void setMovies(final Set<Movie> movies) {
		this.movies = movies;
	}

	public void addMovie(final Movie movie) {
		this.movies.add(movie);
	}
}
