package ru.krasilova.otus.spring.homework16.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data

@AllArgsConstructor
@Entity
@Table(name = "books")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "allJoin", attributeNodes = {
                @NamedAttributeNode("genre"),
                @NamedAttributeNode("author")
        })})

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false, unique = false)
    private String name;
    @ManyToOne(targetEntity = Author.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
    @ManyToOne(targetEntity = Genre.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Book(){};
    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.genre = genre;
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }


    public void setAuthor(Author author) {
        this.author = author;
    }


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }


}
