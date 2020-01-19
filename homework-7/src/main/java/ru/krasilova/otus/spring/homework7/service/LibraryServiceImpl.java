package ru.krasilova.otus.spring.homework7.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.homework7.exceptions.AuthorNotFoundException;
import ru.krasilova.otus.spring.homework7.exceptions.GenreNotFoundException;
import ru.krasilova.otus.spring.homework7.models.*;
import ru.krasilova.otus.spring.homework7.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework7.repositories.BookRepository;
import ru.krasilova.otus.spring.homework7.repositories.CommentRepository;
import ru.krasilova.otus.spring.homework7.repositories.GenreRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LibraryServiceImpl implements LibraryService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;
    private final IOService ioService;

    @Override
    public void addNewGenre(String genreName) {
        Genre genre = new Genre(0, genreName,null);
        genreRepository.save(genre);
    }

    @Override
    public void addNewAuthor(String firstName, String secondName, String lastName, Date birthdate) {
        Author author = new Author(0, firstName, secondName, lastName, birthdate,null);
        authorRepository.save(author);
    }
    @Override

    public boolean addNewBook(String bookName, Long authorId, Long genreId)  throws Exception {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        Author author = optionalAuthor.orElseThrow(() -> new AuthorNotFoundException("Данный автор не заведен в библиотеке!"));
        Optional<Genre> optionalGenre = genreRepository.findById(genreId);
        Genre genre = optionalGenre.orElseThrow(() -> new GenreNotFoundException("Данный жанр не заведен в библиотеке!"));
        Book book = new Book (0,bookName, author, genre );
        bookRepository.save(book);
        return true;
    }

    @Override
    public boolean addNewComment(String text, Long bookId) {
        Optional <Book> optionalBook = bookRepository.findById(bookId);
        Book book;
        if (optionalBook.isPresent() == true)
        {
            book = optionalBook.get();
        }
        else
        {
            ioService.printString("Данная книга не найдена в библиотеке!");
            return false;
        }

        Comment comment = new Comment(0, text, book);
        commentRepository.save(comment);
        return true;
    }

    @Override
    public boolean deleteComment(Long commentId) {
        Optional<Comment>  optionalComment =  commentRepository.findById(commentId);
        Comment comment;
        if (optionalComment.isEmpty())
        {
            ioService.printString("Данный комментарий не найден!");
            return false;
        }
        else
        {
            comment = optionalComment.get();
        }

        commentRepository.deleteById(commentId);
        return true;
    }

    @Override
    public void showAllGenres() {
        String showGenre;
        List<Genre> listGenres = genreRepository.findAll();
        for (Genre genre : listGenres) {
            showGenre = "Id: = " + genre.getId() + " Name: " + genre.getName();
            ioService.printString(showGenre);
        }
    }



    @Override
    public void showAllAuthors() {
        String showAuthor;
        List<Author> listAuthor = authorRepository.findAll();
        for (Author author : listAuthor) {
            showAuthor = "Id: = " + author.getId() + " FirstName: " + author.getFirstName() +
                    " SecondName: " + author.getSecondName() + " LastName: " + author.getLastName() +
                    " Birthdate: " + author.getBirthDate();
            ioService.printString(showAuthor);
        }
    }

    @Override
    public void showAllBooks() {
        List<Book> listBook = bookRepository.findAll();
        showBook(listBook);
    }

    @Override
    public void showAllBooksByAuthorLastName(String lastName) {
        List<Book> listBook = bookRepository.findAllByAuthorLastName(lastName);
        showBook(listBook);

    }

    @Override
    public void showAllBooksByAuthorId(Long id) {
        List<Book> listBook = bookRepository.findAllByAuthorId(id);
        showBook(listBook);

    }

    @Override
    public void showAllBooksByGenreName(String genreName) {
        List<Book> listBook = bookRepository.findAllByGenreName(genreName);
        showBook(listBook);
    }

    @Override
    public void showAllComments() {
        List<Comment> listComment = commentRepository.findAll();
        showComments(listComment);
    }

    @Override
    public void showAllCommentsByBookId(Long id) {
        List<Comment> listComment = commentRepository.findByBookId(id);
        showComments(listComment);
    }

    public void showBook(List<Book> listBook) {
        String showBook;
        for (Book book : listBook) {
            showBook = "Id: = " + book.getId() + " BookName: " + book.getName() +
                    " Author: " + book.getAuthor().getFirstName() + " " +
                    book.getAuthor().getSecondName() + " " +
                    book.getAuthor().getLastName() + " " +
                    book.getAuthor().getBirthDate() +
                    " Genre: " + book.getGenre().getName();
            ioService.printString(showBook);
        }
    }

    public void showComments(List<Comment> listComment) {
        String showComment;
        for (Comment comment : listComment) {
            showComment = " Comment: Id = " + comment.getId()+ " Comment = " + comment.getText();
            ioService.printString(showComment);


        }
    }
}
