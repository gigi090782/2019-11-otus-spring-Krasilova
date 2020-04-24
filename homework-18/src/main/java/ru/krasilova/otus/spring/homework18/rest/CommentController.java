
package ru.krasilova.otus.spring.homework18.rest;
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
import ru.krasilova.otus.spring.homework18.services.LibraryService;


import java.text.ParseException;
import java.util.List;

@Controller
public class CommentController {

    private final LibraryService libraryService;

    @Autowired
    public CommentController(LibraryService libraryService) {
        this.libraryService = libraryService;

    }

    @GetMapping("/commentsbook")
    public String getListBook(@RequestParam("id") long id, Model model) {
        List<Comment> comments = libraryService.findCommentByBookId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("bookid", id);
        return "listComments";
    }

    @GetMapping("/editcomment")
    public String getEditComment(@RequestParam("id") long id, Model model) {
        Comment comment = libraryService.findCommentById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("comment", comment);
        return "editComment";
    }

    @PostMapping("/addcomment")
    public String getAddComment(@RequestParam("bookid") long bookId, Model model) throws ParseException {
        Book book = libraryService.findBookById(bookId).orElseThrow(NotFoundException::new);
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
    ) {
        Comment comment;
        if (id != 0) {
            comment = libraryService.findCommentById(id).orElseThrow(NotFoundException::new);
            comment.setText(text);

        } else {
            Book book = libraryService.findBookById(bookId).orElseThrow(NotFoundException::new);
            comment = new Comment(text, book);

        }

        libraryService.saveComment(comment);
        List<Comment> comments = libraryService.findCommentByBookId(bookId);
        model.addAttribute("comments", comments);
        model.addAttribute("bookid", comment.getBook().getId());
        return "redirect:/commentsbook/?id=" + String.valueOf(comment.getBook().getId());
    }

    @PostMapping("/deletecomment")
    public String postDeleteComment(@RequestParam("id") long id, Model model) throws AuthorHasBooksException {
        Comment comment = libraryService.findCommentById(id).orElseThrow(NotFoundException::new);
        long bookId = comment.getBook().getId();
        model.addAttribute("bookid", bookId);
        libraryService.deleteCommentById(id);
        List<Comment> comments = libraryService.findCommentByBookId(bookId);
        model.addAttribute("comments", comments);
        return "redirect:/commentsbook/?id=" + String.valueOf(bookId);
    }



}
