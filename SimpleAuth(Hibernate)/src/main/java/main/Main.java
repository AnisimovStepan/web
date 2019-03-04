package main;

import dao.UserProfileDao;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import services.DBService;
import servlets.SignInServlet;
import servlets.SignUpServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create config to connect db
        DBService dbService = new DBService();
        // Create Dao
        UserProfileDao userProfileDao = new UserProfileDao(dbService.getSessionFactory());
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        // Add sign up servlet
        context.addServlet(new ServletHolder(new SignUpServlet(userProfileDao)), "/signup");
        // Add sign in servlet
        context.addServlet(new ServletHolder(new SignInServlet(userProfileDao)), "/signin");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
