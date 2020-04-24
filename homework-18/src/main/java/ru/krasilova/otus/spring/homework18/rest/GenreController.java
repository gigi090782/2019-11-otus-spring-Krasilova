package ru.krasilova.otus.spring.homework18.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework18.exceptions.GenreHasBooksException;
import ru.krasilova.otus.spring.homework18.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework18.models.Genre;
import ru.krasilova.otus.spring.homework18.services.LibraryService;


import java.util.List;

@Controller
public class GenreController {

    private final LibraryService libraryService;

    @Autowired
    public GenreController(LibraryService libraryService) {
        this.libraryService = libraryService;

    }

    @GetMapping("/genres")
    public String getListGenres(Model model) {
        List<Genre> genres = libraryService.getAllGenres();
        model.addAttribute("genres", genres);
        return "listGenres";
    }

    @GetMapping("/editgenre")
    public String getEditGenre(@RequestParam("id") long id, Model model) {
        Genre genre = libraryService.findGenreById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("genre", genre);
        return "editGenre";
    }

    @GetMapping("/addgenre")
    public String getAddGenre(Model model) {
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
            genre = libraryService.findGenreById(id).orElseThrow(NotFoundException::new);
            genre.setName(name);

        } else {
            genre = new Genre(name);

        }
        libraryService.saveGenre(genre);

        List<Genre> genres = libraryService.getAllGenres();
        model.addAttribute("genres", genres);
        return "redirect:/genres";
    }

    @PostMapping("/deletegenre")
    public String postDeleteGenre(@RequestParam("id") long id, Model model) throws GenreHasBooksException {
        if (libraryService.existsBookByGenreId(id)) {
            throw new GenreHasBooksException("У данного жанра есть книги, удаление невозможно!");
        }
        Genre genre = libraryService.findGenreById(id).orElseThrow(NotFoundException::new);
        libraryService.deleteGenreById(id);
        List<Genre> genres = libraryService.getAllGenres();
        model.addAttribute("genres", genres);
        return "redirect:/genres";
    }



}

