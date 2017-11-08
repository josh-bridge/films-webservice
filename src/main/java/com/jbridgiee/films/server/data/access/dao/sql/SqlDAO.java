package com.jbridgiee.films.server.data.access.dao.sql;

import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.jbridgiee.films.server.data.access.dao.DAO;

public abstract class SqlDAO<T> implements DAO<T> {

    private final BasicDataSource connectionPool;

    SqlDAO(BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    int create(SimpleJdbcInsert insert, Map<String, String> values) {
        return insert.executeAndReturnKey(values).intValue();
    }

    @Nullable
    T getItem(SqlStatement sql, RowMapper<T> rowMapper, Object... args) {
        try {
            return getTemplate().queryForObject(sql.build(), args, rowMapper);
        } catch (final EmptyResultDataAccessException ignored) {}

        return null;
    }

    Stream<T> getItems(SqlStatement sql, RowMapper<T> rowMapper, Object... args) {
        return getTemplate().query(sql.build(), args, rowMapper).stream();
    }

    JdbcTemplate getTemplate() {
        return new JdbcTemplate(connectionPool);
    }

}
