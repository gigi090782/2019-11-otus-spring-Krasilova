package ru.krasilova.otus.spring.homework9.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework9.models.Author;
import ru.krasilova.otus.spring.homework9.models.Book;
import ru.krasilova.otus.spring.homework9.models.Genre;
import ru.krasilova.otus.spring.homework9.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework9.repositories.BookRepository;
import ru.krasilova.otus.spring.homework9.repositories.GenreRepository;


import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@Controller
public class BookController {

    private final BookRepository repository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookController(BookRepository repository, GenreRepository genreRepository, AuthorRepository authorRepository ) {
        this.repository = repository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;

    }
/*
    @ModelAttribute("allGenres")
    public List<Genre> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genres;
    }

    @ModelAttribute("allAuthors")
    public List<Author> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

 */

    @GetMapping("/")
    public String listBook(Model model) {
        List<Book> books = repository.findAll();
        model.addAttribute("books", books);
        return "listBooks";
    }

    @GetMapping("/editbook")
    public String editBook(@RequestParam("id") long id, Model model) {
       Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("allauthors", authors);
        return "editBook";
    }

    @GetMapping("/saveBook")
    public String saveBook(
            @RequestParam("id") long id,
            @RequestParam("name") String name,
            Model model
    ) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        book.setName(name);
        repository.save(book);
       List<Book> books = repository.findAll();
        model.addAttribute("books", books);
        return "listBooks";
    }
}
