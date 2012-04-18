package org.jasig.portlet.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple servlet that sets some headers
 * 
 * @author Eric Dalquist
 */
public class SimpleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_CREATED);
        
        resp.setContentType("text/plain");
        
        resp.setHeader("SimpleHeader", "SimpleValue");
        resp.setIntHeader("SimpleIntHeader", 1234);
        resp.setDateHeader("SimpleDateHeader", System.currentTimeMillis());
        
        final PrintWriter writer = resp.getWriter();
        writer.println("Simple Servlet Content: ");
        writer.println(new Date()); 
    }

}
