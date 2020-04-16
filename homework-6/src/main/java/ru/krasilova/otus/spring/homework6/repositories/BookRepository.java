package ru.krasilova.otus.spring.homework6.repositories;

import ru.krasilova.otus.spring.homework6.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    //int count();
    //Long insert (String bookName, Long authorId, Long genreId);
    //Optional<Book> getById(long id);
    Book findById(long id);
    List<Book> findByName(String name);
    List<Book> findAllWithAllInfo();
    Book save(Book book);
    List<Book> findAllByAuthorID(Long id);

    List<Book> findAllByAuthorLastName(String lastName);

    List<Book> findAllByGenreName(String genreName);
    void deleteById(long id);

}
