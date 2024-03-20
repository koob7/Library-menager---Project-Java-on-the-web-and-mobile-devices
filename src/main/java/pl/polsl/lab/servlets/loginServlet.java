/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.lab.servlets;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.libraryManager;
import model.myException;

/**
 * @version 5.0
 * @author kobie
 */
@WebServlet("/loginProces")
public class loginServlet extends HttpServlet {

    private libraryManager libraryPart1;

    static int exception1(String string) throws myException {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new myException();
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use the following sample code. */

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Login menu</title>");
            out.println("</head>");
            out.println("<body>");
            try {
                libraryPart1 = libraryManager.getInstance();
                out.println("<h1>Login menu</h1>");
                out.println("<form>");
                out.println("Podaj id użytkownika: <input type=\"text\" name=\"id\"><br><br>");
                out.println("Podaj hasło: <input type=\"password\" name=\"password\"><br><br>");
                out.println("<input type=\"submit\" value=\"zaloguj\" name=\"submit\"><br><br>");
                out.println("<input type=\"submit\" value=\"wstecz\" name=\"submit\">");
                out.println("</form>");

                if ("wstecz".equals(request.getParameter("submit"))) {
                    response.sendRedirect("/JavaServlets/menu");
                }

                if ("zaloguj".equals(request.getParameter("submit"))) {

                    if (!request.getParameter("password").equals("") && !request.getParameter("id").equals("")) {
                        if (libraryPart1.logInto(exception1(request.getParameter("id")), request.getParameter("password"))) {
                            //ustaw ciasteczko na przekazane id      
                            //response.Navigate();
                            Cookie[] cookies = request.getCookies();
                            Cookie cookie = new Cookie("zalogowano", request.getParameter("id"));
                            response.addCookie(cookie);
                            response.sendRedirect("/JavaServlets/menuServlet");
                        } else {

                            out.println("<h1>Error</h1>");
                            out.println("<p style=\"color: red;\">podano bldne dane logowania</p>");
                            out.println("<button onclick=\"goBack()\">Wstecz</button>");
                            out.println("<script>");
                            out.println("function goBack() {");
                            out.println("  window.history.back();");
                            out.println("}");
                            out.println("</script>");

                        }
                    } else {

                        out.println("<h1>Error</h1>");
                        out.println("<p style=\"color: red;\">podaj wszystkie dane</p>");
                        out.println("<button onclick=\"goBack()\">Wstecz</button>");
                        out.println("<script>");
                        out.println("function goBack() {");
                        out.println("  window.history.back();");
                        out.println("}");
                        out.println("</script>");

                    }

                }
            } catch (myException e) {

                out.println("<h1>Error</h1>");
                out.println("<p style=\"color: red;\">podano blddny format id</p>");
                out.println("<button onclick=\"goBack()\">Wstecz</button>");
                out.println("<script>");
                out.println("function goBack() {");
                out.println("  window.history.back();");
                out.println("}");
                out.println("</script>");

            } catch (SQLException e) {
                out.println("<h1>Error</h1>");
                out.println("<p style=\"color: red;\">Błąd łączenia z bazą danych</p>");
                out.println("<button onclick=\"goBack()\">Wstecz</button>");
                out.println("<script>");
                out.println("function goBack() {");
                out.println("  window.history.back();");
                out.println("}");
                out.println("</script>");
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //ServletContext servletContext = getServletContext();

        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
