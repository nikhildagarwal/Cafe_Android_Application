/**
 * Project package
 */
package com.example.project5cafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.project5cafeapp.donut.CakeDonut;
import com.example.project5cafeapp.donut.DonutHole;
import com.example.project5cafeapp.donut.YeastDonut;

import java.util.ArrayList;

/**
 * This class is activity/controller for the activity_donut.xml
 * Contains methods that sets up the view and adapters
 * Contains methods that sets up the menu items consisting of different donut flavors/types
 */
public class donutActivity extends AppCompatActivity {

    //Declare an instance of ArrayList to hold the items to be display with the RecyclerView
    private ArrayList<Item> items = new ArrayList<>();
    /* All the images associated with the menu items are stored in the res/drawable folder
     *  Each image are accessed with the resourse ID, which is an integer.
     *  We need an array of integers to hold the resource IDs. Make sure the index of a given
     *  ID is consistent with the index of the associated menu item in the ArrayList.
     *  An image resource could also be an URI.
     */
    private int [] itemImages = {R.drawable.yeastdonutclassic, R.drawable.yeastdonutglazed, R.drawable.yeastdonutjelly, R.drawable.yeastdonutcinnamonsugar,R.drawable.yeastdonutbostoncream, R.drawable.yeastdonutpowderedsugar, R.drawable.cakedonutplainvanilla, R.drawable.cakedonutchocolatecake, R.drawable.cakedonutstrawberrycake, R.drawable.donutholeglazed, R.drawable.donutholechocolate, R.drawable.donutholejelly};

    /**
     * Get the references of all instances of Views defined in the layout file, set up the list of
     * items to be display in the RecyclerView.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);
        RecyclerView rcview = findViewById(R.id.rcView_menu);
        setupMenuItems(); //add the list of items to the ArrayList
        ItemsAdapter adapter = new ItemsAdapter(this, items); //create the adapter
        rcview.setAdapter(adapter); //bind the list of items to the RecyclerView
        //use the LinearLayout for the RecyclerView
        rcview.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Helper method to set up the data (the Model of the MVC).
     */
    private void setupMenuItems() {
        /*
         * Item names are defined in a String array under res/string.xml.
         * Your item names might come from other places, such as an external file, or the database
         * from the backend.
         */
        String [] itemNames = getResources().getStringArray(R.array.itemNames);
        /* Add the items to the ArrayList.
           item price set , should change later to actual price depending on donut type and such
         */
        CakeDonut c1 = new CakeDonut();
        YeastDonut y1 = new YeastDonut();
        DonutHole d1 = new DonutHole();
        for (int i = 0; i < itemNames.length; i++) {
            String[] nameArray = itemNames[i].split(" ");
            if(nameArray.length > 1){
                if(nameArray[nameArray.length-1].equals("Hole")){
                    items.add(new Item(itemNames[i], itemImages[i], "$"+Double.toString(d1.itemPrice())));
                }else if(nameArray[nameArray.length-1].equals("Cake")){
                    items.add(new Item(itemNames[i], itemImages[i], "$"+Double.toString(c1.itemPrice())));
                }else{
                    items.add(new Item(itemNames[i], itemImages[i], "$"+Double.toString(y1.itemPrice())));
                }
            }else{
                items.add(new Item(itemNames[i], itemImages[i], "$"+Double.toString(y1.itemPrice())));
            }

        }
    }
}
