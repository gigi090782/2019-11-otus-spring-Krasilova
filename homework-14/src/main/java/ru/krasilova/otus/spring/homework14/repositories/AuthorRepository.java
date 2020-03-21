package ru.krasilova.otus.spring.homework14.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.krasilova.otus.spring.homework14.models.Author;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {

    List<Author> findByLastName(String lastName);

}


