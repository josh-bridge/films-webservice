package com.jbridgiee.films.server.data.access.dao.sql;

/**
 *
 * @author josh.bridge
 */
public class SqlStatement {

    private final String select;

    private final String fields;

    private final String from;

    private final String where;

    private final String like;

    public SqlStatement(String select, String fields, String from, String where, String like) {
        this.select = select;
        this.fields = fields;
        this.from = from;
        this.where = where;
        this.like = like;
    }

    public String build() {
        return select + fields + from + where + like;
    }

    public class StatementBuilder {

        public FromBuilder select(String fields) {
            return from -> where -> like -> new SqlStatement(
                    "SELECT ",
                    fields + " ",
                    from,
                    where,
                    like);
        }

        public FromBuilder selectAll() {
            return table -> where -> like -> new SqlStatement(
                    "SELECT ",
                    "* ",
                    "FROM " + table + " ",
                    "WHERE " + where + " ",
                    "LIKE " + like);
        }
    }

    public interface FromBuilder {
        WhereBuilder from(String table);
    }

    public interface WhereBuilder {
        LikeBuilder where(String where);
    }

    public interface LikeBuilder {
        SqlStatement like(String like);
    }
}
