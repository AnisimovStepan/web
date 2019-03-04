package main;

import beans.ResourceServerController;
import beans.ResourceServerControllerMBean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import services.ResourceService;
import servlets.ResourceServlet;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());
    
    public static void main(String[] args) throws Exception {
        ResourceService resourceService = new ResourceService();
    
        ResourceServerControllerMBean adminStatistics = new ResourceServerController(resourceService);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=ResourceServerController");
        mbs.registerMBean(adminStatistics, name);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        // Admin servlet
        context.addServlet(new ServletHolder(
                new ResourceServlet(resourceService)), ResourceServlet.RESOURCE_URL);
    
        Server server = new Server(8080);
        
        server.setHandler(context);

        server.start();
        logger.info("Server started");
        server.join();
    }
}
