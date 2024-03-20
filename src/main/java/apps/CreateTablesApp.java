package apps;

import java.sql.*;

public class CreateTablesApp {

    public void createTables() {

        // make a connection to DB
        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app")) {
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE Dane5 "
                    + "(id INTEGER, nazwisko VARCHAR(50), "
                    + "imie VARCHAR(50), ocena FLOAT )");
            System.out.println("Table created");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public static void main(String[] args) {
        CreateTablesApp createTablesApp = new CreateTablesApp();
        createTablesApp.createTables();
    }
}
