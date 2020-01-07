package ru.krasilova.otus.spring.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.homework6.models.*;
import ru.krasilova.otus.spring.homework6.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework6.repositories.BookRepository;
import ru.krasilova.otus.spring.homework6.repositories.CommentRepository;
import ru.krasilova.otus.spring.homework6.repositories.GenreRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;
    private final IOService ioService;

    @Override
    public void addNewGenre(String genreName) {
        Genre genre = new Genre(0, genreName);
        genreRepository.save(genre);
    }

    @Override
    public void addNewAuthor(String firstName, String secondName, String lastName, Date birthdate) {
        Author author = new Author(0, firstName, secondName, lastName, birthdate);
        authorRepository.save(author);
    }

    @Override
    public boolean addNewBook(String bookName, Long authorId, Long genreId) {
        Author author = authorRepository.findById(authorId);
        if (author == null) {
            ioService.printString("Данный автор не заведен в библиотеке!");
            return false;
        }
        Genre genre = genreRepository.findById(genreId);

        if (genre == null) {
            ioService.printString("Данный жанр не заведен в библиотеке!");
            return false;
        }
        Book book = new Book(0, bookName, author, genre, null);
        bookRepository.save(book);
        return true;
    }

    @Override
    public boolean addNewComment(String text, Long bookId) {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            ioService.printString("Данная книга не найдена в библиотеке!");
            return false;
        }

        Comment comment = new Comment(0, text, bookId);
        commentRepository.save(comment);
        return true;
    }

    @Override
    public boolean deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId);
        if (comment == null) {
            ioService.printString("Данный комментарий не найден!");
            return false;
        }

        commentRepository.deleteById(commentId);
        return true;
    }

    @Override
    public void showAllGenres() {
        String showGenre;
        List<Genre> listGenres = genreRepository.getAll();
        for (Genre genre : listGenres) {
            showGenre = "Id: = " + genre.getId() + " Name: " + genre.getName();
            ioService.printString(showGenre);
        }
    }



    @Override
    public void showAllAuthors() {
        String showAuthor;
        List<Author> listAuthor = authorRepository.getAll();
        for (Author author : listAuthor) {
            showAuthor = "Id: = " + author.getId() + " FirstName: " + author.getFirstName() +
                    " SecondName: " + author.getSecondName() + " LastName: " + author.getLastName() +
                    " Birthdate: " + author.getBirthDate();
            ioService.printString(showAuthor);
        }
    }

    @Override
    public void showAllBooks() {
        List<Book> listBook = bookRepository.findAllWithAllInfo();
        showBook(listBook);
    }

    @Override
    public void showAllBooksByAuthorLastName(String lastName) {
        List<Book> listBook = bookRepository.findAllByAuthorLastName(lastName);
        showBook(listBook);

    }

    @Override
    public void showAllBooksByAuthorId(Long id) {
        List<Book> listBook = bookRepository.findAllByAuthorID(id);
        showBook(listBook);

    }

    @Override
    public void showAllBooksByGenreName(String genreName) {
        List<Book> listBook = bookRepository.findAllByGenreName(genreName);
        showBook(listBook);
    }

    @Override
    public void showAllComments() {
        List<Comment> listComment = commentRepository.getAll();
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
