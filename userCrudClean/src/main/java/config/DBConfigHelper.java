package config;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfigHelper {
    private static final Logger log = LogManager.getLogger(DBConfigHelper.class.getName());
    
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "validate";
    
    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%d/%s";
    private static final String HOST = "localhost";
    private static final int PORT = 5432;
    private static final String DB_NAME = "chat_db";
    private static final String USER = "stepan";
    private static final String PASSWORD = "polkiujhy";
    private static final String URL;
    
    // Session factory for Hibernate
    private static SessionFactory sessionFactory;
    // Connection for JDBC
    private static Connection connection;
    
    // Singleton link
    private static DBConfigHelper dbConfigHelper;
    
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
    }
    
    public static DBConfigHelper getInstance() {
        if (dbConfigHelper == null) {
            dbConfigHelper = new DBConfigHelper();
        }
        
        return dbConfigHelper;
    }
    
    // Private constructor that never can create somebody
    private DBConfigHelper() {}
    
    private Configuration getPostgresConfiguration() {
        Configuration cnfg = new Configuration();
        cnfg.addAnnotatedClass(User.class);
        
        cnfg.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
        cnfg.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        cnfg.setProperty("hibernate.connection.url", URL);
        cnfg.setProperty("hibernate.connection.username", USER);
        cnfg.setProperty("hibernate.connection.password", PASSWORD);
        cnfg.setProperty("hibernate.show_sql", hibernate_show_sql);
        cnfg.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return cnfg;
    }
    
    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
    
    private void printConnectionInfo(Connection con) throws SQLException {
        log.info("DB name: " + con.getMetaData().getDatabaseProductName());
        log.info("DB version: " + con.getMetaData().getDatabaseProductVersion());
        log.info("Driver: " + con.getMetaData().getDriverName());
        log.info("Autocommit: " + con.getAutoCommit());
    }
    
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = getInstance().createSessionFactory(getInstance().getPostgresConfiguration());
            log.info("Start with Hibernate session factory.");
            
            try {
                // One way to get connection version 5.0.2
                Connection connection = sessionFactory.
                        getSessionFactoryOptions().getServiceRegistry().
                        getService(ConnectionProvider.class).getConnection();
                getInstance().printConnectionInfo(connection);
            } catch (SQLException e) {
                log.info("Failed to get connection({}).", e.getMessage());
            }
    
        }

        return sessionFactory;
    }
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection =  DriverManager.getConnection(URL, USER, PASSWORD);
                log.info("Start with JDBC connection.");
    
                getInstance().printConnectionInfo(connection);
            } catch (SQLException e) {
                log.info("Failed to get connection({}).", e.getMessage());
            }
        }
        
        return connection;
    }
}
