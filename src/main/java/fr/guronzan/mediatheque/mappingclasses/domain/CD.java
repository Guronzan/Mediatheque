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
@Table(name = "cd", catalog = "mediatheque", uniqueConstraints = { @UniqueConstraint(columnNames = "CD_ID") })
public class CD extends AbstractPersistentObject {

	private int cdId;
	private String title;
	private String authorName;
	private Date releaseDate = new Date();
	private CDKindType kind;
	private byte[] picture;
	private Set<User> owners = new HashSet<>();

	public CD(final int cdId, final String title, final String authorName,
			final Date releaseDate, final CDKindType kind,
			final byte[] picture, final Set<User> owners) {
		this.cdId = cdId;
		this.title = title;
		this.authorName = authorName;
		this.releaseDate = releaseDate;
		this.kind = kind;
		this.picture = picture;
		this.owners = owners;
	}

	public CD() {
		// Empty constructor
	}

	public CD(final String title) {
		this.title = title;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CD_ID", unique = true, nullable = false)
	public int getCdId() {
		return this.cdId;
	}

	public void setCdId(final int cdId) {
		this.cdId = cdId;
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

	@Column(name = "KIND", nullable = false, length = 20)
	public CDKindType getKind() {
		return this.kind;
	}

	public void setKind(final CDKindType kind) {
		this.kind = kind;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "cd_user", catalog = "mediatheque", joinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = true) }, inverseJoinColumns = { @JoinColumn(name = "CD_ID", nullable = false, updatable = true) })
	public Set<User> getOwners() {
		return this.owners;
	}

	public void setOwners(final Set<User> owners) {
		this.owners = owners;
	}

	public void addOwner(final User user) {
		this.owners.add(user);
	}

	@Column(name = "PICTURE", nullable = true)
	public byte[] getPicture() {
		return this.picture;
	}

	public void setPicture(final byte[] picture) {
		this.picture = picture;
	}
}
