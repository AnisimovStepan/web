package main;

import beans.AccountServerController;
import beans.AccountServerControllerMBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import services.AccountServer;
import services.AccountServerImpl;
import servlets.AdminPageServlet;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());
    
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            logger.error("Use port as the first argument");
            System.exit(1);
        }
    
        int port = Integer.valueOf(args[0]);
        // int port = 5050;
        
        logger.info(String.format("Starting at http://127.0.0.1:%d", port));
    
        AccountServer accountServer = new AccountServerImpl(10);
    
        AccountServerControllerMBean adminStatistics = new AccountServerController(accountServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=AccountServerController.usersLimit");
        mbs.registerMBean(adminStatistics, name);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        // Admin servlet
        context.addServlet(new ServletHolder(
                new AdminPageServlet((AccountServerImpl) accountServer)), AdminPageServlet.ADMIN_URL);
    
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("src/main/resources/static");
        resource_handler.setWelcomeFiles(new String[]{"index.html"});
    
        Server server = new Server(port);
        
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        logger.info("Server started");
        server.join();
    }
}
