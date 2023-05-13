/**
 * package for all enum classes of donut flavors
 */
package com.test.cafe_app.donut.flavors;

/**
 * Implementation for enum class CakeFlavor which consists of flavors for cake donuts.
 * Each enum object contains an attribute "flavor": String
 * @author Nikhil Agarwal, Hyeon Oh
 */
public enum CakeFlavor {

    /**
     * Plain vanilla cake
     */
    PLAIN("Plain Vanilla"),

    /**
     * Chocolate cake
     */
    CHOCOLATE("Chocolate Cake"),

    /**
     * Strawberry short cake
     */
    STRAWBERRY("Strawberry Short-Cake");

    /**
     * String description of the flavor
     */
    private final String flavor;

    /**
     * Constructor for enum Object
     * Cannot be instantiated outside of this class.
     * Must init object above to use outside of class.
     * @param flavor String description of the flavor
     */
    CakeFlavor (String flavor){
        this.flavor = flavor;
    }

    /**
     * getter method to get the "flavor" attribute of enum object
     * @return String description of the flavor
     */
    public String getFlavor(){
        return flavor;
    }
}
