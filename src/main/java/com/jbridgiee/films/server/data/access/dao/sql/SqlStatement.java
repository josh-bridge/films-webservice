package com.jbridgiee.films.server.data.access.dao.sql;

/**
 *
 * @author josh.bridge
 */
public class SqlStatement {

    public static SqlStatement sql() {
        return new SqlStatement();
    }

    private final StringBuilder sql;

    private SqlStatement() {
        sql = new StringBuilder();
    }

    public SqlStatement select(String field) {
        sql.append("SELECT ").append(field).append(" ");
        return this;
    }

    public SqlStatement selectAll() {
        sql.append("SELECT * ");
        return this;
    }

    public SqlStatement from(String table) {
        sql.append("FROM ").append(table).append(" ");
        return this;
    }

    public SqlStatement where(String field) {
        sql.append("WHERE ").append(field).append(" ");
        return this;
    }

    public SqlStatement eq(Object value) {
        sql.append("= ").append(value.toString());
        return this;
    }

    public SqlStatement like(String value) {
        sql.append(" ").append("LIKE ").append(value);
        return this;
    }

    public SqlStatement limit(String value) {
        sql.append(" ").append("LIMIT ").append(value);
        return this;
    }

    public SqlStatement limit(int value) {
        return limit(Integer.toString(value));
    }

    public String build() {
        return sql.toString();
    }
}
