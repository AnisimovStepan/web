package services;

import model.UserProfile;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.SQLException;

public class DBService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "validate";
    
    private SessionFactory sessionFactory;
    
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
            this.sessionFactory = createSessionFactory(getPostgresConfiguration());
            printConnectionInfo();
        }
        catch (SQLException e) {
            System.out.println(String.format("Failed to get connection (%s).", e.getMessage()));
        }
    }
    
    private Configuration getPostgresConfiguration() {
        Configuration cnfg = new Configuration();
        cnfg.addAnnotatedClass(UserProfile.class);
    
        cnfg.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
        cnfg.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        cnfg.setProperty("hibernate.connection.url", URL);
        cnfg.setProperty("hibernate.connection.username", USER);
        cnfg.setProperty("hibernate.connection.password", PASSWORD);
        cnfg.setProperty("hibernate.show_sql", hibernate_show_sql);
        cnfg.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return cnfg;
    }
    
    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
    
    private void printConnectionInfo() throws SQLException {
        // One way to get connection version 5.0.2
        Connection connection = sessionFactory.
                getSessionFactoryOptions().getServiceRegistry().
                getService(ConnectionProvider.class).getConnection();
        
        System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
        System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
        System.out.println("Driver: " + connection.getMetaData().getDriverName());
        System.out.println("Autocommit: " + connection.getAutoCommit());
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
