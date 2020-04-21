package ru.krasilova.otus.spring.homework18.rest;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.krasilova.otus.spring.homework18.exceptions.AuthorHasBooksException;
import ru.krasilova.otus.spring.homework18.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework18.models.Author;
import ru.krasilova.otus.spring.homework18.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework18.repositories.BookRepository;

import java.text.ParseException;
import java.util.*;
import static ru.krasilova.otus.spring.homework18.utils.Util.GetRandomSleep;

@Controller
@DefaultProperties(defaultFallback = "getAuthorWaitResponse")
public class AuthorController {

    private final AuthorRepository repository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorController(AuthorRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @HystrixCommand(fallbackMethod = "getReserveListAuthors")
    @GetMapping("/authors")
    public String getListAuthors(Model model) throws InterruptedException {
        GetRandomSleep();
        List<Author> authors = repository.findAll();
        model.addAttribute("authors", authors);
        return "listAuthors";

    }



    @HystrixCommand
    @GetMapping("/editauthor")
    public String getEditAuthor(@RequestParam("id") long id, Model model) throws InterruptedException {
        GetRandomSleep();
        Author author = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("author", author);
        return "editAuthor";
    }

    @HystrixCommand
    @GetMapping("/addauthor")
    public String getAddAuthor(Model model) throws ParseException {
        GetRandomSleep();
        Author author = new Author("", "", "", "01.01.1960");
        model.addAttribute("author", author);
        return "editAuthor";
    }

    @HystrixCommand
    @PostMapping("/saveauthor")
    public String postSaveAuthor(
            @RequestParam("id") long id,
            @RequestParam("lastname") String lastname,
            @RequestParam("firstname") String firstname,
            @RequestParam("secondname") String secondname,
            @RequestParam("birthdate") String birthdate,
            Model model
    ) {
        GetRandomSleep();
        Author author;
        if (id != 0) {
            author = repository.findById(id).orElseThrow(NotFoundException::new);
            author.setLastName(lastname);
            author.setFirstName(firstname);
            author.setSecondName(secondname);
            author.setBirthDate(birthdate);

        } else {
            author = new Author(firstname, secondname, lastname, birthdate);

        }

        repository.save(author);
        return "redirect:/authors";
    }

    @HystrixCommand(fallbackMethod = "getAuthorWaitResponse",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")})
    @PostMapping("/deleteauthor")
    public String postDeleteAuthor(@RequestParam("id") long id, Model model) throws AuthorHasBooksException {
        GetRandomSleep();
        if (bookRepository.existsByAuthorId(id)) {
            throw new AuthorHasBooksException("У автора есть книги, удаление невозможно!");
        }
        Author author = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.deleteById(id);
        return "redirect:/authors";
    }


    @RequestMapping("/errorWait")
    public String getAuthorWaitResponse() {
        return "errorWait";
    }


    public String getReserveListAuthors(Model model) {
        Author author = new Author("Тест", "Тест", "Тест", "01.01.1990");
        List<Author> authors = new ArrayList<Author>();
        authors.add(author);
        model.addAttribute("authors", authors);
        return "listAuthors";
    }



}
