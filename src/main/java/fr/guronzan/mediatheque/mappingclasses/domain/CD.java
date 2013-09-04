package fr.guronzan.mediatheque.mappingclasses.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
    private List<User> owners = new ArrayList<>();

    public CD(final int cdId, final String title, final String authorName,
            final Date releaseDate, final CDKindType kind,
            final byte[] picture, final List<User> owners) {
        this.cdId = cdId;
        this.title = title;
        this.authorName = authorName;
        this.releaseDate = releaseDate;
        this.kind = kind;
        this.picture = Arrays.copyOf(picture, picture.length);
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cds")
    public List<User> getOwners() {
        return this.owners;
    }

    public void setOwners(final List<User> owners) {
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
        this.picture = Arrays.copyOf(picture, picture.length);
    }
}
