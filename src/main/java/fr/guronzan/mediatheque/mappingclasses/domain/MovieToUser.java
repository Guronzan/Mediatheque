package fr.guronzan.mediatheque.mappingclasses.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "movietouser", uniqueConstraints = {
        @UniqueConstraint(columnNames = "RELATION_ID"),
        @UniqueConstraint(columnNames = { "MOVIE_ID", "USER_ID" }) })
@Data
@NoArgsConstructor
public class MovieToUser implements DomainObject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "RELATION_ID", unique = true, nullable = false)
    private int relationID;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "MOVIE_ID", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "OWNED_DVD", nullable = false, columnDefinition = "boolean default false")
    private boolean isOwnedDVD = false;

    /**
     * @param user
     * @param movie
     */
    public MovieToUser(final User user, final Movie movie) {
        this.user = user;
        this.movie = movie;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.guronzan.mediatheque.mappingclasses.domain.DomainObject#getLblExpression
     * ()
     */
    @Override
    public String getLblExpression() {
        return this.movie + " - " + this.user;
    }

}
