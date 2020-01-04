package ru.krasilova.otus.spring.homework5.repositories;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.krasilova.otus.spring.homework5.models.Author;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorRepositoryJdbc implements AuthorRepository, Serializable {
    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations jdbcNamed;

    public AuthorRepositoryJdbc(JdbcOperations jdbcOperations,  NamedParameterJdbcOperations jdbcNamed) {
        this.jdbc = jdbcOperations;
        this.jdbcNamed = jdbcNamed;
    }

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from authors", Integer.class);
    }

    @Override
    public Long insert(String firstName, String secondName, String lastName, Date birthdate) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", firstName);
        params.addValue("secondName", secondName);
        params.addValue("lastName", lastName);
        params.addValue("birthdate", birthdate);
        KeyHolder kh = new GeneratedKeyHolder();
        jdbcNamed.update("insert into authors (`firstName`,`secondName`,`lastName`,`birthdate`) values (:firstName, :secondName, :lastName, :birthdate)", params, kh);
        return kh.getKey().longValue();

    }


    @Override
    public Author getById(long id) {
         List<Author> listAuthor = jdbc.query("select * from authors where id = ?", new AuthorMapper(), id);
        if (listAuthor.isEmpty())
            return null;
        else
            return listAuthor.get(0);
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from authors", new AuthorMapper());
    }

    public static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String firstName = resultSet.getString("firstName");
            String secondName = resultSet.getString("secondName");
            String lastName = resultSet.getString("lastName");
            Date birthDate = resultSet.getDate("birthDate");
            return new Author(id, firstName, secondName, lastName, birthDate);
        }
    }
}
