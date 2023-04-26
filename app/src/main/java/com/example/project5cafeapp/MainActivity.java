package com.example.project5cafeapp;

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

import com.example.project5cafeapp.data.BasketItem;
import com.example.project5cafeapp.donut.CakeDonut;
import com.example.project5cafeapp.donut.flavors.CakeFlavor;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton mainDonutButton;
    private ImageButton mainCoffeeButton;
    private ImageButton orderBasketButton;
    private ImageButton storeOrdersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getSharedPreferences("data_shared",MODE_PRIVATE);
        Boolean visited = pref.getBoolean("visited",false);
        Log.d("dfs",visited.toString());
        if(!visited){
            ArrayList<String> initBasket = new ArrayList<>();
            Gson gson = new Gson();
            String json = gson.toJson(initBasket);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("basket",json);
            editor.putBoolean("visited",true);
            editor.apply();
            Log.d("sup","done");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainDonutButton = findViewById(R.id.mainDonut);
        mainDonutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, donutActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
        mainCoffeeButton = findViewById(R.id.mainCoffee);
        mainCoffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, coffeeActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
        orderBasketButton = findViewById(R.id.orderBasket);
        orderBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, basketActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
        storeOrdersButton = findViewById(R.id.storeOrders);
        storeOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MainActivity.this, storeActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }



}