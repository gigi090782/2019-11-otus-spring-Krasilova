package ru.krasilova.otus.spring.homework8.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.krasilova.otus.spring.homework8.models.Book;
import ru.krasilova.otus.spring.homework8.models.Comment;

import java.util.List;


@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final MongoTemplate mongoTemplate;


    @Override
    public void removeBooksByAuthorId(String id) {
        //сначала удалим комментарии к книге
        Query query = Query.query(Criteria.where("author_id").is(id));
        List<Book> listBook = mongoTemplate.find(query, Book.class);
        listBook.forEach(s ->removeCommentsByBooksId(s.getId()));
        //удалим книги
        mongoTemplate.remove(query, Book.class, "books");

    }

    @Override
    public void removeBooksByGenreId(String id) {
        //сначала удалим комментарии к книге
        Query query = Query.query(Criteria.where("genre_id").is(id));
        List<Book> listBook = mongoTemplate.find(query, Book.class);
        //удалим книги
        listBook.forEach(s ->removeCommentsByBooksId(s.getId()));
        mongoTemplate.remove(query, Book.class, "books");
    }




    @Override
    public void removeCommentsByBooksId(String id) {
        Query queryComment = Query.query(Criteria.where("book_id").is(id));
        mongoTemplate.remove(queryComment, Comment.class, "comments");
    }


}
