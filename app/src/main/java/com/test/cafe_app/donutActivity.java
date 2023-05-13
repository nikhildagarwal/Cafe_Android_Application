/**
 * Project package
 */
package com.test.cafe_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.test.cafe_app.donut.CakeDonut;
import com.test.cafe_app.donut.DonutHole;
import com.test.cafe_app.donut.YeastDonut;

import java.util.ArrayList;

/**
 * This class is activity/controller for the activity_donut.xml
 * Contains methods that sets up the view and adapters
 * Contains methods that sets up the menu items consisting of different donut flavors/types
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class donutActivity extends AppCompatActivity {

    /**
     * Declare an instance of ArrayList to hold the items to be display with the RecyclerView
     */
    private ArrayList<Item> items = new ArrayList<>();
    /** All the images associated with the menu items are stored in the res/drawable folder
     *  Each image are accessed with the resourse ID, which is an integer.
     *  We need an array of integers to hold the resource IDs. Make sure the index of a given
     *  ID is consistent with the index of the associated menu item in the ArrayList.
     *  An image resource could also be an URI.
     */
    private int [] itemImages = {R.drawable.yeastdonutclassic, R.drawable.yeastdonutglazed, R.drawable.yeastdonutjelly, R.drawable.yeastdonutcinnamonsugar,R.drawable.yeastdonutbostoncream, R.drawable.yeastdonutpowderedsugar, R.drawable.cakedonutplainvanilla, R.drawable.cakedonutchocolatecake, R.drawable.cakedonutstrawberrycake, R.drawable.donutholeglazed, R.drawable.donutholechocolate, R.drawable.donutholejelly};

    /**
     * Get the references of all instances of Views defined in the layout file, set up the list of
     * items to be display in the RecyclerView.
     * @param savedInstanceState If the activity is being re-initialized after
     *      previously being shut down then this Bundle contains the data it most
     *      recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
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
        String [] itemNames = getResources().getStringArray(R.array.itemNames);
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
