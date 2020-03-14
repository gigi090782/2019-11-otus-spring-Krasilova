package ru.krasilova.otus.spring.homework12.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krasilova.otus.spring.homework12.models.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long>
{
     List<Genre> findAll();
    Genre findByName(String name);

}
