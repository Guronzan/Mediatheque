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

import lombok.Data;
import lombok.NoArgsConstructor;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "movie", uniqueConstraints = { @UniqueConstraint(columnNames = "MOVIE_ID") })
@Data
@NoArgsConstructor
public class Movie extends AbstractPersistentObject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MOVIE_ID", unique = true, nullable = false)
    private int movieId;

    @Column(name = "TITLE", unique = true, nullable = false, length = 40)
    private String title;

    @Column(name = "DIRECTOR_NAME", unique = true, nullable = true, length = 30)
    private String directorName;

    @Column(name = "RELEASE_DATE", nullable = false, length = 20)
    private Date releaseDate = new Date();

    @Column(name = "OWNED_DVD", nullable = false)
    private boolean ownedDVD = false;

    @Column(name = "SEASON", nullable = true)
    private Integer season;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "movies")
    private List<User> owners = new ArrayList<>();

    @Column(name = "PICTURE", nullable = true)
    private byte[] picture;

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

    public void setPicture(final byte[] picture) {
        this.picture = Arrays.copyOf(picture, picture.length);
    }
}
