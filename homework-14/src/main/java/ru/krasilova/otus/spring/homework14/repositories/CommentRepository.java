package ru.krasilova.otus.spring.homework14.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.krasilova.otus.spring.homework14.models.CommentForWrite;

import java.util.List;

public interface CommentRepository  extends JpaRepository<CommentForWrite, String>
{

    List<CommentForWrite> findAll();
    void deleteAll();
}
