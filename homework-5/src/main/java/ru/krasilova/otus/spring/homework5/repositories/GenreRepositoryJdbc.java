package ru.krasilova.otus.spring.homework5.repositories;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.krasilova.otus.spring.homework5.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreRepositoryJdbc implements GenreRepository {

    private final NamedParameterJdbcOperations jdbc;

    public GenreRepositoryJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }


    @Override
    public int count() {
        SqlParameterSource paramSource = new EmptySqlParameterSource();
        return jdbc.queryForObject("select count(*) from genres", paramSource, Integer.class);
    }

    @Override
    public Long insert(String genreName) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genreName);
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update("insert into genres (`name`) values (:name)", params, kh);
        return kh.getKey().longValue();

    }

    @Override
    public Genre getById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<Genre> listGenre = jdbc.query("select * from genres where id = :id", params ,new GenreMapper());
        if (listGenre.isEmpty())
            return null;
        else
            return listGenre.get(0);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genres", new GenreMapper());
    }


    public static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);


        }
    }
}


