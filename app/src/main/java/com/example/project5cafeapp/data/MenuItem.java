/**
 * Package for all data java files (structures)
 */
package com.example.project5cafeapp.data;

import com.example.project5cafeapp.cofee.Coffee;

/**
 * Abstract class MenuItem
 * Super class to all menu item classes: coffees, yeast donuts, cake donuts, and donut holes
 * @author Nikhil Agarwal, Hyeon Oh
 */
public abstract class MenuItem{

    /**
     * This method is implemented in each class that extends the MenuItem abstract class
     * @return the price of the menuItem
     */
    public abstract double itemPrice();

    /**
     * This method is implemented in each class that extends the MenuItem abstract class
     * Overrides the equal() method in Java Object class
     * @param obj Object that we want to type cast and compare
     * @return true if the objects equal each other.
     */
    @Override
    public abstract boolean equals(Object obj);

    public String getMenuItemType(){
        String itemAsString = this.toString();
        String result = "";
        if(itemAsString.charAt(0)=='C'){
            result += "C ";
        }else if(itemAsString.charAt(0)=='D'){
            result += "D ";
        }else if(itemAsString.charAt(0)=='Y'){
            result += "Y ";
        }
        result += itemAsString.split(" ")[3];
        return result;
    }
}
