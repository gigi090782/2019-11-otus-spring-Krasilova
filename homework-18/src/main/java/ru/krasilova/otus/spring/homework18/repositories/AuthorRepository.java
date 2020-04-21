package ru.krasilova.otus.spring.homework18.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.krasilova.otus.spring.homework18.models.Author;

import java.util.List;

@RepositoryRestResource(path = "author")
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {
    List<Author> findAll();

    @RestResource(path = "lastnames", rel = "lastnames")
    List<Author> findByLastName(String lastName);

}
