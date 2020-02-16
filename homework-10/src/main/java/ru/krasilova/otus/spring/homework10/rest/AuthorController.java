package ru.krasilova.otus.spring.homework10.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework10.exceptions.AuthorHasBooksException;
import ru.krasilova.otus.spring.homework10.repositories.BookRepository;
import ru.krasilova.otus.spring.homework10.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework10.models.Author;
import ru.krasilova.otus.spring.homework10.repositories.AuthorRepository;

import java.text.ParseException;
import java.util.List;

@Controller
public class AuthorController {

    private final AuthorRepository repository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorController(AuthorRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/authors")
    public String  getViewForListAuthors(Model model) {
        List<Author> authors = repository.findAll();
        model.addAttribute("authors", authors);
        return "listAuthors";
    }

    @GetMapping("/editauthor")
    public String getViewForEditAuthor(@RequestParam("id") long id, Model model) {
        Author author = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("author", author);
        return "editAuthor";
    }

    @GetMapping("/addauthor")
    public String getViewForAddAuthor(Model model) throws ParseException {

        Author author = new Author("", "", "", "01.01.1960");
        model.addAttribute("author", author);
        return "editAuthor";
    }

    @PostMapping("/saveauthor")
    public String saveAuthor(
            @RequestParam("id") long id,
            @RequestParam("lastname") String lastname,
            @RequestParam("firstname") String firstname,
            @RequestParam("secondname") String secondname,
            @RequestParam("birthdate") String birthdate,
            Model model
    ) {
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


    @PostMapping("/deleteauthor")
    public String postDeleteAuthor(@RequestParam("id") long id, Model model) throws AuthorHasBooksException {
        if (bookRepository.existsByAuthorId(id)) {
            throw new AuthorHasBooksException("У автора есть книги, удаление невозможно!");
        }
        Author author = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.deleteById(id);
        return "redirect:/authors";
    }

}
