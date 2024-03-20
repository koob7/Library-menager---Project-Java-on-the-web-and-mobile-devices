package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import model.client;
import model.myException;
import model.order;

/**
 * Manages the library system, including clients and orders. This class provides
 * methods to log in clients, add clients and orders, and retrieve information
 * about clients and orders.
 *
 * @version 5.0
 * @author kobie
 */
public class libraryManager {

    //singleton instance of libraryManager class
    private static libraryManager instance;
    // ID of the logged-in user
    private int loggedInUserId = -1;
    //instance of conection with database
    Connection conection;

/**
 * Private constructor for the LibraryManager class. Initializes the database
 * connection and creates tables for clients and orders. Also inserts sample data
 * into the tables for testing purposes.
 *
 * @throws SQLException If a database access error occurs during the initialization.
 */
    private libraryManager() throws SQLException {
        try {
            conection = DriverManager.getConnection("jdbc:derby://localhost:1527/lab", "app", "app");
            Statement statement = conection.createStatement();

            try {
                // Create client table
                statement.executeUpdate("CREATE TABLE client "
                        + "(clientId INTEGER PRIMARY KEY, name VARCHAR(50), "
                        + "surname VARCHAR(50), password VARCHAR(50) )");
                System.out.println("Client table created");

                // Create orders table
                statement.executeUpdate("CREATE TABLE orders "
                        + "(clientId INTEGER, "
                        + "shippingAddres VARCHAR(50), status VARCHAR(50), listOfPurchasedItems VARCHAR(50), "
                        + "FOREIGN KEY (clientId) REFERENCES client(clientId))");
                System.out.println("Orders table created");

                PreparedStatement insertClient = conection.prepareStatement("INSERT INTO client (clientId, name, surname, password) VALUES (?, ?, ?, ?)");
                insertClient.setInt(1, 1);
                insertClient.setString(2, "Adam");
                insertClient.setString(3, "Wronka");
                insertClient.setString(4, "pass1");
                insertClient.executeUpdate();

                insertClient.setInt(1, 2);
                insertClient.setString(2, "Marek");
                insertClient.setString(3, "Kowalczyk");
                insertClient.setString(4, "pass2");
                insertClient.executeUpdate();

                insertClient.setInt(1, 3);
                insertClient.setString(2, "Pawel");
                insertClient.setString(3, "Kowalski");
                insertClient.setString(4, "pass3");
                insertClient.executeUpdate();

                PreparedStatement preparedStatement = conection.prepareStatement("INSERT INTO orders (clientId, shippingAddres, listOfPurchasedItems, status) VALUES (?, ?, ?, ?)");
                // Insert row 1
                preparedStatement.setInt(1, 1);
                preparedStatement.setString(2, "Gliwice");
                preparedStatement.setString(3, "Product A, Product B, ");
                preparedStatement.setString(4, "delivered");
                preparedStatement.executeUpdate();

                // Insert row 2
                preparedStatement.setInt(1, 2);
                preparedStatement.setString(2, "Katowice");
                preparedStatement.setString(3, "Product A, Product BC, ");
                preparedStatement.setString(4, "shipped");
                preparedStatement.executeUpdate();

                // Insert row 3
                preparedStatement.setInt(1, 3);
                preparedStatement.setString(2, "Rybnik");
                preparedStatement.setString(3, "Product C, Product B, ");
                preparedStatement.setString(4, "pending");
                preparedStatement.executeUpdate();

                // Insert row 4
                preparedStatement.setInt(1, 1);
                preparedStatement.setString(2, "Krakow");
                preparedStatement.setString(3, "Product E, Product F, ");
                preparedStatement.setString(4, "canceled");
                preparedStatement.executeUpdate();
            } catch (SQLException sqle) {

            }

        } catch (SQLException sqle) {
            throw sqle;
        }

    }
/**
 * Returns the singleton instance of the LibraryManager. If the instance does not
 * exist, a new instance is created. If an SQLException occurs during the creation
 * of the instance, it is propagated to the caller.
 *
 * @return The singleton instance of the LibraryManager.
 * @throws SQLException If a database access error occurs
 */
    public static libraryManager getInstance() throws SQLException {
        if (instance == null) {
            try {
                instance = new libraryManager();
            } catch (SQLException sqle) {
                throw sqle;
            }
        }

        return instance;
    }

    /**
     * Logs a client into the system.
     *
     * @param id The ID of the client to log in.
     * @param password The client's password.
     * @return True if the login is successful, false when goes wrong.
     * @throws SQLException if a database access error occurs
     */
    public boolean logInto(int id, String password) throws SQLException {
        try (PreparedStatement preparedStatement = conection.prepareStatement(
                "SELECT * FROM client WHERE clientId = ? AND password = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                loggedInUserId = id;
                return true;
            }
        } catch (SQLException sqle) {
            throw sqle;
        }
        return false;
    }

    /**
     * if client is not logged in returns -1
     *
     * @return the number of logged client
     */
    public int getLoggedInUserId() {
        return loggedInUserId;
    }

    /**
     * set the id of logged in user
     *
     * @param i the id of just logged in user
     */
    public void setLoggedInUserId(int id) {
        loggedInUserId = id;
    }

