/**
 * package for all files concerning coffee menu item
 */
package com.test.cafe_app.cofee;

/**
 * Implementation for enum class of additions on coffee.
 * Each enum object has a String attribute "addOn" which is a description of the addOn.
 * @author Nikhil Agarwal, Hyeon Oh
 */
public enum AddOn {

    /**
     * Sweet Cream add on
     */
    SWEETCREAM("Sweet Cream"),

    /**
     * French Vanilla add on
     */
    FRENCHVANILLA("French Vanilla"),

    /**
     * Irish Cream add on
     */
    IRISHCREAM("Irish Cream"),

    /**
     * Caramel add on
     */
    CARAMEL("Caramel"),

    /**
     * Mocha infusion add on
     */
    MOCHA("Mocha");

    /**
     * Attribute of enum object (add on)
     */
    private String addition;

    /**
     * Constructor for enum Object
     * Cannot be instantiated outside of this class.
     * Must init object above to use outside of class.
     * @param addition the string description of our addition.
     */
    AddOn(String addition){
        this.addition = addition;
    }

    /**
     * Override method to convert add On object to string
     * @return add on as a String
     */
    @Override
    public String toString(){
        return addition;
    }

}
