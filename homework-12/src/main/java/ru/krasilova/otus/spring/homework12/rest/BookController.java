package ru.krasilova.otus.spring.homework12.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework12.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework12.models.Author;
import ru.krasilova.otus.spring.homework12.models.Book;
import ru.krasilova.otus.spring.homework12.models.Genre;
import ru.krasilova.otus.spring.homework12.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework12.repositories.BookRepository;
import ru.krasilova.otus.spring.homework12.repositories.CommentRepository;
import ru.krasilova.otus.spring.homework12.repositories.GenreRepository;

import java.text.ParseException;
import java.util.List;

@Controller

public class BookController {

    private final BookRepository repository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public BookController(BookRepository repository, GenreRepository genreRepository,
                          AuthorRepository authorRepository, CommentRepository commentRepository) {
        this.repository = repository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
    }

    //@GetMapping("/")

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getListBook(Model model) {
        List<Book> books = repository.findAll();
        model.addAttribute("books", books);
        return "listBooks";
    }

    @GetMapping("/editbook")
    public String getEditBook(@RequestParam("id") long id, Model model) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("allauthors", authors);
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("allgenres", genres);
        return "editBook";
    }


    @GetMapping("/addbook")
    public String getAddBook( Model model) throws ParseException {
        Book book = new Book();
        model.addAttribute("book", book);
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("allauthors", authors);
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("allgenres", genres);
        return "editBook";
    }

    @PostMapping("/deletebook")
    public String postDeleteBook(@RequestParam("id") long id, Model model) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        commentRepository.deleteAllByBookId(id);
        repository.deleteById(id);
        return "redirect:/";
    }


    @PostMapping("/saveBook")
    public String postSaveBook(
            @RequestParam("id") long id,
            @RequestParam("name") String name,
            @RequestParam("genre") Genre genre,
            @RequestParam("author") Author author,
            Model model
    ) {Book book;

        if (id!=0) {
            book = repository.findById(id).orElseThrow(NotFoundException::new);
            book.setAuthor(author);
            book.setName(name);
            book.setGenre(genre);

        } else
        {
            book = new Book(name, author,genre);

        }

        repository.save(book);
        return "redirect:/";
    }
}
