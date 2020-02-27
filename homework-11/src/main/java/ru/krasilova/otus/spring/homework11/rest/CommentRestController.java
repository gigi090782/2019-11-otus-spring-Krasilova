
package ru.krasilova.otus.spring.homework11.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.krasilova.otus.spring.homework11.models.Comment;
import ru.krasilova.otus.spring.homework11.repositories.CommentRepository;


@RestController
public class CommentRestController {

    private final CommentRepository repository;

    public CommentRestController(CommentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/comments")
    public Flux<Comment> getViewComments() {
        return repository.findAll();
    }

    @GetMapping("/api/comments/{id}")
    public Mono<Comment> byId(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @PostMapping("/api/comments")
    public Mono<Comment> saveComment(@RequestBody Mono<Comment> monoComment) {
        return repository.save(monoComment);
    }


    @GetMapping("/api/comments/findbybookid/")
    public Flux<Comment> byName(@RequestParam("bookid") String bookid) {
        return repository.findByBookId(bookid);
    }


    @DeleteMapping("/api/comments/{id}")
    public Mono<ResponseEntity<Void>> deleteCommentById(@PathVariable("id") String id) {


        return repository.existsById(id)
                .flatMap(existingComment ->
                        repository.deleteById(id)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
