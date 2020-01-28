package ru.krasilova.otus.spring.homework8.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.krasilova.otus.spring.homework8.models.Book;

import java.util.List;


public interface BookRepository extends MongoRepository<Book, String>,BookCustomRepository {

    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAll();
    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllByAuthorId(String id);
    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllByAuthorLastName(String lastName);
    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllByGenreName(String genreName);

}
