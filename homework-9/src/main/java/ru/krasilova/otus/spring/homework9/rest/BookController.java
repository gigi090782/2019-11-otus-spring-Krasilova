package ru.krasilova.otus.spring.homework9.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework9.models.Book;
import ru.krasilova.otus.spring.homework9.repositories.BookRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookRepository repository;

    @Autowired
    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String listBook(Model model) {
        List<Book> books = repository.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") long id, Model model) {
       Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        return "edit";
    }

    @GetMapping("/save")
    public String changeName(
            @RequestParam("id") long id,
            @RequestParam("name") String name,
            Model model
    ) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        book.setName(name);
        repository.save(book);
        List<Book> books = repository.findAll();
        model.addAttribute("books", books);
        return "list";
    }
}
