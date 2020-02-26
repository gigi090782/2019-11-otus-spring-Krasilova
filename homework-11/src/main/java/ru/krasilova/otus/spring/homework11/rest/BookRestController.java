package ru.krasilova.otus.spring.homework11.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.krasilova.otus.spring.homework11.models.Book;
import ru.krasilova.otus.spring.homework11.repositories.BookRepository;
import ru.krasilova.otus.spring.homework11.repositories.CommentRepository;

@RestController
public class BookRestController {

    private final BookRepository repository;
    private final CommentRepository commentRepository;

    public BookRestController(BookRepository repository, CommentRepository commentRepository) {
        this.repository = repository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/api/books")
    public Flux<Book> getViewBooks() {
        return repository.findAll();
    }

    @GetMapping("/api/books/{id}")
    public Mono<Book> byId(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @PostMapping("/api/books")
    public Mono<Book> saveBook(@RequestBody Mono<Book> monoBook) {
        return repository.save(monoBook);
    }


    @GetMapping("/api/books/find/{name}")
    public Flux<Book> byName(@PathVariable("name") String name) {
        return repository.findAllByAuthorLastName(name);
    }


    @DeleteMapping("/api/books/{id}")
    public Mono<ResponseEntity<Void>> deleteBookById(@PathVariable("id") String id) {

        return repository.existsById(id)

                .flatMap(
                        existingBook ->
                                commentRepository.existsByBookId(id)
                ).filter(isExist -> !isExist)
                .flatMap(isExist ->
                        repository.deleteById(id)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
