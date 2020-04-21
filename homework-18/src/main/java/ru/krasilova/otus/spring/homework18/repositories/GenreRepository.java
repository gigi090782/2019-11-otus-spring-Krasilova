package ru.krasilova.otus.spring.homework18.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.krasilova.otus.spring.homework18.models.Genre;

import java.util.List;
@RepositoryRestResource(path = "genre")
public interface GenreRepository extends PagingAndSortingRepository<Genre, Long>
{
     List<Genre> findAll();
    @RestResource(path = "genrenames", rel = "ganrenames")
    Genre findByName(String name);

}
