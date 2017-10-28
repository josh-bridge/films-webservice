package server.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import server.Film;

/**
 *
 * @author josh.bridge
 */
@Component
public class ConnectionPooler {

    private static final String DB_PROPERTIES = "db.properties";

    private static final String DB_USERNAME = "db.username";

    private static final String DB_PASSWORD = "db.password";

    private static final String DB_URL = "db.url";

    private final BasicDataSource connectionPool;

    public ConnectionPooler() throws IOException {
        this.connectionPool = new BasicDataSource();
        init();
    }

    public String executeQuery(String script) throws SQLException {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(connectionPool);
        final Film film = jdbcTemplate.queryForObject(
                "select * from films where id = ?",
                new Object[] {10811},
                (result, rowNum) -> new Film(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getInt("year"),
                        result.getString("director"),
                        Collections.singletonList(result.getString("stars")),
                        result.getString("review")));


        return film != null ? film.toString() : "error";
        // try (final Connection connection = connectionPool.getConnection();
        //      final PreparedStatement statement = connection.prepareStatement(script);
        //      final ResultSet resultSet = statement.executeQuery()) {
        //
        //     resultSet.next();
        //     return resultSet.getString(2);
        // }
    }

    private void init() throws IOException {
        final URL propertiesFile = getClass().getClassLoader().getResource(DB_PROPERTIES);

        if (propertiesFile == null) {
            throw new FileNotFoundException("'" + DB_PROPERTIES + "' file not found.");
        }

        final Properties properties = new Properties();
        try (final InputStream stream = propertiesFile.openStream()) {
            properties.load(stream);
        }

        connectionPool.setUsername(properties.getProperty(DB_USERNAME));
        connectionPool.setPassword(properties.getProperty(DB_PASSWORD));
        connectionPool.setUrl("jdbc:mysql://" + properties.getProperty(DB_URL));
        connectionPool.setDriverClassName("com.mysql.cj.jdbc.Driver");
        connectionPool.setInitialSize(1);


    }
}
