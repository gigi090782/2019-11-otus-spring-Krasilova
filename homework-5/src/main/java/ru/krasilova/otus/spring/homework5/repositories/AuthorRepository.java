package ru.krasilova.otus.spring.homework5.repositories;

import ru.krasilova.otus.spring.homework5.models.Author;

import java.util.Date;
import java.util.List;

public interface AuthorRepository
{
    int count();
    Long insert (String firstName, String secondName, String lastName, Date birthdate);
    Author getById(long id);
    List<Author> getAll();
}

