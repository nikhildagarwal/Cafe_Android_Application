/**
 * package for all files concerning coffee menu item
 */
package com.example.project5cafeapp.cofee;

/**
 * Implementation for enum class of size of coffee.
 * Each enum object has a String attribute "Size" which is the descriptive size of the coffee.
 * Each enum object has a double attribute "price" which is the cost of the respective size.
 * @author Nikhil Agarwal, Hyeon Oh
 */
public enum Size {

    /**
     * Short coffee size
     */
    SHORT(1.89,"Short"),

    /**
     * Tall coffee size
     */
    TALL(2.29,"Tall"),

    /**
     * Grande coffee size
     */
    GRANDE(2.69,"Grande"),

    /**
     * Venti coffee size
     */
    VENTI(3.09,"Venti");

    /**
     * double price of coffee
     */
    private double price;

    /**
     * String descriptive size of coffee
     */
    private String size;

    /**
     * Constructor for enum Object
     * Cannot be instantiated outside of this class.
     * Must init object above to use outside of class.
     * @param price double price of coffee.
     * @param size String descriptive size of coffee.
     */
    Size(double price, String size){
        this.price = price;
        this.size = size;
    }

    /**
     * getter method for description of Size of coffee
     * @return String descriptive size of coffee
     */
    public String getSize(){
        return size;
    }

    /**
     * getter method for price of coffee
     * @return double price of coffee
     */
    public double getPrice(){
        return price;
    }

    /**
     * Override method to convert add On object to string
     * @return price as a String
     */
    @Override
    public String toString(){
        return Double.toString(price);
    }
}
