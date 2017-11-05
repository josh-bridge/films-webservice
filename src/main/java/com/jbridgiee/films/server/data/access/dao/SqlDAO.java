package com.jbridgiee.films.server.data.access.dao;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.jbridgiee.films.server.data.access.dao.sql.SqlStatement;

public abstract class SqlDAO<T> implements DAO<T> {

    private final BasicDataSource connectionPool;

    SqlDAO(BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Nullable
    T getItem(SqlStatement sql, RowMapper<T> rowMapper, Object... args) {
        return getTemplate().queryForObject(sql.build(), args, rowMapper);
    }

    Stream<T> getItems(SqlStatement sql, RowMapper<T> rowMapper, Object... args) {
        return getTemplate().query(sql.build(), args, rowMapper).stream();
    }

    private JdbcTemplate getTemplate() {
        return new JdbcTemplate(connectionPool);
    }
}
