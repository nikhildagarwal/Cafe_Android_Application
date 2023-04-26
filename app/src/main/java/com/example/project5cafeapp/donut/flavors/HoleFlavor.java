/**
 * package for all enum classes of donut flavors
 */
package com.example.project5cafeapp.donut.flavors;

/**
 * Implementation for enum class HoleFlavor which consists of flavors for donut holes.
 * Each enum object contains an attribute "flavor": String
 * @author Nikhil Agarwal, Hyeon Oh
 */
public enum HoleFlavor {

    /**
     * Glazed donut hole
     */
    GLAZED("Glazed"),

    /**
     * Chocolate donut hole
     */
    CHOCOLATE("Chocolate"),

    /**
     * Jelly donut hole
     */
    JELLY("Jelly");

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
    HoleFlavor (String flavor){
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
