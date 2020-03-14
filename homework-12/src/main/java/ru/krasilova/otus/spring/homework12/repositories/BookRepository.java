package ru.krasilova.otus.spring.homework12.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.krasilova.otus.spring.homework12.models.Book;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BookRepository extends CrudRepository<Book, Long> {
    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAll();
    //Book findById(long id);
    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllByAuthorId(Long id);
    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllByAuthorLastName(String lastName);
    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllByGenreName(String genreName);
    boolean existsByAuthorId(long authorId);
    boolean existsByGenreId(long genreId);

}
