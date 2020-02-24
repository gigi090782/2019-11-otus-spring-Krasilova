package ru.krasilova.otus.spring.homework11.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.krasilova.otus.spring.homework11.models.Genre;

public interface GenreRepository
        extends ReactiveMongoRepository<Genre, String> {

    Genre findById(long id);
    Flux<Genre> findAll();
    Mono<Genre>  findByName(String name);
    Mono<Genre> save(Mono<Genre> comment);

}
