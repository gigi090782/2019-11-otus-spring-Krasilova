package ru.krasilova.otus.spring.homework18.services;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.krasilova.otus.spring.homework18.models.Author;
import ru.krasilova.otus.spring.homework18.models.Book;
import ru.krasilova.otus.spring.homework18.models.Comment;
import ru.krasilova.otus.spring.homework18.models.Genre;
import ru.krasilova.otus.spring.homework18.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework18.repositories.BookRepository;
import ru.krasilova.otus.spring.homework18.repositories.CommentRepository;
import ru.krasilova.otus.spring.homework18.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ru.krasilova.otus.spring.homework18.utils.Util.getRandomSleep;

@Service
@Transactional
@DefaultProperties(groupKey = "LibraryGroupKey", defaultFallback = "getWaitResponse")
public class LibraryServiceImpl  implements LibraryService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public LibraryServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "AuthorGroup", commandKey = "getAllAuthorsCommand",
                    fallbackMethod = "getReserveListAuthors")
    @Override
    public List<Author> getAllAuthors() {
        getRandomSleep();
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        return authorRepository.findById(id);
    }


    public List<Author> getReserveListAuthors( ) {
        return Collections.emptyList();
    }


    @Override
    @HystrixCommand
    public List<Author> findAuthorByLastName(String lastName) {
        return authorRepository.findByLastName(lastName);
    }

    @Override
    @HystrixCommand(groupKey = "BookGroup", commandKey = "getAllBooksCommand",
            fallbackMethod = "getReserveListBooks")
    public List<Book> getAllBooks() {
        getRandomSleep();
        return bookRepository.findAll();
    }

    public List<Book>  getReserveListBooks() {
        return Collections.emptyList();
    }

    @Override
    public List<Book> findAllBooksByAuthorId(Long id) {
        return bookRepository.findAllByAuthorId(id);
    }

    @Override
    public List<Book> findAllBooksByAuthorLastName(String lastName) {
        return bookRepository.findAllByAuthorLastName(lastName);
    }

    @Override
    public List<Book> findAllBooksByGenreName(String genreName) {
        return bookRepository.findAllByGenreName(genreName);
    }

    @Override
    public boolean existsBookByAuthorId(long authorId) {
        return bookRepository.existsByAuthorId(authorId);
    }

    @Override
    public boolean existsBookByGenreId(long genreId) {
        return bookRepository.existsByGenreId(genreId);
    }

    @Override
    @HystrixCommand(groupKey = "CommentGroup", commandKey = "getAllCommentsCommand",
            fallbackMethod = "getReserveListComments")
    public List<Comment> findCommentByBookId(Long id) {
        getRandomSleep();
        return commentRepository.findByBookId(id);
    }

    public List<Comment> getReserveListComments(Long id) {
        return Collections.emptyList();
    }


    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void deleteAllCommentsByBookId(long bookId) {
         commentRepository.deleteAllByBookId(bookId);
    }

    @Override
    @HystrixCommand(groupKey = "GenreGroup", commandKey = "getAllGenresCommand",
            fallbackMethod = "getReserveListGenres")
    public List<Genre> getAllGenres() {
        getRandomSleep();
        return genreRepository.findAll();
    }


    public List<Genre> getReserveListGenres( ) {
        return Collections.emptyList();
    }

    @Override
    public Genre findGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<Comment> findCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Optional<Genre> findGenreById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public void deleteGenreById(Long id) {
        genreRepository.deleteById(id);
    }


    public String getWaitResponse()
    {

        return "Сервер не отвечает! Попробуйте еще раз попозже!";
    }


}
