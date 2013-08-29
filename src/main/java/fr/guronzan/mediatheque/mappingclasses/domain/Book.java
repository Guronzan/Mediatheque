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
@Table(name = "book", catalog = "mediatheque", uniqueConstraints = { @UniqueConstraint(columnNames = "BOOK_ID") })
public class Book extends AbstractPersistentObject {

	private int bookId;
	private String title;
	private String authorName;
	private Date releaseDate = new Date();
	private String editor;
	private Set<User> owners = new HashSet<>();

	public Book() {
		// Empty constructor
	}

	public Book(final String title) {
		this.title = title;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "BOOK_ID", unique = true, nullable = false)
	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(final int bookId) {
		this.bookId = bookId;
	}

	@Column(name = "TITLE", unique = true, nullable = false, length = 40)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Column(name = "AUTHOR_NAME", unique = true, nullable = true, length = 30)
	public String getAuthorName() {
		return this.authorName;
	}

	public void setAuthorName(final String authorName) {
		this.authorName = authorName;
	}

	@Column(name = "RELEASE_DATE", nullable = false, length = 20)
	public Date getReleaseDate() {
		return this.releaseDate;
	}

	public void setReleaseDate(final Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Column(name = "EDITOR", nullable = false, length = 20)
	public String getEditor() {
		return this.editor;
	}

	public void setEditor(final String editor) {
		this.editor = editor;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "book_user", catalog = "mediatheque", joinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "CD_ID", nullable = false, updatable = false) })
	public Set<User> getOwners() {
		return this.owners;
	}

	public void setOwners(final Set<User> owners) {
		this.owners = owners;
	}

	public void addOwner(final User user) {
		this.owners.add(user);
	}
}
