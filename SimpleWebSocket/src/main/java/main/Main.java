package main;

import dao.UserProfileDao;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import services.DBService;
import servlets.WebSocketChatServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create Dao
        // UserProfileDao userProfileDao = new UserProfileDao(DBService.instance().getSessionFactory());
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        // Add sign up servlet
        // context.addServlet(new ServletHolder(new SignUpServlet(userProfileDao)), "/signup");
        // Add sign in servlet
        // context.addServlet(new ServletHolder(new SignInServlet(userProfileDao)), "/signin");
    
        context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");
    
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("src/main/resources/static");
        resource_handler.setWelcomeFiles(new String[]{"index.html"});
    
        Server server = new Server(8080);
        
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
