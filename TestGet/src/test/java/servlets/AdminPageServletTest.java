package servlets;

import org.junit.Before;
import org.junit.Test;

import services.AccountServerImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdminPageServletTest {
    // private AccountServerImpl accountServer = mock(AccountServerImpl.class);
     private AccountServerImpl accountServer = new AccountServerImpl(10);
    
    private HttpServletResponse getMockedResponse(StringWriter stringWriter) throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        
        final PrintWriter writer = new PrintWriter(stringWriter);
        
        when(response.getWriter()).thenReturn(writer);
        
        return response;
    }
    
    private HttpServletRequest getMockedRequest(String url) {
        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        
        when(request.getSession()).thenReturn(httpSession);
        when(request.getPathInfo()).thenReturn(url);
        
        return request;
    }
    
    // @Before
    // public void setUp() throws Exception {
    //     accountServer.setUsersLimit(10);
    // }
    
    @Test
    public void testRemove() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(AdminPageServlet.ADMIN_URL);
        // when(request.getParameter("remove")).thenReturn("");
    
        AdminPageServlet homePage = new AdminPageServlet(accountServer);
        
        homePage.doGet(request, response);
        
        assertEquals("10", stringWriter.toString().trim());
        // verify(accountServer, times(1)).getUsersLimit();
    }
}