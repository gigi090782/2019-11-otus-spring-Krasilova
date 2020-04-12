package ru.krasilova.otus.spring.homework17.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.krasilova.otus.spring.homework17.models.Book;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RepositoryRestResource(path = "book")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
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
