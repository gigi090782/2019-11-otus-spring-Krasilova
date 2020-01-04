package ru.krasilova.otus.spring.homework5.repositories;

import ru.krasilova.otus.spring.homework5.models.Genre;

import java.util.List;

public interface GenreRepository {
    int count();
    Long insert (String genreName);
    Genre getById(long id);
    List<Genre> getAll();
}

