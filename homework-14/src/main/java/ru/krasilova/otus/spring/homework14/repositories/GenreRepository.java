package ru.krasilova.otus.spring.homework14.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krasilova.otus.spring.homework14.models.GenreForWrite;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreForWrite, String> {
    GenreForWrite findById(long id);
    List<GenreForWrite> findAll();
    GenreForWrite findByName(String name);
    void deleteAll();

}
