/**
 * Project package
 */
package com.test.cafe_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.cafe_app.data.BasketItem;
import com.test.cafe_app.donut.CakeDonut;
import com.test.cafe_app.donut.flavors.CakeFlavor;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * This class is the main activity/main controller of the mobile application
 * This is the first activity page that the user will see upon entering the application
 * Defines the image buttons used to redirect the user to other views
 * @author Hyeon Oh, Nikhil Agarwal
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Main Buttons for donut
     */
    private ImageButton mainDonutButton;
    /**
     * Main Buttons for coffee
     */
    private ImageButton mainCoffeeButton;
    /**
     * Main Buttons for basket
     */
    private ImageButton orderBasketButton;
    /**
     * Main Buttons for store
     */
    private ImageButton storeOrdersButton;

    /**
     * Sets up the view for the "front page" of what the user sees when opening the application
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getSharedPreferences("data_shared",MODE_PRIVATE);
        Boolean visited = pref.getBoolean("visited",false);
        if(!visited) {
            ArrayList<String> initBasket = new ArrayList<>();
            Gson gson = new Gson();
            String json = gson.toJson(initBasket);
            ArrayList<String> initOrders = new ArrayList<>();
            Gson gsonNext = new Gson();
            String jsonNext = gsonNext.toJson(initOrders);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("orders",jsonNext);
            editor.putString("basket", json);
            editor.putBoolean("visited", true);
            editor.apply();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainDonutButton = findViewById(R.id.mainDonut);
        mainDonutButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When clicking on the donuts image button, the user will be redirected to the donut ordering page
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, donutActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
        mainCoffeeButton = findViewById(R.id.mainCoffee);
        mainCoffeeButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When clicking on the coffee image button, the user will be redirected to the coffee ordering page
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, coffeeActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
        orderBasketButton = findViewById(R.id.orderBasket);
        orderBasketButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When clicking on the basket image button, the user will be redirected to the basket page
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, basketActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
        storeOrdersButton = findViewById(R.id.storeOrders);
        storeOrdersButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When clicking on the store image button, the user will be redirected to the store page
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, storeActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }



}