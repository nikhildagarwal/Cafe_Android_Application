package com.example.project5cafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

/**
 * This class is the activity for activity_basket.xml
 * Contains methods to set up the view and adapters
 * Contains methods to remove basket items and place orders of current basket items.
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class basketActivity extends AppCompatActivity {
    /**
     * Basket View
     */
    private ListView basketView;
    /**
     * Total Text Field
     */
    private TextView total;
    /**
     * Tax field
     */
    private TextView tax;
    /**
     * Tax + total field
     */
    private TextView basketTotal;
    /**
     * Remove button
     */
    private Button removeButton;
    /**
     * add button
     */
    private Button addButton;
    /**
     * Previous index of selected basket item
     */
    private int previousPosition = -1;

    /**
     * Sets up the view and adapters for the spinners
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        total = findViewById(R.id.editTextText2);
        tax = findViewById(R.id.editTextText3);
        basketTotal = findViewById(R.id.editTextText4);
        addButton = findViewById(R.id.button2);

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
            /**
             * OnItemClick even listener (connects to frontend).
             * @param parent The AdapterView where the click happened.
             * @param view The view within the AdapterView that was clicked (this
             *            will be a view provided by the adapter)
             * @param position The position of the view in the adapter.
             * @param id The row id of the item that was clicked.
             */
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
        addBasketToOrder(adapter,basket);
    }

    /**
     * removes basket items from our basket while also deleting it from "local storage"
     * @param adapter adapter Array for View
     * @param basket ArrayList of strings that is shared amongst all views
     */
    public void removeItems(ArrayAdapter<BasketItem> adapter,ArrayList<String> basket){
        removeButton = findViewById(R.id.button4);
        removeButton.setOnClickListener(new View.OnClickListener() {
            /**
             * On click listener for the remove button
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                if(previousPosition==-1){
                    Toast.makeText(basketView.getContext(), "Nothing Selected to Remove!", Toast.LENGTH_SHORT).show();
                }else{
                    try{
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
                        Toast.makeText(basketView.getContext(), selected.toString()+" removed.", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(basketView.getContext(), "Basket is Empty!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * Wraps all the basket orders into one order object and inputs it into our local storage orders arrayList.
     * Clears the contents of the current basket.
     * Resets the page to clear all text view to "$0.00"
     * @param adapter adapter Array for View
     * @param basket ArrayList of strings that is shared amongst all views
     */
    public void addBasketToOrder(ArrayAdapter<BasketItem> adapter, ArrayList<String> basket){
        addButton.setOnClickListener(new View.OnClickListener() {
            /**
             * On click listener for the add to order button
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(view.getContext());
                alert.setTitle("Place Order");
                alert.setMessage("Would you like to place this order?");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    /**
                     * Alert dialog listener for adding to orders
                     * @param dialog the dialog that received the click
                     * @param which the button that was clicked (ex.
                     *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
                     *              of the item clicked
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            String orderString = "";
                            for(int i = 0;i<adapter.getCount();i++){
                                orderString += "%"+adapter.getItem(i).toString();
                            }
                            int n = (int)(Math.random() * 900000000) + 100000000;
                            orderString = n + orderString;
                            orderString +=("%"+basketTotal.getText());
                            SharedPreferences preferences = getSharedPreferences("data_shared",MODE_PRIVATE);
                            String json = preferences.getString("orders",null);
                            if(json!=null){
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<String>>(){}.getType();
                                ArrayList<String> orders = gson.fromJson(json, type);
                                if(adapter.isEmpty()){
                                    Toast.makeText(view.getContext(),
                                            "Basket is Empty!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                orders.add(orderString);
                                /**
                                 * Section end
                                 */
                                SharedPreferences pref = getSharedPreferences("data_shared",MODE_PRIVATE);
                                Gson g = new Gson();
                                String jsonToPut = g.toJson(orders);
                                SharedPreferences.Editor newEdit = pref.edit();
                                newEdit.putString("orders",jsonToPut);

                                Toast.makeText(view.getContext(),"Order Number "+n+" Has Been Placed.", Toast.LENGTH_SHORT).show();
                                ArrayList<String> newBasket = new ArrayList<>();
                                Gson GSON = new Gson();
                                String JSON = GSON.toJson(newBasket);
                                newEdit.putString("basket",JSON);
                                newEdit.apply();
                                total.setText("Sub-Total: $0.00");
                                tax.setText("Sales Tax: $0.00");
                                basketTotal.setText("Total: $0.00");
                                ArrayAdapter<BasketItem> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, new ArrayList<BasketItem>());
                                basketView.setAdapter(adapter);
                            }else{
                                Toast.makeText(view.getContext(),
                                        "Basket is Empty!", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(view.getContext(),
                                    "Basket is Empty!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    /**
                     * Alert dialog listener when user selects "no"
                     * @param dialog the dialog that received the click
                     * @param which the button that was clicked (ex.
                     *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
                     *              of the item clicked
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(view.getContext(),
                                "Order Not Placed.", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
    }

    /**
     * Sets the display text to a certain number using the contents of an arrayList of basketItems
     * @param convertedBasket list of basketItems we want to sum
     */
    private void displayTotal(ArrayList<BasketItem> convertedBasket){
        double totalSum = 0.0;
        for(int i = 0;i<convertedBasket.size();i++){
            totalSum += (convertedBasket.get(i).getMenuItem().itemPrice() * convertedBasket.get(i).getQuantity());
        }
        total.setText("Sub-Total: $"+format(Math.round(totalSum * 100.00) / 100.00));
    }

    /**
     * Sets the display text for total.
     * Overload method, uses the adapter array for a list instead.
     * @param adapter
     */
    private void displayTotal(ArrayAdapter<BasketItem> adapter){
        double totalSum = 0.0;
        for(int i =0;i<adapter.getCount();i++){
            totalSum += (adapter.getItem(i).getMenuItem().itemPrice() * adapter.getItem(i).getQuantity());
        }
        total.setText("Sub-Total: $"+format(Math.round(totalSum * 100.00) / 100.00));
    }

    /**
     * Displays the text of the tax using the total value of all basket items added up.
     * @param totalSum
     */
    private void displayTax(String totalSum){
        double num = Double.parseDouble(totalSum.split("\\$")[1]);
        double numSave = num;
        num *= 0.06625;
        tax.setText("Sales Tax: $"+(Math.round(num * 100.00) / 100.00));
        double numAfter = Math.round(num * 100.00) / 100.00;
        basketTotal.setText("Total: $"+format(Math.round((numSave+numAfter)*100.00)/100.00));
    }

    /**
     * Extracts a menuItem from a code string.
     * Parses the code string and returns the corresponding menuItem.
     * @param code String code
     * @return MenuItem menuItem
     */
    private BasketItem extractMenuItem(String code){
        String[] codes = code.split(" ");
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

    /**
     * Formats a double into a String rounded to 2 decimal places.
     * @param num double that we want to convert and truncate.
     * @return String formatted as 0.00
     */
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