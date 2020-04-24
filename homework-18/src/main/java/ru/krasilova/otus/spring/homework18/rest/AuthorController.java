package ru.krasilova.otus.spring.homework18.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.krasilova.otus.spring.homework18.exceptions.AuthorHasBooksException;
import ru.krasilova.otus.spring.homework18.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework18.models.Author;
import ru.krasilova.otus.spring.homework18.services.LibraryService;

import java.text.ParseException;
import java.util.*;


@Controller

public class AuthorController {

    private final LibraryService libraryService;

    @Autowired
    public AuthorController(LibraryService libraryService) {
        this.libraryService = libraryService;

    }

    @GetMapping("/authors")
    public String getListAuthors(Model model) throws InterruptedException {
        List<Author> authors = libraryService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "listAuthors";

    }

    @GetMapping("/editauthor")
    public String getEditAuthor(@RequestParam("id") long id, Model model) throws InterruptedException {
        Author author = libraryService.findAuthorById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("author", author);
        return "editAuthor";
    }

    @GetMapping("/addauthor")
    public String getAddAuthor(Model model) throws ParseException {
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
            author = libraryService.findAuthorById(id).orElseThrow(NotFoundException::new);
            author.setLastName(lastname);
            author.setFirstName(firstname);
            author.setSecondName(secondname);
            author.setBirthDate(birthdate);

        } else {
            author = new Author(firstname, secondname, lastname, birthdate);

        }

        libraryService.saveAuthor(author);
        return "redirect:/authors";
    }


    @PostMapping("/deleteauthor")
    public String postDeleteAuthor(@RequestParam("id") long id, Model model) throws AuthorHasBooksException {
        if (libraryService.existsBookByAuthorId(id)) {
            throw new AuthorHasBooksException("У автора есть книги, удаление невозможно!");
        }
        Author author = libraryService.findAuthorById(id).orElseThrow(NotFoundException::new);
        libraryService.deleteAuthorById(id);
        return "redirect:/authors";
    }



}
