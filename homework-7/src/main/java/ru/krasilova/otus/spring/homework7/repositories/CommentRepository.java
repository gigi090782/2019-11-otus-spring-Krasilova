package ru.krasilova.otus.spring.homework7.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.krasilova.otus.spring.homework7.models.Book;
import ru.krasilova.otus.spring.homework7.models.Comment;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment, Long>
{
    @Query("select count(c) from Comment c")
    long count();

    Comment findById(long id);
    List<Comment> findByBookId(Long id);
    List<Comment> findByBookName(String name);
    List<Comment> findAll();
    void deleteById(long id);
}
