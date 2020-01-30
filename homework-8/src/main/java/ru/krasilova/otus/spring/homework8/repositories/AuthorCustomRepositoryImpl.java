package ru.krasilova.otus.spring.homework8.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.krasilova.otus.spring.homework8.exceptions.AuthorHasBooksException;
import ru.krasilova.otus.spring.homework8.models.Book;

import java.util.List;

@RequiredArgsConstructor
public class AuthorCustomRepositoryImpl implements AuthorCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public boolean canRemoveAuthor(String id) throws Exception {
        Query query = Query.query(Criteria.where("author_id").is(id));
        List<Book> listBook = mongoTemplate.find(query, Book.class);
        if (listBook.size() != 0) {
            return false;
        } else return true;
    }

}
