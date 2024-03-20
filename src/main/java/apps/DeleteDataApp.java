package apps;

import java.sql.*;

public class DeleteDataApp {

    public void deleteData() {
        
        // make a connection to DB
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            Statement statement = con.createStatement();
            // Usuwamy dane z tabeli
            int numberOfDeletedRows = statement.executeUpdate("DELETE FROM Dane WHERE nazwisko = 'Mickiewicz'");
            System.out.println("Data removed: " + numberOfDeletedRows + " rows");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public static void main(String[] args) {
        DeleteDataApp deleteDataApp = new DeleteDataApp();
        deleteDataApp.deleteData();
    }
}
