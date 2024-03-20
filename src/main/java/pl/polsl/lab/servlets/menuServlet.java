/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.lab.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.libraryManager;
import model.myException;

/**
 * @version 5.0
 * @author kobie
 */
@WebServlet("/menu")
public class menuServlet extends HttpServlet {

    private libraryManager libraryPart1;

    static int exception1(String string) throws myException {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new myException();
        }
    }

    int zalogowano = -1;

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
            /* TODO output your page here. You may use following sample code. */

            out.println("<!DOCTYPE html>");
            out.println("<html>");

            try {
                libraryPart1 = libraryManager.getInstance();
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("zalogowano")) {
                            zalogowano = exception1(cookie.getValue());
                            libraryPart1.setLoggedInUserId(zalogowano);
                            break;
                        }
                    }
                }
                Cookie cookie = new Cookie("zalogowano", Integer.toString(zalogowano));
                response.addCookie(cookie);

                if (zalogowano < 0) {
                    out.println("<head>");
                    out.println("<title>menu</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>menu</h1>");
                    out.println(" <script> window.history.clear();</script>");
                    out.println("<form>");
                    out.println("<input type=\"submit\" value=\"zaloguj\" name=\"submit\"><br><br>");
                    out.println("<input type=\"submit\" value=\"wyswietl uzytkownikow\" name=\"submit\">");
                    out.println("</form>");

                    if ("zaloguj".equals(request.getParameter("submit"))) {
                        response.sendRedirect("/JavaServlets/loginServlet");
                    }
                    if ("wyswietl uzytkownikow".equals(request.getParameter("submit"))) {
                        response.sendRedirect("/JavaServlets/displayUserServlet");
                    }

                    out.println("</body>");
                } else {
                    out.println("<head>");
                    out.println("<title>menu</title>");
                    out.println("</head>");
                    out.println("<body>");

                    out.println("<h1>menu</h1>");
                    out.println(" <script> window.history.clear();</script>");
                    out.println("<form>");
                    out.println("<input type=\"submit\" value=\"wyloguj\" name=\"submit\"><br><br>");
                    out.println("<input type=\"submit\" value=\"dodaj zamowienie\" name=\"submit\"><br><br>");
                    out.println("<input type=\"submit\" value=\"dodaj klienta\" name=\"submit\"><br><br>");
                    out.println("<input type=\"submit\" value=\"wyswietl uzytkownikow\" name=\"submit\"><br><br>");
                    out.println("<input type=\"submit\" value=\"wyswietl zamowienia\" name=\"submit\"><br><br>");
                    out.println("</form>");
                    if ("wyswietl uzytkownikow".equals(request.getParameter("submit"))) {
                        response.sendRedirect("/JavaServlets/displayUserServlet");
                    }
                    if ("wyswietl zamowienia".equals(request.getParameter("submit"))) {
                        response.sendRedirect("/JavaServlets/displayOrder");
                    }
                    if ("dodaj klienta".equals(request.getParameter("submit"))) {
                        response.sendRedirect("/JavaServlets/addUser");
                    }
                    if ("dodaj zamowienie".equals(request.getParameter("submit"))) {
                        response.sendRedirect("/JavaServlets/addOrder");
                    }
                    if ("wyloguj".equals(request.getParameter("submit"))) {
                        cookies = request.getCookies();
                        cookie = new Cookie("zalogowano", Integer.toString(-1));
                        response.addCookie(cookie);
                        libraryPart1.setLoggedInUserId(-1);
                        response.sendRedirect("/JavaServlets/menu");
                    }
                }

                out.println("</body>");

            } catch (myException e) {
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
