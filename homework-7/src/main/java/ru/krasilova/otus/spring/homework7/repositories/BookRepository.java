package ru.krasilova.otus.spring.homework7.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.krasilova.otus.spring.homework7.models.Author;
import ru.krasilova.otus.spring.homework7.models.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>
{

    Book findById(long id);
    List<Book> findByName(String name);
    List<Book> findAll();
    List<Book> findAllByAuthorId(Long id);
    List<Book> findAllByAuthorLastName(String lastName);
    List<Book> findAllByGenreName(String genreName);
    void deleteById(long id);


}
