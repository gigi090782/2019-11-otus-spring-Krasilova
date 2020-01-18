package ru.krasilova.otus.spring.homework7.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.krasilova.otus.spring.homework7.models.Book;
import ru.krasilova.otus.spring.homework7.models.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long>
{
    @Query("select count(g) from Genre g")
    long count();
    Genre findById(long id);
    List<Genre> findAll();
    Genre findByName(String name);
    void deleteById(long id);
}
