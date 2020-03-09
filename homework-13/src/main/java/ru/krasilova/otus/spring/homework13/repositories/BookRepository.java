package ru.krasilova.otus.spring.homework13.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.krasilova.otus.spring.homework13.models.Book;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BookRepository extends CrudRepository<Book, Long> {
    @PostFilter("hasPermission(filterObject, 'READ')")
    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAll();
    //Book findById(long id);
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllByAuthorId(Long id);
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllByAuthorLastName(String lastName);
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @EntityGraph(value = "allJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAllByGenreName(String genreName);
    boolean existsByAuthorId(long authorId);
    boolean existsByGenreId(long genreId);
    @PreAuthorize("hasPermission(#book, 'WRITE')")
    Book save(@Param("book")Book book);
    @PostFilter("hasPermission(filterObject, 'READ')")
    long count();
}

