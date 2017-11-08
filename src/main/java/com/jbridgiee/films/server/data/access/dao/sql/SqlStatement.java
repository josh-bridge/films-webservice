package com.jbridgiee.films.server.data.access.dao.sql;

/**
 *
 * @author josh.bridge
 */
public class SqlStatement {

    static SqlStatement sql() {
        return new SqlStatement();
    }

    private final StringBuilder sql;

    private SqlStatement() {
        sql = new StringBuilder();
    }

    SqlStatement select(String field) {
        sql.append("SELECT ").append(field).append(" ");
        return this;
    }

    SqlStatement selectAll() {
        sql.append("SELECT * ");
        return this;
    }

    SqlStatement insertInto(String table) {
        sql.append("INSERT INTO ").append(table).append(" ");
        return this;
    }

    SqlStatement columns(String... columns) {
        commaSeparated(columns);
        return this;
    }

    SqlStatement distinct(String field) {
        sql.append("(DISTINCT ").append(field).append(") ");
        return this;
    }

    SqlStatement from(String table) {
        sql.append("FROM ").append(table).append(" ");
        return this;
    }

    SqlStatement values(String... values) {
        sql.append("VALUES ");
        commaSeparated(values);

        return this;
    }

    SqlStatement where(String field) {
        sql.append("WHERE ").append(field).append(" ");
        return this;
    }

    SqlStatement or(String field) {
        sql.append("OR ").append(field).append(" ");
        return this;
    }

    SqlStatement eq(Object value) {
        sql.append("= ").append(value.toString()).append(" ");
        return this;
    }

    SqlStatement like(String value) {
        sql.append("LIKE ").append(value).append(" ");
        return this;
    }

    SqlStatement limit(String value) {
        sql.append("LIMIT ").append(value).append(" ");
        return this;
    }

    SqlStatement limit(int value) {
        return limit(Integer.toString(value));
    }

    SqlStatement groupBy(String field) {
        sql.append("GROUP BY ").append(field).append(" ");
        return this;
    }

    String build() {
        return sql.toString().trim();
    }

    private void commaSeparated(String[] strings) {
        sql.append("(")
                .append(String.join(", ", strings))
                .append(") ");
    }
}
