package ru.krasilova.otus.spring.homework11.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.krasilova.otus.spring.homework11.models.Book;


public interface BookRepository
        extends ReactiveMongoRepository<Book, String> {


    Flux<Book> findAll();

    Flux<Book> findAllByAuthorId(String id);

    Flux<Book> findAllByAuthorLastName(String lastName);

    Flux<Book> findAllByGenreName(String genreName);

    Mono<Boolean> existsByAuthorId(String authorId);

    Mono<Boolean> existsByGenreId(String genreId);

    Mono<Book> save(Mono<Book> book);

}
