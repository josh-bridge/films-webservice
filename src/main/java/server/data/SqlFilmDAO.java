package server.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 *
 * @author josh.bridge
 */
@Component
public class SqlFilmDAO {

    private static final String CONN_URL = "mudfoot.doc.stu.mmu.ac.uk";

    private static final String USERNAME = "bridgej";

    private static final String PASSWORD = "gonsplIt2";

    private static final String DB_NAME = "bridgej";

    private static final int DB_PORT = 3306;

    private static final String MYSQL_CONN_URL = "jdbc:mysql://" + CONN_URL + ":" + DB_PORT + "/" + DB_NAME;

    private Connection conn = null;

    private Statement stmt = null;

    public String openConnection() {
        final MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(CONN_URL);
        dataSource.setUrl(MYSQL_CONN_URL);
        dataSource.setUser(USERNAME);
        dataSource.setPassword(PASSWORD);

        try {
            conn = dataSource.getConnection();

            stmt = conn.createStatement();

            stmt.executeQuery("select * from `films`");
            try (final ResultSet result = stmt.getResultSet()) {
                result.next();
                return result.getString(2);
            }

        } catch (final SQLException se) {
            se.printStackTrace();
        }

        return "fail";
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
