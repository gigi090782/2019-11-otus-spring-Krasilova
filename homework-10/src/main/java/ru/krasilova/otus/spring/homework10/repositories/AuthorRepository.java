package ru.krasilova.otus.spring.homework10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krasilova.otus.spring.homework10.models.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByLastName(String lastName);

}


