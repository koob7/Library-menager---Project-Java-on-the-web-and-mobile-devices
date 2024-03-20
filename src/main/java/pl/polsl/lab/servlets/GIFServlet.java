package pl.polsl.lab.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;

/**
 * Main class of the servlet generating the answer in form of a image in GIF
 * standard
 * 
 * @author Gall Anonim
 * @version 1.0
 */
@WebServlet("/GIF")
public class GIFServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            String GIFfile = getServletContext().getRealPath("/WEB-INF/images/duke_swinging.gif");
            response.setContentType("image/gif");
            FileInputStream in = new FileInputStream(GIFfile);
            ServletOutputStream out = response.getOutputStream();
            byte buffer[] = new byte[512];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
    }
}
