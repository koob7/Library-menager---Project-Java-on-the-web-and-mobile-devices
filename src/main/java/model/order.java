package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;





/**
 * Represents an order in the order management system.
 * An order is associated with a client, contains a shipping address, and a list of purchased items.
 *
 * @version 5.0
 * @author kobie
 */

public class order {


    public enum OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELED
}

    private final int clientId; // ID of the client who placed the order
    private List<String> listOfPurchasedItems = Collections.synchronizedList(new ArrayList<>()); // List of items purchased in the order
    private final String shippingAddress; // Shipping address for the order
    private OrderStatus status; // Status of the order
    /**
     * Constructor for creating an order with client ID, shipping address, and a list of purchased items.
     *
     * @param clientId The ID of the client who placed the order.
     * @param shippingAddress The shipping address for the order.
     * @param listOfPurchasedItems A list of items purchased in the order.
     * @param status                The status of the order.
     */
    public order(int clientId, String shippingAddress, List<String> listOfPurchasedItems, String status) {
        this.clientId = clientId;
        this.shippingAddress = shippingAddress;
        this.listOfPurchasedItems = Collections.synchronizedList(new ArrayList<>(listOfPurchasedItems));
        if (status.equals("pending"))
        {
            this.status = OrderStatus.PENDING;
        }
        else if (status.equals("processing"))
        {
            this.status = OrderStatus.PROCESSING;
        }
        else if (status.equals("shipped"))
        {
            this.status = OrderStatus.SHIPPED;
        }
        else if (status.equals("delivered"))
        {
            this.status = OrderStatus.DELIVERED;
        }
        else if (status.equals("canceled"))
        {
            this.status = OrderStatus.CANCELED;
        }
        else 
        {
            this.status = OrderStatus.PENDING;
        }
    }

    /**
     * Get the client ID associated with this order.
     *
     * @return The client ID.
     */
    public int getClientId() {
        return this.clientId;
    }
    /**
     * Get the order status.
     *
     * @return The status.
     */
    public OrderStatus getStatus()
    {
        return status;
    }
    public String getStatusText()
    {
        
    if (status == OrderStatus.PENDING)
        {
            return "pending";
        }
        else if (status == OrderStatus.PROCESSING)
        {
            return "processing";
        }
        else if (status == OrderStatus.SHIPPED)
        {
            return "shipped";
        }
        else if (status == OrderStatus.DELIVERED)
        {
            return "delivered";
        }
        else if (status == OrderStatus.CANCELED)
        {
            return "canceled";
        }
    return "error";
    }

    /**
     * Get the list of purchased items in this order.
     *
     * @return A list of purchased items.
     */
    public List<String> getListOfPurchasedItems() {
        return listOfPurchasedItems;
    }
    
      /**
     * Get the list of purchased items in this order in String format.
     *
     * @return A String of purchased items.
     */
        public String getStringOfPurchasedItems() {
        String stringOfPurchasedItems="";
        for (String string1 :listOfPurchasedItems)
        {
            stringOfPurchasedItems+=string1+", ";
        }
        return stringOfPurchasedItems;
    }

    /**
     * Get the shipping address for this order.
     *
     * @return The shipping address.
     */
    public String getshippingAddress() {
        return shippingAddress;
    }

}
