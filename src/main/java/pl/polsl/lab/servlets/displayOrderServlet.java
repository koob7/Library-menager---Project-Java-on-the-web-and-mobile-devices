/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.lab.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import model.client;
import model.libraryManager;
import model.myException;
import model.order;

/**
 * @version 5.0
 * @author kobie
 */
@WebServlet("/displayOrder")
public class displayOrderServlet extends HttpServlet {

    private libraryManager libraryPart1;
    int user = -1;

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet displayOrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            try {
                libraryPart1 = libraryManager.getInstance();
                out.println("<h1>wyświetl użytkowników</h1>");

                out.println("<form>");
                out.println("Podaj id uzytkownika: <input type=\"text\" name=\"id\"><br><br>");
                out.println("<input type=\"submit\" value=\"wyswietl zamowienia dla podanego id\" name=\"submit\"><br><br>");
                out.println("<input type=\"submit\" value=\"wstecz\" name=\"submit\">");
                out.println("</form>");

                if ("wstecz".equals(request.getParameter("submit"))) {
                    response.sendRedirect("/JavaServlets/menuServlet");
                }
                if ("wyswietl zamowienia dla podanego id".equals(request.getParameter("submit"))) {

                    if (!request.getParameter("id").equals("")) {
                        user = exception1(request.getParameter("id"));
                    } else {
                        user = -1;

                        out.println("<h1>Error</h1>");
                        out.println("<p style=\"color: red;\">podaj id</p>");
                        out.println("<button onclick=\"goBack()\">Wstecz</button>");
                        out.println("<script>");
                        out.println("function goBack() {");
                        out.println("  window.history.back();");
                        out.println("}");
                        out.println("</script>");

                    }

                }

                out.println("<table border='1' cellpadding='5'>"); // Add border and cellpadding
                out.println("<tr>");
                out.println("<th>ID</th> <th>adres wysyłki</th> <th>status</th> <th>lista produktów</th>"); // Use <th> for header cells
                out.println("</tr>");

// Iterate over clients and output table rows
                if (user < 0) {
                    for (order order1 : libraryPart1.getListOfOrders()) {
                        out.println("<tr>");
                        out.println("<td>" + Integer.toString(order1.getClientId()) + "</td>");
                        out.println("<td>" + order1.getshippingAddress() + "</td>");
                        out.println("<td>" + order1.getStatusText() + "</td>");
                        out.println("<td>" + order1.getStringOfPurchasedItems() + "</td>");
                        out.println("</tr>");
                    }
                } else {
                    for (order order1 : libraryPart1.getOrdersForClient(user)) {
                        out.println("<tr>");
                        out.println("<td>" + Integer.toString(order1.getClientId()) + "</td>");
                        out.println("<td>" + order1.getshippingAddress() + "</td>");
                        out.println("<td>" + order1.getStatusText() + "</td>");
                        out.println("<td>" + order1.getStringOfPurchasedItems() + "</td>");
                        out.println("</tr>");
                    }
                }
                out.println("</table><br>");
            } catch (myException e) {

                out.println("<h1>Error</h1>");
                out.println("<p style=\"color: red;\">podaj poprawny format id</p>");
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
