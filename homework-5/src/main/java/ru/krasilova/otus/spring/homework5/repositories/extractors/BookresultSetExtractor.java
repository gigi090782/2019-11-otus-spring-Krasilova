package ru.krasilova.otus.spring.homework5.repositories.extractors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.krasilova.otus.spring.homework5.models.Author;
import ru.krasilova.otus.spring.homework5.models.Book;
import ru.krasilova.otus.spring.homework5.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BookresultSetExtractor implements
        ResultSetExtractor<Map<Long, Book>> {

        @Override
        public Map<Long, Book> extractData(ResultSet rs) throws SQLException,
                DataAccessException {

            Map<Long, Book> books = new HashMap<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                Book book = books.get(id);
                if (book == null) {
                    book = new Book(id, rs.getString("name"),
                            new Author(rs.getLong("authorid"), rs.getString("firstname"), rs.getString("secondname"),
                                       rs.getString("lastname"), rs.getDate("birthDate")),
                            new Genre(rs.getLong("genreid"), rs.getString("genrename")));
                    books.put(book.getId(), book);
                }


            }
            return books;
        }
}


