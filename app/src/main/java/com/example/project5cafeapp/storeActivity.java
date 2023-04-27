package com.example.project5cafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project5cafeapp.cofee.AddOn;
import com.example.project5cafeapp.cofee.Coffee;
import com.example.project5cafeapp.cofee.Size;
import com.example.project5cafeapp.data.BasketItem;
import com.example.project5cafeapp.data.MenuItem;
import com.example.project5cafeapp.data.Order;
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
import java.util.HashSet;

public class storeActivity extends AppCompatActivity {

    private ListView storeOrdersView;
    private int previousPosition = -1;
    private TextView total;
    private Button removeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        storeOrdersView = findViewById(R.id.storeOrders);
        total = findViewById(R.id.totalOfOrder);
        total.setText("Total: $0.00");
        SharedPreferences preferences = getSharedPreferences("data_shared",MODE_PRIVATE);
        String json = preferences.getString("orders",null);
        ArrayAdapter<Order> adapter = null;
        ArrayList<String> orders = null;
        if(json!=null){
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            orders = gson.fromJson(json, type);
            Log.d("s",orders.toString());
            ArrayList<Order> reformat = new ArrayList<>();
            for(int j = 0;j<orders.size();j++){
                String[] parts = orders.get(j).split("%");
                int orderNumber = Integer.parseInt(parts[0]);
                ArrayList<BasketItem> basket = new ArrayList<>();
                for(int i = 1;i<parts.length-1;i++){
                    String currString = parts[i];
                    Log.d("sup",currString);
                    String[] subParts = currString.split(" ");
                    MenuItem menuItem = null;
                    if(subParts[3].equals("-")){
                        menuItem = getCoffee(subParts,orders.get(j));
                    }else{
                        menuItem = getDonut(subParts);
                    }
                    int quantity = Integer.parseInt(subParts[0]);
                    BasketItem basketItem = new BasketItem(menuItem,quantity);
                    basket.add(basketItem);
                }
                reformat.add(new Order(orderNumber,basket));
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reformat);
            storeOrdersView.setAdapter(adapter);
        }
        ArrayAdapter<Order> finalAdapter = adapter;
        storeOrdersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(Color.parseColor("#DDDDDD"));
                storeOrdersView.setItemChecked(position,true);
                if(previousPosition != -1 && previousPosition != position) {
                    View previousView = parent.getChildAt(previousPosition - storeOrdersView.getFirstVisiblePosition());
                    if(previousView != null) {
                        previousView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }
                previousPosition = position;
                String amount = finalAdapter.getItem(previousPosition).getOrderPrice();
                total.setText("Total: "+amount);
            }
        });
        removeButtonClicked(adapter,orders);
    }

    public void removeButtonClicked(ArrayAdapter<Order> adapter,ArrayList<String> orders){
        removeButton = findViewById(R.id.orderRemoveButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(previousPosition==-1){
                    Toast.makeText(storeOrdersView.getContext(), "Nothing Selected to Remove!", Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        Order selected = adapter.getItem(previousPosition);
                        adapter.remove(selected);
                        SharedPreferences preferences = getSharedPreferences("data_shared",MODE_PRIVATE);
                        SharedPreferences.Editor ed = preferences.edit();
                        Gson gson = new Gson();
                        orders.remove(previousPosition);
                        String json = gson.toJson(orders);
                        ed.putString("orders",json);
                        ed.apply();
                        storeOrdersView.setAdapter(adapter);
                        total.setText("Total: $0.00");
                        Toast.makeText(storeOrdersView.getContext(), "Order Number "+selected.getOrderNumber()+" removed.", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(storeOrdersView.getContext(), "Store is Empty!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private MenuItem getCoffee(String[] subParts,String item){
        HashSet<AddOn> addOns = getAddOns(item);
        switch(subParts[5]){
            case "Short,":
                return new Coffee(Size.SHORT,addOns);
            case "Tall,":
                return new Coffee(Size.TALL,addOns);
            case "Grande,":
                return new Coffee(Size.GRANDE,addOns);
            case "Venti,":
                return new Coffee(Size.VENTI,addOns);
        }
        return null;
    }

    private HashSet<AddOn> getAddOns(String item){
        String right = item.split("\\[")[1];
        String contents = right.split("\\]")[0];
        String[] addOnStrings = contents.split(", ");
        HashSet<AddOn> ourSet = new HashSet<>();
        for(int i = 0;i<addOnStrings.length;i++){
            if(addOnStrings[i].equals("Sweet Cream")){
                ourSet.add(AddOn.SWEETCREAM);
            }else if(addOnStrings[i].equals("French Vanilla")){
                ourSet.add(AddOn.FRENCHVANILLA);
            }else if(addOnStrings[i].equals("Irish Cream")){
                ourSet.add(AddOn.IRISHCREAM);
            }else if(addOnStrings[i].equals("Caramel")){
                ourSet.add(AddOn.CARAMEL);
            }else{
                ourSet.add(AddOn.MOCHA);
            }
        }
        return ourSet;
    }

    private MenuItem getDonut(String[] subParts){
        switch(subParts[2]){
            case "Yeast":
                return getYeastDonut(subParts[5]);
            case "Cake":
                return getCakeDonut(subParts[5]);
            case "Donut":
                return getHoleDonut(subParts[5]);
        }
        return null;
    }

    private DonutHole getHoleDonut(String code){
        switch(code){
            case "Glazed":
                return new DonutHole(HoleFlavor.GLAZED);
            case "Chocolate":
                return new DonutHole(HoleFlavor.CHOCOLATE);
            case "Jelly":
                return new DonutHole(HoleFlavor.JELLY);
        }
        return null;
    }

    private CakeDonut getCakeDonut(String code){
        switch (code){
            case "Plain":
                return new CakeDonut(CakeFlavor.PLAIN);
            case "Chocolate":
                return new CakeDonut(CakeFlavor.CHOCOLATE);
            case "Strawberry":
                return new CakeDonut(CakeFlavor.STRAWBERRY);
        }
        return null;
    }

    private YeastDonut getYeastDonut(String code){
        switch(code){
            case "Classic":
                return new YeastDonut(YeastFlavor.PLAIN);
            case "Glazed":
                return new YeastDonut(YeastFlavor.GLAZED);
            case "Jelly":
                return new YeastDonut(YeastFlavor.JELLY);
            case "Cinnamon":
                return new YeastDonut(YeastFlavor.CINNAMON);
            case "Boston":
                return new YeastDonut(YeastFlavor.CREAM);
            case "Powdered":
                return new YeastDonut(YeastFlavor.SUGAR);
        }
        return null;
    }
}