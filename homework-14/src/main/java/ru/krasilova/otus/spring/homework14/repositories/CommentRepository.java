package ru.krasilova.otus.spring.homework14.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.krasilova.otus.spring.homework14.models.Comment;

import java.util.List;

public interface CommentRepository  extends MongoRepository<Comment, String>
{
    List<Comment> findByBookId(String id);
    List<Comment> findAll();
}
