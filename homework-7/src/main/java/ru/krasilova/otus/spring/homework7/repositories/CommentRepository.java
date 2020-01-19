package ru.krasilova.otus.spring.homework7.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.krasilova.otus.spring.homework7.models.Book;
import ru.krasilova.otus.spring.homework7.models.Comment;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment, Long>
{
    List<Comment> findByBookId(Long id);
    List<Comment> findAll();
}
