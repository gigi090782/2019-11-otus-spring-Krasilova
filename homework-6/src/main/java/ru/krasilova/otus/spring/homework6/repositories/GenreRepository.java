package ru.krasilova.otus.spring.homework6.repositories;

import ru.krasilova.otus.spring.homework6.models.Genre;

import java.util.List;

public interface GenreRepository {
    int count();
    Genre save (Genre genre);
  //  Optional<Genre> findById(long id);
    Genre findById(long id);
    List<Genre> getAll();
    void deleteById(long id);

}
