package ru.krasilova.otus.spring.homework8.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.krasilova.otus.spring.homework8.models.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String>, GenreCustomRepository
{
    Genre findById(long id);
    List<Genre> findAll();
    Genre findByName(String name);

}
