package servlets;

import services.ResourceService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourceServlet extends HttpServlet {
    public static final String RESOURCE_URL = "/resources";
    private final ResourceService resourceService;
    
    public ResourceServlet(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getParameter("path");
        
        // Check correct values
        if (path == null || path.equals("")) {
            resp.getWriter().println("Path is empty or null.");
            return;
        }
        
        try {
            resourceService.readXMLResource(path);
            resp.getWriter().println(String.format("File read successfully.\n  Name = %s \n  Age = %s",
                    resourceService.getTestResourceName(), resourceService.getTestResourceAge()));
        }
        catch (Exception e) {
            resp.getWriter().println(e.getMessage());
        }
    }
}
