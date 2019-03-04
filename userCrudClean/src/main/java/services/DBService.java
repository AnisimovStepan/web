package services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBService {
    private static final Logger log = LogManager.getLogger(DBService.class.getName());
    
    private static Connection connection;
    
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
            log.info("Failed to load jdbc driver ({}).", e.getMessage());
            System.exit(-1);
        }
        
        URL = String.format(URL_TEMPLATE, HOST, PORT, DB_NAME);
        log.info("Success. Postgres driver init.");
        
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
    
            log.info("DB name: " + connection.getMetaData().getDatabaseProductName());
            log.info("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            log.info("Driver: " + connection.getMetaData().getDriverName());
            log.info("Autocommit: " + connection.getAutoCommit());
        }
        catch (SQLException e) {
            log.info("Failed to get connection ({}).", e.getMessage());
        }
    }
    
    public static Connection getConnection() {
        return connection;
    }
}
