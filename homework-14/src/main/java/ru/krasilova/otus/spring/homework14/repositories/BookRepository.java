package ru.krasilova.otus.spring.homework14.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.krasilova.otus.spring.homework14.models.Book;

import java.util.List;


public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAll();
    List<Book> findAllByAuthorId(String id);
    List<Book> findAllByAuthorLastName(String lastName);
    List<Book> findAllByGenreName(String genreName);
    boolean existsByAuthorId(String authorId);

}
