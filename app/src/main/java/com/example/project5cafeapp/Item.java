/**
 * Project package
 */
package com.example.project5cafeapp;

/**
 * This class defines the data structure of Item which will be used in the RecyclerView
 */
public class Item {
    /**
     * Item Name String
     */
    private String itemName;
    /**
     * Positin index of image
     */
    private int image;
    /**
     * String price total
     */
    private String unitPrice;

    /**
     * Parameterized constructor.
     * @param itemName name of item
     * @param image image of item
     * @param unitPrice unit Price String of item
     */
    public Item(String itemName, int image, String unitPrice) {
        this.itemName = itemName;
        this.image = image;
        this.unitPrice = unitPrice;
    }

    /**
     * Getter method that returns the item name of an item.
     * @return the item name of an item.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Getter method that returns the image of an item.
     * @return the image of an item.
     */
    public int getImage() {
        return image;
    }

    /**
     * Getter method that returns the unit price.
     * @return the unit price of the item.
     */
    public String getUnitPrice() {
        return unitPrice;
    }
}