package ru.krasilova.otus.spring.homework18.rest;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework18.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework18.models.Author;
import ru.krasilova.otus.spring.homework18.models.Book;
import ru.krasilova.otus.spring.homework18.models.Genre;
import ru.krasilova.otus.spring.homework18.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework18.repositories.BookRepository;
import ru.krasilova.otus.spring.homework18.repositories.CommentRepository;
import ru.krasilova.otus.spring.homework18.repositories.GenreRepository;
import static ru.krasilova.otus.spring.homework18.utils.Util.GetRandomSleep;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@DefaultProperties(defaultFallback = "getBookWaitResponse")
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

    @HystrixCommand(fallbackMethod = "getReserveListBooks")
    @GetMapping("/")
    public String getListBook(Model model) throws InterruptedException {
        GetRandomSleep();
        List<Book> books = repository.findAll();
        model.addAttribute("books", books);
        return "listBooks";
    }

    public String getReserveListBooks(Model model) {
        GetRandomSleep();
        List<Book> books = new ArrayList<Book>();
        model.addAttribute("books", books);
        return "listBooks";
    }

    @HystrixCommand
    @GetMapping("/editbook")
    public String getEditBook(@RequestParam("id") long id, Model model) {
        GetRandomSleep();
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("allauthors", authors);
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("allgenres", genres);
        return "editBook";
    }


    public String getReserveEditBook(@RequestParam("id") long id, Model model) {
        GetRandomSleep();
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("allauthors", authors);
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("allgenres", genres);
        return "editBook";
    }

    @HystrixCommand
    @GetMapping("/addbook")
    public String getAddBook(Model model) throws ParseException {
        GetRandomSleep();
        Book book = new Book();
        model.addAttribute("book", book);
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("allauthors", authors);
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("allgenres", genres);
        return "editBook";
    }

    @HystrixCommand
    @PostMapping("/deletebook")
    public String postDeleteBook(@RequestParam("id") long id, Model model) {
        GetRandomSleep();
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        commentRepository.deleteAllByBookId(id);
        repository.deleteById(id);
        return "redirect:/";
    }

    @HystrixCommand
    @PostMapping("/saveBook")
    public String postSaveBook(
            @RequestParam("id") long id,
            @RequestParam("name") String name,
            @RequestParam("genre") Genre genre,
            @RequestParam("author") Author author,
            Model model
    ) {
        GetRandomSleep();
        Book book;
        if (id != 0) {
            book = repository.findById(id).orElseThrow(NotFoundException::new);
            book.setAuthor(author);
            book.setName(name);
            book.setGenre(genre);

        } else {
            book = new Book(name, author, genre);

        }

        repository.save(book);
        return "redirect:/";
    }

    public String getBookWaitResponse() {
        return "errorWait";
    }


}
