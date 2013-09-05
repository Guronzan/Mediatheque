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
import lombok.Getter;
import lombok.NoArgsConstructor;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "book", uniqueConstraints = { @UniqueConstraint(columnNames = "BOOK_ID") })
@Data
@NoArgsConstructor
public class Book extends AbstractPersistentObject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "BOOK_ID", unique = true, nullable = false)
    private int bookId;

    @Column(name = "TITLE", unique = true, nullable = false, length = 40)
    private String title;

    @Column(name = "AUTHOR_NAME", unique = true, nullable = true, length = 30)
    private String authorName;

    @Column(name = "RELEASE_DATE", nullable = false, length = 20)
    private final Date releaseDate = new Date();

    @Column(name = "EDITOR", nullable = false, length = 20)
    private String editor;

    @Column(name = "PICTURE", nullable = true)
    @Getter
    private byte[] picture;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "books")
    private final List<User> owners = new ArrayList<>();

    public Book(final String title) {
        this.title = title;
    }

    public void setPicture(final byte[] picture) {
        if (picture != null) {
            this.picture = Arrays.copyOf(picture, picture.length);
        } else {
            this.picture = null;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
