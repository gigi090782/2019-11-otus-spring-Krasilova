package ru.krasilova.otus.spring.homework5.repositories;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.krasilova.otus.spring.homework5.models.Author;
import ru.krasilova.otus.spring.homework5.models.Genre;
import ru.krasilova.otus.spring.homework5.repositories.extractors.BookresultSetExtractor;
import ru.krasilova.otus.spring.homework5.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookReposiryJdbc implements BookReposiry {
    private final JdbcOperations jdbcOp;
    private final NamedParameterJdbcOperations jdbc;

    public BookReposiryJdbc(NamedParameterJdbcOperations jdbcOperations
                            ) {
        this.jdbc = jdbcOperations;
        this.jdbcOp = jdbcOperations.getJdbcOperations();
    }

    @Override
    public int count() {
        SqlParameterSource paramSource = new EmptySqlParameterSource();
        return jdbc.queryForObject("select count(*) from books", paramSource, Integer.class);
    }

    @Override
    public Long insert(String bookName, Long authorId, Long genreId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", bookName);
        params.addValue("genreid", authorId);
        params.addValue("authorid", genreId);
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into books (`name`,genreid,authorid) values (:name, :genreid, :authorid)", params, kh);
        return kh.getKey().longValue();

    }

    @Override
    public Book getById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbc.queryForObject(" select b.id, b.name, b.authorid, " +
                "a.firstname, a.secondname, a.lastname, a.birthdate, " +
                "b.genreid, j.name as genrename " +
                "from books b " +
                "inner join authors a on a.id = b.authorid " +
                "inner join genres j on j.id = b.genreid " +
                "where b.id = :id ", params, new BookMapper());

    }

    @Override
    public Book getByName(String name) {
        return null;
    }


    @Override
    public List<Book> findAllWithAllInfo() {
        SqlParameterSource params = new EmptySqlParameterSource();
        List<Book> listBook = jdbc.query(" select b.id, b.name, b.authorid, " +
                "a.firstname, a.secondname, a.lastname, a.birthdate, " +
                "b.genreid, j.name as genrename " +
                "from books b " +
                "inner join authors a on a.id = b.authorid " +
                "inner join genres j on j.id = b.genreid", params, new BookMapper());

        return listBook;

    }

    @Override
    public List<Book> findAllByAuthorID(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        Map<Long, Book> books = jdbc.query(" select b.id, b.name, b.authorid, " +
                "a.firstname, a.secondname, a.lastname, a.birthdate, " +
                "b.genreid, j.name as genrename " +
                "from authors a " +
                "inner join books b on  b.authorid = a.id " +
                "inner join genres j on j.id = b.genreid " +
                "where a.id = :id ", params,new BookresultSetExtractor());

        return new ArrayList<>(Objects.requireNonNull(books).values());
    }

    @Override
    public List<Book> findAllByAuthorLastName(String lastName) {
        Map<Long, Book> books = jdbcOp.query(" select b.id, b.name, b.authorid, " +
                "a.firstname, a.secondname, a.lastname, a.birthdate, " +
                "b.genreid, j.name as genrename " +
                "from authors a " +
                "inner join books b on  b.authorid = a.id " +
                "inner join genres j on j.id = b.genreid " +
                "where a.lastname = ? ", new BookresultSetExtractor(), lastName);

        return new ArrayList<>(Objects.requireNonNull(books).values());
    }

    @Override
    public List<Book> findAllByGenreName(String jereName) {
        Map<Long, Book> books = jdbcOp.query(" select b.id, b.name, b.authorid, " +
                "a.firstname, a.secondname, a.lastname, a.birthdate, " +
                "b.genreid, j.name as genrename " +
                "from genres j  " +
                "inner join books b on  b.genreid  = j.id " +
                "inner join authors a on a.id = b.authorid  " +
                "where j.name = ? ", new BookresultSetExtractor(), jereName);

        return new ArrayList<>(Objects.requireNonNull(books).values());
    }

    public static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String bookName = resultSet.getString("name");
            Long genreId = resultSet.getLong("genreId");
            String genreName = resultSet.getString("genrename");
            Long authorId = resultSet.getLong("authorId");
            String firstname = resultSet.getString("firstname");
            String secondname = resultSet.getString("secondname");
            String lastname = resultSet.getString("lastname");
            Date birthdate = resultSet.getDate("birthdate");
            Author author = new Author(authorId, firstname, secondname, lastname, birthdate);
            Genre genre = new Genre(genreId, genreName);
            return new Book(id, bookName, author, genre);
        }
    }
}
