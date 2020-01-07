package ru.krasilova.otus.spring.homework6.repositories;

import ru.krasilova.otus.spring.homework6.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository
{
    int count();
    Author save(Author author);
    //Optional<Author> findById(long id);
    Author findById(long id);
    List<Author> findByLastName(String lastName);
    List<Author> getAll();
    void deleteById(long id);
}


