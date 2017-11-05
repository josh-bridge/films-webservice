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

    public SqlStatement distinct(String field) {
        sql.append("(DISTINCT ").append(field).append(") ");
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

    public SqlStatement or(String field) {
        sql.append("OR ").append(field).append(" ");
        return this;
    }

    public SqlStatement eq(Object value) {
        sql.append("= ").append(value.toString()).append(" ");
        return this;
    }

    public SqlStatement like(String value) {
        sql.append("LIKE ").append(value).append(" ");
        return this;
    }

    public SqlStatement limit(String value) {
        sql.append("LIMIT ").append(value).append(" ");
        return this;
    }

    public SqlStatement groupBy(String field) {
        sql.append("GROUP BY ").append(field).append(" ");
        return this;
    }

    public SqlStatement limit(int value) {
        return limit(Integer.toString(value));
    }

    public String build() {
        return sql.toString().trim();
    }
}
