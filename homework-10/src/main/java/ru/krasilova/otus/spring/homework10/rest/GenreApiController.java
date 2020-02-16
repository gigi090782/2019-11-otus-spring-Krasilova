package ru.krasilova.otus.spring.homework10.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework10.exceptions.GenreHasBooksException;
import ru.krasilova.otus.spring.homework10.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework10.models.Genre;
import ru.krasilova.otus.spring.homework10.repositories.BookRepository;
import ru.krasilova.otus.spring.homework10.repositories.GenreRepository;
import ru.krasilova.otus.spring.homework10.rest.dto.GenreDto;
import java.util.List;
import java.util.stream.Collectors;

@RestController

public class GenreApiController {

    private final GenreRepository repository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreApiController(GenreRepository genreRepository, BookRepository bookRepository) {
        this.repository = genreRepository;
        this.bookRepository = bookRepository;
    }


    @GetMapping("/api/genres")
    public List<GenreDto> getListGenresApi() {
        return repository.findAll().stream().map(GenreDto::toDto)
                .collect(Collectors.toList());

    }

    @DeleteMapping("/api/genres/{id}")
    public void  deleteGenreApi(@PathVariable("id") long id) throws GenreHasBooksException {
        if (bookRepository.existsByGenreId(id)) {
            throw new GenreHasBooksException("У данного жанра есть книги, удаление невозможно!");
        }
        Genre genre = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.deleteById(id);


    }



}
