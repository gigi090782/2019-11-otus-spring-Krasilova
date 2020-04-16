
package ru.krasilova.otus.spring.homework9.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.homework9.exceptions.AuthorHasBooksException;
import ru.krasilova.otus.spring.homework9.exceptions.NotFoundException;
import ru.krasilova.otus.spring.homework9.models.Book;
import ru.krasilova.otus.spring.homework9.models.Comment;
import ru.krasilova.otus.spring.homework9.repositories.BookRepository;
import ru.krasilova.otus.spring.homework9.repositories.CommentRepository;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.List;

@Controller
public class CommentController {

    private final CommentRepository repository;
    private final BookRepository bookRepository;

    @Autowired
    public CommentController(CommentRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/commentsbook")
    public String getListBook(@RequestParam("id") long id,Model model) {
        List<Comment> comments = repository.findByBookId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("bookid", id);
        return "listComments";
    }

    @GetMapping("/editcomment")
    public String getEditComment(@RequestParam("id") long id, Model model) {
        Comment comment = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("comment", comment);
        return "editComment";
    }


    @GetMapping("/addcomment")
    public String getAddComment(@RequestParam("bookid") long bookId, Model model) throws ParseException {
        Book book = bookRepository.findById(bookId).orElseThrow(NotFoundException::new);
        Comment comment = new Comment("", book);
        model.addAttribute("comment", comment);
        return "editComment";
    }

    @PostMapping("/savecomment")
    public String postSaveComment(
            @RequestParam("id") long id,
            @RequestParam("text") String text,
            @RequestParam("bookid") long bookId,
            Model model
    ) { Comment comment;

        if (id!=0) {
            comment = repository.findById(id).orElseThrow(NotFoundException::new);
            comment.setText(text);

        } else
        {   Book book = bookRepository.findById(bookId).orElseThrow(NotFoundException::new);
            comment = new Comment(text, book);

        }

        repository.save(comment);
        List<Comment> comments = repository.findByBookId(bookId);
        model.addAttribute("comments", comments);
        model.addAttribute("bookid", comment.getBook().getId() );
        return "redirect:/commentsbook";
    }


    @PostMapping("/deletecomment")
    public String postDeleteComment(@RequestParam("id") long id, Model model) throws AuthorHasBooksException {
        Comment comment = repository.findById(id).orElseThrow(NotFoundException::new);
        long bookId =comment.getBook().getId();
        model.addAttribute("bookid", bookId );
        repository.deleteById(id);
        List<Comment> comments = repository.findByBookId(bookId);
        model.addAttribute("comments", comments);
        return "redirect:/commentsbook";
    }

}
