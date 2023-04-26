/**
 * package for all files concerning coffee menu item
 */
package com.example.project5cafeapp.cofee;

import com.example.project5cafeapp.data.MenuItem;

import java.util.HashSet;

/**
 * Coffee object which extends the superClass MenuItem
 * Consists of two variables: size of coffee and a HashSet of addOn objects.
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class Coffee extends MenuItem {

    /**
     * Size of the coffee (enum object Size)
     */
    private Size size;

    /**
     * Set of addOns to add into the coffee (enum Object(s) AddOn)
     */
    private HashSet<AddOn> addOnList;

    /**
     * Default Constructor for coffee object
     * Sets size to short
     * no addOns, set is init empty
     */
    public Coffee(){
        this.size = Size.SHORT;
        this.addOnList = new HashSet<>();
    }

    /**
     * Overload1 Constructor for Coffee object
     * Sets addOns to none, init empty set
     * Sets size of coffee to user specified amount.
     * @param size enum object Size
     */
    public Coffee(Size size){
        this.size = size;
        this.addOnList = new HashSet<>();
    }

    /**
     * Overload2 Constructor for Coffee object
     * @param size enum object Size
     * @param addOnList HashSet of enum Object addOns
     */
    public Coffee(Size size, HashSet<AddOn> addOnList){
        this.size = size;
        this.addOnList = addOnList;
    }

    /**
     * Implements the abstract method itemPrice() from MenuItem superClass
     * Each add on costs $0.30
     * Short - $1.89
     * Tall - $2.29
     * Grande - $2.69
     * Venti - $3.09
     * @return double total cost of Coffee (NOT INCLUDING TAX)
     */
    @Override
    public double itemPrice(){
        double addOnPrice = addOnList.size() * 0.30;
        return addOnPrice + size.getPrice();
    }

    /**
     * Override toString() method in java Object class.
     * @return Coffee Object as a String
     */
    @Override
    public String toString(){
        if(addOnList.isEmpty()){
            return "Coffee - Size: "+size.getSize()+", AddOns: None";
        }
        return "Coffee - Size: "+size.getSize()+", AddOns: "+addOnList;
    }

    /**
     * Override equals() method in java Object class.
     * @param obj Coffee Object we want to compare to initial coffee Object
     *            Syntax: Coffee1.equals(Coffee2)
     * @return true if coffee sizes are the same and set of addOns is the same. false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        Coffee coffee;
        try{
            coffee = (Coffee) obj;
        }catch (Exception e){
            return false;
        }
        if(coffee.size==size && coffee.addOnList.equals(addOnList)){
            return true;
        }
        return false;
    }
}
