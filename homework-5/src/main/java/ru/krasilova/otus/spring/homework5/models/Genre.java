package ru.krasilova.otus.spring.homework5.models;

public class Genre {

    private final long id;
    private final String name;

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }


}
