package ru.krasilova.otus.spring.homework5.repositories;

import ru.krasilova.otus.spring.homework5.models.Book;

import java.util.Date;
import java.util.List;

public interface BookReposiry {
    int count();
    Long insert (String bookName, Long authorId, Long genreId);
    Book getById(long id);
    Book getByName(String name);
    List<Book> findAllWithAllInfo();

    List<Book> findAllByAuthorID(Long id);

    List<Book> findAllByAuthorLastName(String lastName);

    List<Book> findAllByGenreName(String genreName);


}
