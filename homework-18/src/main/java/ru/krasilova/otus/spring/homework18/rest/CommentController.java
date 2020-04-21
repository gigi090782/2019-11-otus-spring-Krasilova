
package ru.krasilova.otus.spring.homework18.rest;


import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework18.exceptions.AuthorHasBooksException;
import ru.krasilova.otus.spring.homework18.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework18.models.Book;
import ru.krasilova.otus.spring.homework18.models.Comment;
import ru.krasilova.otus.spring.homework18.repositories.BookRepository;
import ru.krasilova.otus.spring.homework18.repositories.CommentRepository;

import static ru.krasilova.otus.spring.homework18.utils.Util.GetRandomSleep;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@DefaultProperties(defaultFallback = "getCommentWaitResponse")
public class CommentController {

    private final CommentRepository repository;
    private final BookRepository bookRepository;

    @Autowired
    public CommentController(CommentRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @HystrixCommand
    @GetMapping("/commentsbook")
    public String getListBook(@RequestParam("id") long id, Model model) {
        GetRandomSleep();
        List<Comment> comments = repository.findByBookId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("bookid", id);
        return "listComments";
    }

    @HystrixCommand
    @GetMapping("/editcomment")
    public String getEditComment(@RequestParam("id") long id, Model model) {
        GetRandomSleep();
        Comment comment = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("comment", comment);
        return "editComment";
    }

    @HystrixCommand
    @PostMapping("/addcomment")
    public String getAddComment(@RequestParam("bookid") long bookId, Model model) throws ParseException {
        GetRandomSleep();
        Book book = bookRepository.findById(bookId).orElseThrow(NotFoundException::new);
        Comment comment = new Comment("", book);
        model.addAttribute("comment", comment);
        return "editComment";
    }

    @HystrixCommand
    @PostMapping("/savecomment")
    public String postSaveComment(
            @RequestParam("id") long id,
            @RequestParam("text") String text,
            @RequestParam("bookid") long bookId,
            Model model
    ) {
        GetRandomSleep();
        Comment comment;
        if (id != 0) {
            comment = repository.findById(id).orElseThrow(NotFoundException::new);
            comment.setText(text);

        } else {
            Book book = bookRepository.findById(bookId).orElseThrow(NotFoundException::new);
            comment = new Comment(text, book);

        }

        repository.save(comment);
        List<Comment> comments = repository.findByBookId(bookId);
        model.addAttribute("comments", comments);
        model.addAttribute("bookid", comment.getBook().getId());
        return "redirect:/commentsbook/?id=" + String.valueOf(comment.getBook().getId());
    }

    @HystrixCommand
    @PostMapping("/deletecomment")
    public String postDeleteComment(@RequestParam("id") long id, Model model) throws AuthorHasBooksException {
        GetRandomSleep();
        Comment comment = repository.findById(id).orElseThrow(NotFoundException::new);
        long bookId = comment.getBook().getId();
        model.addAttribute("bookid", bookId);
        repository.deleteById(id);
        List<Comment> comments = repository.findByBookId(bookId);
        model.addAttribute("comments", comments);
        return "redirect:/commentsbook/?id=" + String.valueOf(bookId);
    }

    public String getCommentWaitResponse() {
        return "errorWait";
    }


}
