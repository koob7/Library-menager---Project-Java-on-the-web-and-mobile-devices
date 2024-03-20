package pl.polsl.lab.servlets;

import java.util.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;

/** 
 * Servlet generating as an answer the current date and time
 * 
 * @author Gall Anonim
 * @version 1.0
 */
@WebServlet("/UserDate")
public class UserDateServlet extends HttpServlet {

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
        resp.setContentType("text/plain; charset=ISO-8859-2");
        PrintWriter out = resp.getWriter();

        String date = new Date().toString();
        out.println(date);
    }
}
