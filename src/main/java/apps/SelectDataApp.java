package apps;

import java.sql.*;

public class SelectDataApp {

    public void selectData() {

        // make a connection to DB
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Dane");
            // Przeglądamy otrzymane wyniki
            System.out.printf("|%-3s|%-12s|%-10s|%-5s|\n", "ID", "Nazwisko", "Imię", "Ocena");
            System.out.println("-----------------------------------");
            while (rs.next()) {
                System.out.printf("|%-3s", rs.getInt("id"));
                System.out.printf("|%-12s", rs.getString("nazwisko"));
                System.out.printf("|%-10s", rs.getString("imie"));
                System.out.printf("| %-4s|\n", rs.getFloat("ocena"));
            }
            System.out.println("-----------------------------------");
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public static void main(String[] args) {
        SelectDataApp selectDataApp = new SelectDataApp();
        selectDataApp.selectData();
    }
}
