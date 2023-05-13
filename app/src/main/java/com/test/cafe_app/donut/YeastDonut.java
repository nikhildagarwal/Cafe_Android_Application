/**
 * package for all donut objects
 * yeast donuts, cake donuts, donut holes
 */
package com.test.cafe_app.donut;

import com.test.cafe_app.data.MenuItem;
import com.test.cafe_app.donut.flavors.YeastFlavor;


/**
 * YeastDonut object which extends the superClass MenuItem
 * Consists of one variable: an enum object YeastFlavor
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class YeastDonut extends MenuItem {

    /**
     * final price of yeast donut
     */
    public static final double yeastDonutPrice = 1.59;

    /**
     * Enum object YeastFlavor describing flavor of donut
     */
    private YeastFlavor yeastFlavor;

    /**
     * Constructor overload for YeastDonut
     * Sets the flavour to an input object.
     * @param yeastFlavor enum object YeastFlavor describing flavor of donut
     */
    public YeastDonut(YeastFlavor yeastFlavor){
        this.yeastFlavor = yeastFlavor;
    }

    /**
     * Default constructor for YeastDonut object
     * Sets flavour to "plain" by default
     */
    public YeastDonut(){
        this.yeastFlavor = YeastFlavor.PLAIN;
    }

    /**
     * getter method to return the yeastFlavor attribute of the YeastDonut object
     * @return returns YeastFlavor object
     */
    public YeastFlavor getYeastFlavor(){
        return yeastFlavor;
    }

    /**
     * Implements the abstract method itemPrice() from MenuItem superClass
     * Each yeast donut costs $1.59
     * @return double total cost of Yeast donut (NOT INCLUDING TAX)
     */
    @Override
    public double itemPrice(){
        return yeastDonutPrice;
    }

    /**
     * Override method for toString() method in Java Object class.
     * gets the YeastDonut object as a printable string.
     * @return String of yeast donut object (along with flavor)
     */
    @Override
    public String toString(){
        return "Yeast Donut - "+yeastFlavor.getFlavor();
    }

    /**
     * Override equals() method in java Object class.
     * @param obj YeastDonut Object we want to compare to initial YeastDonut Object
     *            Syntax: YD1.equals(YD2)
     * @return true if the flavours of each donut is the same. false otherwise.
     */
    @Override
    public boolean equals(Object obj){
        YeastDonut yeastDonut;
        try{
            yeastDonut = (YeastDonut) obj;
        }catch(Exception e) {
            return false;
        }
        if(yeastFlavor.equals(yeastDonut.getYeastFlavor())){
            return true;
        }
        return false;
    }
}
