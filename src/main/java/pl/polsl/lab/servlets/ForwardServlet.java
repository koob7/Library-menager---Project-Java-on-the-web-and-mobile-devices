package pl.polsl.lab.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;

/**
 * Main class of the servlet that demonstrates communicaton between servlets and
 * the ways of redirecting the request
 * 
 * @author Gall Anonim
 * @version 1.0
 */
@WebServlet("/Forward")
public class ForwardServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("data");
        if (action.equals("time")) {
            response.setContentType("text/plain; charset=ISO-8859-2");
            PrintWriter out = response.getWriter();

            out.println("Current date:");
            getServletContext().getRequestDispatcher("/UserDate").include(request, response);
        } else // action == "page"
        {
            String url = request.getParameter("address");

            if ((url.length() == 0) || url.equals("http://")) {
                getServletContext().getRequestDispatcher("/Error").forward(request, response);
            } else {
                response.sendRedirect(url);
            }
        }
    }
}
