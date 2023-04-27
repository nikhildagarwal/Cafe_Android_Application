/**
 * Package for all data java files (structures)
 */
package com.example.project5cafeapp.data;

import java.util.ArrayList;

/**
 * Implementation for orders in our project.
 * Each order object consists of an integer order number and a hashmap structure for the order itself.
 * Keys: menuItems, Values: quantity of corresponding menuItem
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class Order {

    /**
     * To indicate that there are no orders
     */
    public static final int EMPTY = 1;

    /**
     * To indicate that there are no orders and the file has been created.
     */
    public static final int EMPTYFILE = -1;

    /**
     * integer order number
     */
    private int orderNumber;

    /**
     * ArrayList of basketItems
     * Contains all basket items (does not have to be unique).
     */
    private ArrayList<BasketItem> orderList;

    /**
     * Constructor for order Object.
     * Initializes a new, empty order.
     * @param orderNumber The number of the order
     */
    public Order(int orderNumber){
        this.orderNumber = orderNumber;
        this.orderList = new ArrayList<>();
    }

    /**
     * Overload Constructor for order Object.
     * Creates an object with a non-empty, user selected order.
     * @param orderNumber the number of the order
     * @param orderList unique basketItems
     */
    public Order(int orderNumber, ArrayList<BasketItem> orderList){
        this.orderNumber = orderNumber;
        this.orderList = orderList;
    }

    /**
     * Getter method for order Number
     * @return integer order Number
     */
    public int getOrderNumber(){
        return orderNumber;
    }

    /**
     * Getter method for list of items in the order.
     * @return ArrayList of BasketItem objects
     */
    public ArrayList<BasketItem> getOrderList(){
        return orderList;
    }

    /**
     * Method to calculate the total price of an order including tax
     * @return String value of the total price
     */
    public String getOrderPrice(){
        double total = 0.0;
        for(int i = 0;i<orderList.size();i++){
            total += (orderList.get(i).getMenuItem().itemPrice() * orderList.get(i).getQuantity());
        }
        total = total * 1.06625;
        return format(Math.round(total * 100.0) / 100.0);
    }

    /**
     * Override of toString() method in Java Object class
     * @return the order object as a String that detains the contents of each order.
     */
    @Override
    public String toString(){
        if(orderNumber == EMPTYFILE){
            return "Order Summary Created.\nFilename: orderSummary.txt\nPlease close all tabs to view this file.";
        }
        if(orderNumber == EMPTY){
            return "No Orders Yet";
        }
        String output = this.getOrderNumber()+":\n\n";
        for(int i = 0;i<orderList.size();i++){
            output += (orderList.get(i).toString() + "\n");
        }
        return output;
    }

    /**
     * Formats a double number into money format
     * Ex: 923.5  -->  $923.50
     * @param total a double value
     * @return String in the formal of $0.00
     */
    private String format(double total){
        String totalString = Double.toString(total);
        String[] splitter = totalString.split("\\.");
        if(splitter[1].length()<2){
            totalString += "0";
        }
        return totalString;
    }
}
