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
@Table(name = "cd", uniqueConstraints = { @UniqueConstraint(columnNames = "CD_ID") })
@Data
@NoArgsConstructor
public class CD extends AbstractPersistentObject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CD_ID", unique = true, nullable = false)
    private int cdId;

    @Column(name = "TITLE", unique = true, nullable = false, length = 40)
    private String title;

    @Column(name = "AUTHOR_NAME", nullable = true, length = 30)
    private String authorName;

    @Column(name = "RELEASE_DATE", nullable = false, length = 20)
    private Date releaseDate = new Date();

    @Column(name = "KIND", nullable = false, length = 20)
    private CDKindType kind;

    @Column(name = "PICTURE", nullable = true)
    private byte[] picture;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cds")
    private List<User> owners = new ArrayList<>();

    public CD(final String title) {
        this.title = title;
    }

    public void setPicture(final byte[] picture) {
        if (picture != null) {
            this.picture = Arrays.copyOf(picture, picture.length);
        } else {
            this.picture = null;
        }
    }
}
