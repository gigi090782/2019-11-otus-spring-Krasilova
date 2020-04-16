package ru.krasilova.otus.spring.homework8.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.krasilova.otus.spring.homework8.models.Comment;

@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements  CommentCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeCommentsByBookId(String id) {
        Query  query = Query.query(Criteria.where("book_id").is(id));
        mongoTemplate.remove(query, Comment.class, "comments");
    }
}
