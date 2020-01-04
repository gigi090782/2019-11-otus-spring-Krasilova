package ru.krasilova.otus.spring.homework5.models;

import java.util.Date;

public class Book {

    private final String name;
    private long id;
    private final Author author;
    private final Genre genre;

    public Book(long id, String name, Author author, Genre genre){
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }


    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
