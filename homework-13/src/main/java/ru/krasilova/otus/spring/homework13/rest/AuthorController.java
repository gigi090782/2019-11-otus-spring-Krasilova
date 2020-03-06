package ru.krasilova.otus.spring.homework13.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework13.exceptions.AuthorHasBooksException;
import ru.krasilova.otus.spring.homework13.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework13.models.Author;
import ru.krasilova.otus.spring.homework13.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework13.repositories.BookRepository;

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
    public String getListAuthors(Model model) {
        List<Author> authors = repository.findAll();
        model.addAttribute("authors", authors);
        return "listAuthors";
    }

    @GetMapping("/editauthor")
    public String getEditAuthor(@RequestParam("id") long id, Model model) {
        Author author = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("author", author);
        return "editAuthor";
    }

    @GetMapping("/addauthor")
    public String getAddComment(Model model) throws ParseException {

        Author author = new Author("", "", "", "01.01.1960");
        model.addAttribute("author", author);
        return "editAuthor";
    }

    @PostMapping("/saveauthor")
    public String postSaveAuthor(
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
