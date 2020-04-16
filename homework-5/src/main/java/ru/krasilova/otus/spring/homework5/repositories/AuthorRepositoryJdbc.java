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

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorRepositoryJdbc implements AuthorRepository, Serializable {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorRepositoryJdbc(JdbcOperations jdbcOperations, NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {


        SqlParameterSource paramSource = new EmptySqlParameterSource();
        return jdbc.queryForObject("select count(*) from authors", paramSource, Integer.class);
    }

    @Override
    public Long insert(String firstName, String secondName, String lastName, Date birthdate) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", firstName);
        params.addValue("secondName", secondName);
        params.addValue("lastName", lastName);
        params.addValue("birthdate", birthdate);
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into authors (`firstName`,`secondName`,`lastName`,`birthdate`) values (:firstName, :secondName, :lastName, :birthdate)", params, kh);
        return kh.getKey().longValue();

    }


    @Override
    public Author getById(long id) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbc.queryForObject(" select * from authors where id = :id ", params, new AuthorMapper());
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
