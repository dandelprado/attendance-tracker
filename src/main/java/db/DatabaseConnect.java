package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConnect {
    private static String url;
    private static String username;
    private static String password;

    static {
        try (InputStream input = DatabaseConnect.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties properties = new Properties();
            if (input == null) {
                throw new IllegalStateException("Unable to find database.properties");
            }
            properties.load(input);

            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load database properties");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}