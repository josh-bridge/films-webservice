package com.jbridgiee.films.server.data.sql;

/**
 *
 * @author josh.bridge
 */
public class SqlStatementBuilder {

    public static SqlStatementBuilder sqlStatement() {
        return new SqlStatementBuilder();
    }

    private final StringBuilder sql;

    private SqlStatementBuilder() {
        sql = new StringBuilder();
    }

    public SqlStatementBuilder select(String field) {
        sql.append("SELECT ").append(field).append(" ");
        return this;
    }

    public SqlStatementBuilder selectAll() {
        sql.append("SELECT * ");
        return this;
    }

    public SqlStatementBuilder from(String table) {
        sql.append("FROM ").append(table).append(" ");
        return this;
    }

    public SqlStatementBuilder where(String field) {
        sql.append("WHERE ").append(field).append(" ");
        return this;
    }

    public SqlStatementBuilder eq(Object value) {
        sql.append("= ").append(value.toString());
        return this;
    }

    public SqlStatementBuilder like(String value) {
        sql.append(" ").append("LIKE ").append(value);
        return this;
    }

    public SqlStatementBuilder limit(String value) {
        sql.append(" ").append("LIMIT ").append(value);
        return this;
    }

    public String build() {
        return sql.toString();
    }
}
