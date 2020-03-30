package ru.krasilova.otus.spring.homework14.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krasilova.otus.spring.homework14.models.Book;
import ru.krasilova.otus.spring.homework14.models.BookForWrite;

import java.util.List;

public interface BookRepository extends JpaRepository<BookForWrite, Long> {
    BookForWrite findById(long id);
    BookForWrite findByName(String name);
    List<BookForWrite> findAll();
    void deleteAll();


}
