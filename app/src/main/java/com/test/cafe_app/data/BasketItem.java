/**
 * Package for all data java files (structures)
 */
package com.test.cafe_app.data;

import com.test.cafe_app.donut.YeastDonut;
import com.test.cafe_app.donut.flavors.YeastFlavor;

/**
 * Class to hold a menuItem and the quantity of that menuItem.
 * Orders are made up of many basketItems.
 * This class is observable in the donutBasketView and the orderBasketView.
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class BasketItem {

    /**
     * Indicates that basket is empty in the orderView
     */
    public static final int BASKETVIEW = 0;

    /**
     * Indicates that the basket is empty in the donutView
     */
    public static final int DONUTVIEW = 1;

    /**
     * MenuItem attribute of BasketItem
     */
    private MenuItem menuItem;

    /**
     * The said quantity of the MenuItem object
     */
    private int quantity;

    /**
     * Main Constructor for menuItem class
     * @param menuItem MenuItem object
     * @param quantity int quantity of that MenuItem
     */
    public BasketItem(MenuItem menuItem,int quantity){
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    /**
     * Overload constructor
     * Default sets values to null and 0
     */
    public BasketItem(){
        this.menuItem = null;
        this.quantity = 0;
    }

    /**
     * Creates a basketItem with null MenuItem and specific quantity.
     * In this case the item is used to signal a code and trigger a different BasketItem.toString() output.
     * @param quantity int quantity of item.
     */
    public BasketItem(int quantity){
        this.menuItem = null;
        this.quantity = quantity;
    }

    /**
     * Getter method for MenuItem
     * @return MenuItem object
     */
    public MenuItem getMenuItem(){
        return menuItem;
    }

    /**
     * Getter method for quantity
     * @return int quantity
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * Override of Java Object class method: toString().
     * Returns the basket Item as a string with both the quantity and the MenuItem returned.
     * Also returns error codes such as "Basket is Empty!"
     * @return String BasketItem object as String
     */
    @Override
    public String toString(){
        if(menuItem == null && quantity == BASKETVIEW){
            return "Basket is Empty!";
        }
        if(menuItem == null && quantity == DONUTVIEW){
            return "Basket is Empty!";
        }
        if(menuItem.equals(new YeastDonut(YeastFlavor.TEST))){
            return "Order Placed!\nOrder Number: "+quantity;
        }
        return quantity+ " x " + menuItem.toString();
    }
}
