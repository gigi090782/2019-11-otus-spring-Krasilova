package ru.krasilova.otus.spring.homework14.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krasilova.otus.spring.homework14.models.AuthorForWrite;
import java.util.List;


public interface AuthorRepository extends JpaRepository<AuthorForWrite, Long> {

    List<AuthorForWrite> findByLastName(String lastName);
    void deleteAll();

}


