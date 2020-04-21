package ru.krasilova.otus.spring.homework18.rest;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework18.exceptions.GenreHasBooksException;
import ru.krasilova.otus.spring.homework18.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework18.models.Genre;
import ru.krasilova.otus.spring.homework18.repositories.BookRepository;
import ru.krasilova.otus.spring.homework18.repositories.GenreRepository;

import static ru.krasilova.otus.spring.homework18.utils.Util.GetRandomSleep;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@Transactional
@DefaultProperties(defaultFallback = "getGenreWaitResponse")
public class GenreController {

    private final GenreRepository repository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreController(GenreRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @HystrixCommand
    @GetMapping("/genres")
    public String getListGenres(Model model) {
        GetRandomSleep();
        List<Genre> genres = repository.findAll();
        model.addAttribute("genres", genres);
        return "listGenres";
    }

    @HystrixCommand
    @GetMapping("/editgenre")
    public String getEditGenre(@RequestParam("id") long id, Model model) {
        GetRandomSleep();
        Genre genre = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("genre", genre);
        return "editGenre";
    }

    @HystrixCommand
    @GetMapping("/addgenre")
    public String getAddGenre(Model model) {
        GetRandomSleep();
        Genre genre = new Genre("");
        model.addAttribute("genre", genre);
        return "editGenre";
    }

    @HystrixCommand
    @PostMapping("/savegenre")
    public String postSaveGenre(
            @RequestParam("id") long id,
            @RequestParam("name") String name,
            Model model
    ) {
        GetRandomSleep();
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

    @HystrixCommand
    @PostMapping("/deletegenre")
    public String postDeleteGenre(@RequestParam("id") long id, Model model) throws GenreHasBooksException {
        GetRandomSleep();
        if (bookRepository.existsByGenreId(id)) {
            throw new GenreHasBooksException("У данного жанра есть книги, удаление невозможно!");
        }
        Genre genre = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.deleteById(id);
        List<Genre> genres = repository.findAll();
        model.addAttribute("genres", genres);
        return "redirect:/genres";
    }

    public String getGenreWaitResponse() {
        return "errorWait";
    }


}

