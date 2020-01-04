package ru.krasilova.otus.spring.homework5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.homework5.models.Author;
import ru.krasilova.otus.spring.homework5.models.Book;
import ru.krasilova.otus.spring.homework5.models.Genre;
import ru.krasilova.otus.spring.homework5.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework5.repositories.BookReposiry;
import ru.krasilova.otus.spring.homework5.repositories.GenreRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final AuthorRepository authorRepository;
    private final BookReposiry bookReposiry;
    private final GenreRepository genreRepository;
    private final IOService ioService;

    @Override
    public void addNewGenre(String genreName) {
        genreRepository.insert(genreName);
    }

    @Override
    public void addNewAuthor(String firstName, String secondName, String lastName, Date birthdate) {
        authorRepository.insert(firstName, secondName, lastName,birthdate);
    }

    @Override
    public boolean addNewBook(String bookName, Long authorId, Long genreId) {
        Author author = authorRepository.getById(authorId);
        if (author == null)
        {
            ioService.printString("Данный автор не заведен в библиотеке!");
            return false;
        }
        Genre genre = genreRepository.getById(genreId);

        if (genre == null)
        {
            ioService.printString("Данный жанр не заведен в библиотеке!");
            return false;
        }
        bookReposiry.insert(bookName, authorId, genreId);
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
        List<Book> listBook = bookReposiry.findAllWithAllInfo();
        showBook(listBook);
    }

    @Override
    public void showAllBooksByAuthorLastName(String lastName) {
        List<Book> listBook = bookReposiry.findAllByAuthorLastName(lastName);
        showBook(listBook);

    }

    @Override
    public void showAllBooksByAuthorId(Long id) {
        List<Book> listBook = bookReposiry.findAllByAuthorID(id);
        showBook(listBook);

    }

    @Override
    public void showAllBooksByGenreName(String genreName) {
        List<Book> listBook = bookReposiry.findAllByGenreName(genreName);
        showBook(listBook);
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
}
