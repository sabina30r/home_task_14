package nix.edu.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionUtil {
    private static Connection connection;

    public static Connection getConnection() {
        Properties props = loadProperties();
        String url = props.getProperty("url");
        try {
            connection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = DbConnectionUtil.class.getResourceAsStream("/Jdbc.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return props;
    }
}
