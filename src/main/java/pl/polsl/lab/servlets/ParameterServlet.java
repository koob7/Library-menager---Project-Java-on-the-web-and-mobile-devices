package pl.polsl.lab.servlets;

import java.util.Enumeration;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;

/**
 * Main class of the servlet presenting initial parameters 
 * 
 * @author Gall Anonim
 * @version 1.0
 */
@WebServlet(name = "ParameterServlet", urlPatterns = {"/Parameters"}, initParams = {
    @WebInitParam(name = "param1", value = "100"),
    @WebInitParam(name = "param2", value = "200"),
    @WebInitParam(name = "param3", value = "300")})
public class ParameterServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Response type
        resp.setContentType("text/plain; charset=ISO-8859-2");
        PrintWriter out = resp.getWriter();

        // Servlet configuration - through initialization
        ServletConfig config = getServletConfig();

        // Servlet context
        ServletContext context = config.getServletContext();

        // Server info
        out.println("Server information:");
        out.println("server: " + req.getServerName());
        out.println("port: " + req.getServerPort());
        out.println("servlet container: " + context.getServerInfo());
        out.println();

        // ParametersServlet given to servlet context
        out.println("Parameters passed to servlet context:");
        out.println("Path on server: " + context.getContextPath());
        out.println();
        out.println("Parameters passed to servlet context:");
        Enumeration enumeration = context.getInitParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            out.println(name + " = " + context.getInitParameter(name));
        }
        out.println();

        // Servlet info
        out.println("Servlet information:");
        out.println("call method: " + req.getMethod());
        out.println("registered name: " + config.getServletName());
        out.println("parameters passed during initialization:");
        enumeration = config.getInitParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            out.println(name + " = " + config.getInitParameter(name));
        }
    }

}
