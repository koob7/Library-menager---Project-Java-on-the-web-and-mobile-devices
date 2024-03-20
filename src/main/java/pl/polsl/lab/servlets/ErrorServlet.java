package pl.polsl.lab.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;

/** 
 * Servlet handling runtime error
 * 
 * @author Gall Anonim
 * @version 1.0
 */
@WebServlet("/Error")
public class ErrorServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException when a servlet-specific error occurs
     * @throws IOException when an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain; charset=ISO-8859-2");
        PrintWriter out = response.getWriter();

        out.println("Incorrect servlet call!!!");
    }
}
