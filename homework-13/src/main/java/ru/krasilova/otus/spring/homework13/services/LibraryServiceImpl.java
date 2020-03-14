package ru.krasilova.otus.spring.homework13.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.homework13.models.Book;
import ru.krasilova.otus.spring.homework13.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework13.repositories.BookRepository;

import ru.krasilova.otus.spring.homework13.services.LibraryService;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;

    @Autowired
    public LibraryServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addNewBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }
}
