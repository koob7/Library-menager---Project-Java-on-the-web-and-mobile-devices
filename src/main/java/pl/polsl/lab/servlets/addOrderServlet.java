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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.libraryManager;
import model.myException;

/**
 * @version 5.0
 * @author kobie
 */
@WebServlet("/addOrder")
public class addOrderServlet extends HttpServlet {

    private libraryManager libraryPart1;
    private List<String> listOfItems = new ArrayList<>();

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
            out.println("<title>add order</title>");
            out.println("</head>");
            out.println("<body>");
            try {
                libraryPart1 = libraryManager.getInstance();

                out.println("<h1>add order</h1>");
                out.println("<form>");
                out.println("Status zamówienia: ");
                out.println("<select name=\"status\">");
                out.println("<option value=\"oczekujace\">Oczekujace</option>");
                out.println("<option value=\"przetwarzane\">Przetwarzane</option>");
                out.println("<option value=\"wyslane\">Wyslane</option>");
                out.println("<option value=\"dostarczone\">Dostarczone</option>");
                out.println("<option value=\"anulowane\">Anulowane</option>");
                out.println("</select><br><br>");
                out.println("Podaj id uzytkownika: <input type=\"text\" name=\"id\"><br><br>");
                out.println("Podaj adres wysyłki: <input type=\"text\" name=\"adres\"><br><br>");
                out.println("Podaj zakupiony produkt: <input type=\"text\" name=\"product\"><br><br>");
                out.println("<input type=\"submit\" value=\"dodaj produkt\" name=\"submit\"><br><br>");
                if (!listOfItems.isEmpty()) {
                    out.println("<table border='1' cellpadding='1'>"); // Add border and cellpadding
                    out.println("<tr><th>produkt</th></tr>"); // Use <th> for header cells
                    for (String string1 : listOfItems) {
                        out.println("<tr><td>" + string1 + "</td></tr>");
                    }
                    out.println("</table><br>");
                }
                out.println("<input type=\"submit\" value=\"dodaj zamowienie\" name=\"submit\"><br><br>");
                out.println("<input type=\"submit\" value=\"wstecz\" name=\"submit\">");
                out.println("</form>");

                if ("wstecz".equals(request.getParameter("submit"))) {
                    response.sendRedirect("/JavaServlets/menu");
                }

                if ("dodaj zamowienie".equals(request.getParameter("submit"))) {
                    String status;

                    if (!request.getParameter("id").equals("") && !request.getParameter("adres").equals("") && !listOfItems.isEmpty()) {
                        switch (request.getParameter("status")) {
                            case "oczekujace":
                                status = "pending";
                                break;
                            case "przetwarzane":
                                status = "processing";
                                break;
                            case "wyslane":
                                status = "shipped";
                                break;
                            case "dostarczone":
                                status = "delivered";
                                break;
                            case "anulowane":
                                status = "canceled";
                                break;
                            default:
                                status = "pending";
                                break;

                        }
                        libraryPart1.addOrder(exception1(request.getParameter("id")), request.getParameter("adres"), listOfItems, status);
                        int clientId = exception1(request.getParameter("id"));
                        String address = request.getParameter("adres");

                        String StringOfItems = "";
                        for (String item : listOfItems) {
                            StringOfItems += item;
                        }// your code to get the list of items
                        response.sendRedirect("/JavaServlets/menu");
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

        if ("dodaj produkt".equals(request.getParameter("submit"))) {
            if (!request.getParameter("product").equals("")) {
                listOfItems.add(request.getParameter("product"));
            }

        }

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
