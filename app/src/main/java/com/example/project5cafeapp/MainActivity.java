package com.example.project5cafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton mainDonutButton;
    private ImageButton mainCoffeeButton;
    private ImageButton orderBasketButton;
    private ImageButton storeOrdersButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    /*
    //for some reason doesn't work on Samsung phone
    @Override
    protected void onStart()
    {
        super.onStart();
        System.out.println("run onStart()");
    }
    */

}