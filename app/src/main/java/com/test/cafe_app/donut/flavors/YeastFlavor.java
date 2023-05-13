/**
 * package for all enum classes of donut flavors
 */
package com.test.cafe_app.donut.flavors;

/**
 * Implementation for enum class YeastFlavor which consists of flavors for yeast donuts.
 * Each enum object contains an attribute "flavor": String
 * @author Nikhil Agarwal, Hyeon Oh
 */
public enum YeastFlavor {

    /**
     * Plain yeast donut
     */
    PLAIN("Classic"),

    /**
     * Glazed yeast donut
     */
    GLAZED("Glazed"),

    /**
     * Jelly yeast donut
     */
    JELLY("Jelly"),

    /**
     * Cinnamon Sugar yeast donut
     */
    CINNAMON("Cinnamon Sugar"),

    /**
     * Boston Cream yeast donut
     */
    CREAM("Boston Cream"),

    /**
     * Powdered Sugar yeast donut
     */
    SUGAR("Powdered Sugar"),

    /**
     * Test Flavor
     */
    TEST("test");

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
    YeastFlavor (String flavor){
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
