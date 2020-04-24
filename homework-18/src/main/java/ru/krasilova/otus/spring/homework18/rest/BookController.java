package ru.krasilova.otus.spring.homework18.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework18.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework18.models.Author;
import ru.krasilova.otus.spring.homework18.models.Book;
import ru.krasilova.otus.spring.homework18.models.Genre;
import ru.krasilova.otus.spring.homework18.services.LibraryService;

import java.text.ParseException;
import java.util.List;

@Controller
public class BookController {

    private final LibraryService libraryService;

    @Autowired
    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;

    }

    @GetMapping("/")
    public String getListBook(Model model) throws InterruptedException {
        List<Book> books = libraryService.getAllBooks();
        model.addAttribute("books", books);
        return "listBooks";
    }



    @GetMapping("/editbook")
    public String getEditBook(@RequestParam("id") long id, Model model) {
        Book book = libraryService.findBookById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        List<Author> authors = libraryService.getAllAuthors();
        model.addAttribute("allauthors", authors);
        List<Genre> genres = libraryService.getAllGenres();
        model.addAttribute("allgenres", genres);
        return "editBook";
    }


    @GetMapping("/addbook")
    public String getAddBook(Model model) throws ParseException {
        Book book = new Book();
        model.addAttribute("book", book);
        List<Author> authors = libraryService.getAllAuthors();
        model.addAttribute("allauthors", authors);
        List<Genre> genres = libraryService.getAllGenres();
        model.addAttribute("allgenres", genres);
        return "editBook";
    }

    @PostMapping("/deletebook")
    public String postDeleteBook(@RequestParam("id") long id, Model model) {
        Book book = libraryService.findBookById(id).orElseThrow(NotFoundException::new);
        libraryService.deleteAllCommentsByBookId(id);
        libraryService.deleteBookById(id);
        return "redirect:/";
    }

    @PostMapping("/saveBook")
    public String postSaveBook(
            @RequestParam("id") long id,
            @RequestParam("name") String name,
            @RequestParam("genre") Genre genre,
            @RequestParam("author") Author author,
            Model model
    ) {
        Book book;
        if (id != 0) {
            book = libraryService.findBookById(id).orElseThrow(NotFoundException::new);
            book.setAuthor(author);
            book.setName(name);
            book.setGenre(genre);

        } else {
            book = new Book(name, author, genre);

        }

        libraryService.saveBook(book);
        return "redirect:/";
    }



}
