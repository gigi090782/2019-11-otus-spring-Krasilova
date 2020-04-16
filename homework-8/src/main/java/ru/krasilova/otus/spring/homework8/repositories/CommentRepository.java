package ru.krasilova.otus.spring.homework8.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.krasilova.otus.spring.homework8.models.Comment;

import java.util.List;

public interface CommentRepository  extends MongoRepository<Comment, String>,CommentCustomRepository
{
    List<Comment> findByBookId(String id);
    List<Comment> findAll();
}
