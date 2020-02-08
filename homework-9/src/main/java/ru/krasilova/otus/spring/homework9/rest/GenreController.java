package ru.krasilova.otus.spring.homework9.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework9.exceptions.GenreHasBooksException;
import ru.krasilova.otus.spring.homework9.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework9.models.Genre;
import ru.krasilova.otus.spring.homework9.repositories.BookRepository;
import ru.krasilova.otus.spring.homework9.repositories.GenreRepository;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@Transactional
public class GenreController {

    private final GenreRepository repository;
    private final BookRepository bookRepository;
    @Autowired
    public GenreController(GenreRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/genres")
    public String listGenres(Model model) {
        List<Genre> genres = repository.findAll();
        model.addAttribute("genres", genres);
        return "listGenres";
    }

    @GetMapping("/editgenre")
    public String editGenre(@RequestParam("id") long id, Model model) {
        Genre genre = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("genre", genre);
        return "editGenre";
    }

    @GetMapping("/addgenre")
    public String addGenre( Model model) {
        Genre genre = new Genre("");
        model.addAttribute("genre", genre);
        return "editGenre";
    }


    @PostMapping("/savegenre")
    public String saveGenre(
            @RequestParam("id") long id,
            @RequestParam("name") String name,
            Model model
    ) { Genre genre;
        if (id!=0) {
            genre = repository.findById(id).orElseThrow(NotFoundException::new);
            genre.setName(name);

        } else
        {
            genre = new Genre(name);

        }
        repository.save(genre);

        List<Genre> genres = repository.findAll();
        model.addAttribute("genres", genres);
        return "listGenres";
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
        return "listGenres";
    }


}

