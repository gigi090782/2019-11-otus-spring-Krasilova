package ru.krasilova.otus.spring.homework10.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework10.exceptions.GenreHasBooksException;
import ru.krasilova.otus.spring.homework10.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework10.models.Author;
import ru.krasilova.otus.spring.homework10.repositories.BookRepository;
import ru.krasilova.otus.spring.homework10.repositories.GenreRepository;
import ru.krasilova.otus.spring.homework10.models.Genre;
import ru.krasilova.otus.spring.homework10.rest.dto.GenreDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Controller

public class GenreController {

    private final GenreRepository repository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreController(GenreRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }


    @GetMapping("/genres")

    public String getViewForListGenres(Model model) {
        List<Genre> genres = repository.findAll();
        model.addAttribute("genres", genres);
        return "listGenres";
    }



    @GetMapping("/editgenre")
    public String getViewForEditGenre(@RequestParam("id") long id, Model model) {
        Genre genre = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("genre", genre);
        return "editGenre";
    }

    @GetMapping("/addgenre")
    public String getViewForAddGenre(Model model) {
        Genre genre = new Genre("");
        model.addAttribute("genre", genre);
        return "editGenre";
    }


    @PostMapping("/savegenre")
    public String postSaveGenre(
            @RequestParam("id") long id,
            @RequestParam("name") String name,
            Model model
    ) {
        Genre genre;
        if (id != 0) {
            genre = repository.findById(id).orElseThrow(NotFoundException::new);
            genre.setName(name);

        } else {
            genre = new Genre(name);

        }
        repository.save(genre);

        List<Genre> genres = repository.findAll();
        model.addAttribute("genres", genres);
        return "redirect:/genres";
    }


    @PostMapping("/deletegenre")
    public String deleteGenre(@RequestParam("id") long id, Model model) throws GenreHasBooksException {
        if (bookRepository.existsByGenreId(id)) {
            throw new GenreHasBooksException("У данного жанра есть книги, удаление невозможно!");
        }
        Genre genre = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.deleteById(id);
        List<Genre> genres = repository.findAll();
        model.addAttribute("genres", genres);
        return "redirect:/genres";
    }


}

