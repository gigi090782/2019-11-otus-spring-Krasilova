package ru.krasilova.otus.spring.homework11.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.krasilova.otus.spring.homework11.models.Author;



public interface AuthorRepository
        extends ReactiveMongoRepository<Author, String> {

    Flux<Author> findByLastName(String lastName);
    Mono<Author> save(Mono<Author> author);

}