    /**
     * Checks if a client with a specific ID exists.
     *
     * @param id The ID to check.
     * @return True if a client with the given ID exist.
     * @throws myException If no client with the given ID exists.
     * @throws SQLException if a database access error occurs
     */
    public boolean checkId(int id) throws myException, SQLException  {
        try (PreparedStatement preparedStatement = conection.prepareStatement(
                "SELECT * FROM client WHERE clientId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException sqle) {
            throw sqle;
        }
        throw new myException(); // If no client with the given ID exists
    }

    /**
     * Returns the number of clients in the library.
     *
     * @return The number of clients in the library.
     * @throws SQLException if a database access error occurs
     */
    public int getNumberOfClients() throws SQLException {
        try (Statement statement = conection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM client")) {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    /**
     * Adds a new client to the library.
     *
     * @param name The client's first name.
     * @param surname The client's last name.
     * @param password The client's password.
     * @param clientId The ID of the new client.
     * @throws SQLException if a database access error occurs
     */
    public void addClient(String name, String surname, String password, int clientId) throws SQLException{
        try (PreparedStatement insertClient = conection.prepareStatement(
                "INSERT INTO client (clientId, name, surname, password) VALUES (?, ?, ?, ?)")) {
            insertClient.setInt(1, clientId);
            insertClient.setString(2, name);
            insertClient.setString(3, surname);
            insertClient.setString(4, password);
            insertClient.executeUpdate();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    /**
     * Adds a new order to the library.
     *
     * @param clientId The ID of the client placing the order.
     * @param shippingAddress The shipping address for the order.
     * @param listOfPurchasedItems The list of purchased items in the order.
     * @throws SQLException if a database access error occurs
     */
    public void addOrder(int clientId, String shippingAddress, List<String> listOfPurchasedItems, String status) throws SQLException{

        String stringOfPurchasedItems = "";
        for (String string1 : listOfPurchasedItems) {
            stringOfPurchasedItems += string1 + ", ";
        }

        try (PreparedStatement preparedStatement = conection.prepareStatement("INSERT INTO orders (clientId, shippingAddres, listOfPurchasedItems, status) VALUES (?, ?, ?, ?)")) {
            // Insert row 1
            preparedStatement.setInt(1, clientId);
            preparedStatement.setString(2, shippingAddress);
            preparedStatement.setString(3, stringOfPurchasedItems);
            preparedStatement.setString(4, status);
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            throw sqle;
        }

    }

    /**
     * GetsReturns
     *
     * @return The list of clients in the library.
     * @throws SQLException if a database access error occurs
     */
    public List<client> getListOfClients() throws SQLException{
        List<client> clients = new ArrayList<>();
        try (Statement statement = conection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM client")) {
            while (resultSet.next()) {
                int clientId = resultSet.getInt("clientId");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String password = resultSet.getString("password");
                clients.add(new client(name, surname, password, clientId));
            }
        } catch (SQLException sqle) {
            throw sqle;
        }
        return clients;
    }

    /**
     * Returns the list of orders in the library.
     *
     * @return The list of orders in the library.
     * @throws SQLException if a database access error occurs
     */
    public List<order> getListOfOrders() throws SQLException{
        List<order> orders = new ArrayList<>();
        try (Statement statement = conection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM orders")) {
            while (resultSet.next()) {
                int clientId = resultSet.getInt("clientId");
                String shippingAddress = resultSet.getString("shippingAddres");
                String status = resultSet.getString("status");
                String listOfPurchasedItems = resultSet.getString("listOfPurchasedItems");
                List<String> purchasedItems = Arrays.asList(listOfPurchasedItems.split(", "));
                orders.add(new order(clientId, shippingAddress, purchasedItems, status));
            }
        } catch (SQLException sqle) {
            throw sqle;
        }
        return orders;
    }

    /**
     * Returns the orders for a specific client.
     *
     * @param clientId The ID of the client.
     * @return The list of orders for the specified client.
     * @throws SQLException if a database access error occurs
     */
    public List<order> getOrdersForClient(int clientId) throws SQLException{
        List<order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement = conection.prepareStatement(
                "SELECT * FROM orders WHERE clientId = ?")) {
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String shippingAddress = resultSet.getString("shippingAddres");
                String status = resultSet.getString("status");
                String listOfPurchasedItems = resultSet.getString("listOfPurchasedItems");
                List<String> purchasedItems = Arrays.asList(listOfPurchasedItems.split(", "));
                orders.add(new order(clientId, shippingAddress, purchasedItems, status));
            }
        } catch (SQLException sqle) {
            throw sqle;
        }
        return orders;
    }

    /**
     * Returns the client with a specific ID.
     *
     * @param id The ID of the client to return.
     * @return The client with the given ID, or null if not found.
     * @throws SQLException if a database access error occurs
     */
    public client getClient(int id) throws SQLException{
        try (PreparedStatement preparedStatement = conection.prepareStatement(
                "SELECT * FROM client WHERE clientId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String password = resultSet.getString("password");
                return new client(name, surname, password, id);
            }
        } catch (SQLException sqle) {
            throw sqle;
        }
        return null;
    }

}
