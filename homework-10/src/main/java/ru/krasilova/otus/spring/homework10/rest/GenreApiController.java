package ru.krasilova.otus.spring.homework10.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public GenreApiController(GenreRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }


    @GetMapping("/api/listgenres")
    public List<GenreDto> getListGenresApi() {
        return repository.findAll().stream().map(GenreDto::toDto)
                .collect(Collectors.toList());

    }

    @PostMapping("/api/deleteGenre")
    public void  deleteGenreApi(@RequestParam("id") long id) throws GenreHasBooksException {
        if (bookRepository.existsByGenreId(id)) {
            throw new GenreHasBooksException("У данного жанра есть книги, удаление невозможно!");
        }
        Genre genre = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.deleteById(id);


    }

    @GetMapping("/api/editGenreApi")
    public String getViewForEditGenre(@RequestParam("id") long id, Model model) {
        Genre genre = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("genre", genre);
        return "editGenre";
    }

}
