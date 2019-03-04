package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
    private Connection connection;
    
    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%d/%s";
    private static final String HOST = "localhost";
    private static final int PORT = 5432;
    private static final String DB_NAME = "chat_db";
    private static final String USER = "stepan";
    private static final String PASSWORD = "polkiujhy";
    private static final String URL;

    // Check database postgres driver init.
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(String.format("Failed to load jdbc driver (%s).", e.getMessage()));
            System.exit(-1);
        }
        
        URL = String.format(URL_TEMPLATE, HOST, PORT, DB_NAME);
        System.out.println("Success. Postgres driver init.");
    }
    
    public DBService() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            printConnectionInfo();
        }
        catch (SQLException e) {
            System.out.println(String.format("Failed to get connection (%s).", e.getMessage()));
        }
    }
    
    private void printConnectionInfo() throws SQLException {
        System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
        System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
        System.out.println("Driver: " + connection.getMetaData().getDriverName());
        System.out.println("Autocommit: " + connection.getAutoCommit());
    }
    
    public Connection getConnection() {
        return connection;
    }
}
