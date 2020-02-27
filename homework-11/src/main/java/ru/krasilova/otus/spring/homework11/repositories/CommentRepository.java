package ru.krasilova.otus.spring.homework11.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.krasilova.otus.spring.homework11.models.Comment;

public interface CommentRepository
        extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findByBookId(String id);
    Flux<Comment> findAll();
    Mono<Comment> save(Mono<Comment> comment);
    Mono<Boolean> existsByBookId(String bookId);
}
