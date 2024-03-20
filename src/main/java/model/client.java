package model;



/**
 * Represents a client in the order management system.
 * A client is identified by a unique client ID and has a name, surname, and a password.
 *
 * @version 5.0
 * @author kobie
 */
public class client {

    private final int clientId; // Unique ID of the client
    private final String name; // Name of the client
    private final String surname; // Surname of the client
    private final String password; // Password associated with the client

    /**
     * Constructor for creating a client with a name, surname, password, and a unique client ID.
     *
     * @param name The name of the client.
     * @param surname The surname of the client.
     * @param password The password associated with the client.
     * @param clientId The unique ID of the client.
     */
    public client(String name, String surname, String password, int clientId) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.clientId = clientId;
    }

    /**
     * Return the name of the client.
     *
     * @return The name of the client.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the surname of the client.
     *
     * @return The surname of the client.
     */
    public String getSurname() {
        return this.surname;
    }

    /**
     * Get the unique ID of the client.
     *
     * @return The client ID.
     */
    public int getClientId() {
        return this.clientId;
    }

    /**
     * Get the password associated with the client.
     *
     * @return The client's password.
     */
    public String getClientPassword() {
        return this.password;
    }

}
