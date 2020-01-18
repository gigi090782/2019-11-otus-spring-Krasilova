package ru.krasilova.otus.spring.homework7.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.krasilova.otus.spring.homework7.models.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository <Author, Long>

{    @Query("select count(a) from Author a")
     long count();
     //Author save(Author author);
    //Optional<Author> findById(long id);
    Author findById(long id);
    List<Author> findByLastName(String lastName);
    List<Author> findAll();
    void deleteById(long id);
}


