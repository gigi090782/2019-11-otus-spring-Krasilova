package ru.krasilova.otus.spring.homework7.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.krasilova.otus.spring.homework7.models.Book;

import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {

    Book findById(long id);
    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAll();

    List<Book> findAllByAuthorId(Long id);

    List<Book> findAllByAuthorLastName(String lastName);

    List<Book> findAllByGenreName(String genreName);

}
