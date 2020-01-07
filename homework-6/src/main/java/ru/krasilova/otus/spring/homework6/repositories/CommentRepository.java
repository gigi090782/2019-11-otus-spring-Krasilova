package ru.krasilova.otus.spring.homework6.repositories;

import ru.krasilova.otus.spring.homework6.models.Comment;

import java.util.List;

public interface CommentRepository {
    int count();
    Comment save(Comment author);
    Comment findById(long id);
    List<Comment> findByBookId(Long id);
    List<Comment> findByBookName(String name);
    List<Comment> getAll();
    void deleteById(long id);
}
