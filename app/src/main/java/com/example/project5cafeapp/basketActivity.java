package com.example.project5cafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project5cafeapp.cofee.AddOn;
import com.example.project5cafeapp.cofee.Coffee;
import com.example.project5cafeapp.cofee.Size;
import com.example.project5cafeapp.data.BasketItem;
import com.example.project5cafeapp.data.MenuItem;
import com.example.project5cafeapp.donut.CakeDonut;
import com.example.project5cafeapp.donut.DonutHole;
import com.example.project5cafeapp.donut.YeastDonut;
import com.example.project5cafeapp.donut.flavors.CakeFlavor;
import com.example.project5cafeapp.donut.flavors.HoleFlavor;
import com.example.project5cafeapp.donut.flavors.YeastFlavor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class basketActivity extends AppCompatActivity {

    private ListView basketView;
    private TextView total;
    private TextView tax;
    private TextView basketTotal;
    private Button removeButton;
    private int previousPosition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        total = findViewById(R.id.editTextText2);
        tax = findViewById(R.id.editTextText3);
        basketTotal = findViewById(R.id.editTextText4);

        SharedPreferences preferences = getSharedPreferences("data_shared",MODE_PRIVATE);
        String json = preferences.getString("basket",null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> basket = gson.fromJson(json, type);
        ArrayList<BasketItem> convertedBasket = new ArrayList<>();
        for(int i = 0;i<basket.size();i++){
            BasketItem basketItem = extractMenuItem(basket.get(i));
            convertedBasket.add(basketItem);
        }
        ArrayAdapter<BasketItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, convertedBasket);
        basketView = findViewById(R.id.basketList);
        basketView.setAdapter(adapter);
        displayTotal(convertedBasket);
        displayTax(total.getText().toString());
        basketView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(Color.parseColor("#DDDDDD"));
                basketView.setItemChecked(position,true);
                if(previousPosition != -1 && previousPosition != position) {
                    View previousView = parent.getChildAt(previousPosition - basketView.getFirstVisiblePosition());
                    if(previousView != null) {
                        previousView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }

                previousPosition = position;
            }
        });
        removeItems(adapter,basket);
    }

    public void removeItems(ArrayAdapter<BasketItem> adapter,ArrayList<String> basket){
        removeButton = findViewById(R.id.button4);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(previousPosition==-1){
                    Toast.makeText(basketView.getContext(), "Nothing Selected to Remove!", Toast.LENGTH_SHORT).show();
                }else{

                    BasketItem selected = adapter.getItem(previousPosition);
                    adapter.remove(selected);
                    SharedPreferences preferences = getSharedPreferences("data_shared",MODE_PRIVATE);
                    SharedPreferences.Editor ed = preferences.edit();
                    Gson gson = new Gson();
                    basket.remove(previousPosition);
                    String json = gson.toJson(basket);
                    ed.putString("basket",json);
                    ed.apply();
                    basketView.setAdapter(adapter);
                    displayTotal(adapter);
                    displayTax(total.getText().toString());
                }
            }
        });
    }
    //Toast.makeText(basketView.getContext(), "Button clicked!", Toast.LENGTH_SHORT).show();

    private void displayTotal(ArrayList<BasketItem> convertedBasket){
        double totalSum = 0.0;
        for(int i = 0;i<convertedBasket.size();i++){
            totalSum += (convertedBasket.get(i).getMenuItem().itemPrice() * convertedBasket.get(i).getQuantity());
        }
        total.setText("Sub-Total: $"+format(Math.round(totalSum * 100.00) / 100.00));
    }

    private void displayTotal(ArrayAdapter<BasketItem> adapter){
        double totalSum = 0.0;
        for(int i =0;i<adapter.getCount();i++){
            totalSum += (adapter.getItem(i).getMenuItem().itemPrice() * adapter.getItem(i).getQuantity());
        }
        total.setText("Sub-Total: $"+format(Math.round(totalSum * 100.00) / 100.00));
    }

    private void displayTax(String totalSum){
        double num = Double.parseDouble(totalSum.split("\\$")[1]);
        double numSave = num;
        num *= 0.06625;
        tax.setText("Sales Tax: $"+(Math.round(num * 100.00) / 100.00));
        double numAfter = Math.round(num * 100.00) / 100.00;
        basketTotal.setText("Total: $"+format(Math.round((numSave+numAfter)*100.00)/100.00));
    }

    private BasketItem extractMenuItem(String code){
        String[] codes = code.split(" ");
        Log.d("array",Arrays.toString(codes));
        if(codes[0].equals("C")){
            switch(codes[1]){
                case "Strawberry":
                    return new BasketItem(new CakeDonut(CakeFlavor.STRAWBERRY),Integer.parseInt(codes[2]));
                case "Plain":
                    return new BasketItem(new CakeDonut(CakeFlavor.PLAIN),Integer.parseInt(codes[2]));
                case "Chocolate":
                    return new BasketItem(new CakeDonut(CakeFlavor.CHOCOLATE),Integer.parseInt(codes[2]));
            }
        }else if(codes[0].equals("D")){
            switch(codes[1]){
                case "Jelly":
                    return new BasketItem(new DonutHole(HoleFlavor.JELLY),Integer.parseInt(codes[2]));
                case "Glazed":
                    return new BasketItem(new DonutHole(HoleFlavor.GLAZED),Integer.parseInt(codes[2]));
                case "Chocolate":
                    return new BasketItem(new DonutHole(HoleFlavor.CHOCOLATE),Integer.parseInt(codes[2]));
            }
        }else if(codes[0].equals("Y")){
            switch(codes[1]){
                case "Classic":
                    return new BasketItem(new YeastDonut(YeastFlavor.PLAIN),Integer.parseInt(codes[2]));
                case "Glazed":
                    return new BasketItem(new YeastDonut(YeastFlavor.GLAZED),Integer.parseInt(codes[2]));
                case "Jelly":
                    return new BasketItem(new YeastDonut(YeastFlavor.JELLY),Integer.parseInt(codes[2]));
                case "Cinnamon":
                    return new BasketItem(new YeastDonut(YeastFlavor.CINNAMON),Integer.parseInt(codes[2]));
                case "Boston":
                    return new BasketItem(new YeastDonut(YeastFlavor.CREAM),Integer.parseInt(codes[2]));
                case "Powdered":
                    return new BasketItem(new YeastDonut(YeastFlavor.SUGAR),Integer.parseInt(codes[2]));
            }
        }else if(codes[0].equals("Z")){
            HashSet<AddOn> addOns = new HashSet<>();
            if(codes[1].equals("true")){
                addOns.add(AddOn.SWEETCREAM);
            }
            if(codes[2].equals("true")){
                addOns.add(AddOn.MOCHA);
            }
            if(codes[3].equals("true")){
                addOns.add(AddOn.FRENCHVANILLA);
            }
            if(codes[4].equals("true")){
                addOns.add(AddOn.CARAMEL);
            }
            if(codes[5].equals("true")){
                addOns.add(AddOn.IRISHCREAM);
            }
            switch(codes[6]){
                case "Short":
                    return new BasketItem(new Coffee(Size.SHORT,addOns),Integer.parseInt(codes[7]));
                case "Tall":
                    return new BasketItem(new Coffee(Size.TALL,addOns),Integer.parseInt(codes[7]));
                case "Grande":
                    return new BasketItem(new Coffee(Size.GRANDE,addOns),Integer.parseInt(codes[7]));
                case "Venti":
                    return new BasketItem(new Coffee(Size.VENTI,addOns),Integer.parseInt(codes[7]));
            }
        }
        return null;
    }

    private String format(double num){
        String numString = Double.toString(num);
        String back = numString.split("\\.")[1];
        String front = numString.split("\\.")[0];
        if(back.length()<2){
            while(back.length()<2){
                back += "0";
            }
        }
        return front +"."+back;
    }
}