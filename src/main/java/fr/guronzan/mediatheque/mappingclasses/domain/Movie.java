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
@Table(name = "movie", catalog = "mediatheque", uniqueConstraints = { @UniqueConstraint(columnNames = "MOVIE_ID") })
public class Movie extends AbstractPersistentObject {

    private int movieId;
    private String title;
    private String directorName;
    private Date releaseDate = new Date();
    private boolean ownedDVD = false;
    private Integer season;
    private List<User> owners = new ArrayList<>();
    private byte[] picture;

    public Movie() {
        // Empty constructor
    }

    public Movie(final String title, final String director,
            final Date releaseDate) {
        this.title = title;
        this.directorName = director;
        this.releaseDate = releaseDate;
        this.ownedDVD = false;
    }

    public Movie(final String title) {
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MOVIE_ID", unique = true, nullable = false)
    public int getMovieId() {
        return this.movieId;
    }

    public void setMovieId(final int movieId) {
        this.movieId = movieId;
    }

    @Column(name = "TITLE", unique = true, nullable = false, length = 40)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Column(name = "DIRECTOR_NAME", unique = true, nullable = true, length = 30)
    public String getDirectorName() {
        return this.directorName;
    }

    public void setDirectorName(final String directorName) {
        this.directorName = directorName;
    }

    @Column(name = "RELEASE_DATE", nullable = false, length = 20)
    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(final Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(name = "OWNED_DVD", nullable = false)
    public boolean isOwnedDVD() {
        return this.ownedDVD;
    }

    public void setOwnedDVD(final boolean ownedDVD) {
        this.ownedDVD = ownedDVD;
    }

    @Column(name = "SEASON", nullable = true)
    public Integer getSeason() {
        return this.season;
    }

    public void setSeason(final Integer season) {
        this.season = season;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "movies")
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
