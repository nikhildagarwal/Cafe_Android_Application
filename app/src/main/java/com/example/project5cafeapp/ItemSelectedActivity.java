/**
 * Project package
 */
package com.example.project5cafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import com.example.project5cafeapp.data.BasketItem;
import com.example.project5cafeapp.donut.CakeDonut;
import com.example.project5cafeapp.donut.DonutHole;
import com.example.project5cafeapp.donut.YeastDonut;
import com.example.project5cafeapp.data.MenuItem;
import com.example.project5cafeapp.donut.flavors.CakeFlavor;
import com.example.project5cafeapp.donut.flavors.HoleFlavor;
import com.example.project5cafeapp.donut.flavors.YeastFlavor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * When an item displayed in a row on the RecyclerView is clicked, the user will be redirected to a new activity
 * In this activity, the user will be able to select the amount of donuts from the donut flavor picked and add the donut order to the basket
 */
public class ItemSelectedActivity extends AppCompatActivity {
    private Button btn_itemName;
    private ImageView donutImageView;
    private Spinner donutAmountSpinner;
    private TextView donutFlavor;
    private TextView donutFlavorSelected;
    private TextView price;
    private String [] donutAmounts = {"1", "2", "3", "4", "5","6"};
    private ArrayAdapter<String> adapter;

    /**
     * Sets up the view and adapters
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selected);

        donutAmountSpinner = findViewById(R.id.donutAmount);
        adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, donutAmounts);
        donutAmountSpinner.setAdapter(adapter);

        btn_itemName = findViewById(R.id.btn1);
        //Intent intent = getIntent();
        //btn_itemName.setText(intent.getStringExtra("ITEM"));

        donutFlavor = findViewById(R.id.donutFlavorSelected);
        Intent intent = getIntent();
        donutFlavor.setText(intent.getStringExtra("ITEM"));
        donutAmountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * When selecting an item, determines the donut flavor and type and assigns prices accordingly
             * @param parent The AdapterView where the selection happened
             * @param view The view within the AdapterView that was clicked
             * @param position The position of the view in the adapter
             * @param id The row id of the item that is selected
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                price = findViewById(R.id.price);
                CakeDonut c1 = new CakeDonut();
                YeastDonut y1 = new YeastDonut();
                DonutHole d1 = new DonutHole();
                donutFlavorSelected = findViewById(R.id.donutFlavorSelected);
                String header = (String) donutFlavorSelected.getText();
                String[] subHeader = header.split(" ");
                int amount = Integer.parseInt(donutAmountSpinner.getSelectedItem().toString());
                if(subHeader.length > 1){
                    if(subHeader[subHeader.length - 1].equals("Cake")){
                        price.setText("Price: $"+formatDouble(c1.itemPrice()*amount));
                    }else if(subHeader[subHeader.length - 1].equals("Hole")){
                        price.setText("Price: $"+formatDouble(d1.itemPrice()*amount));
                    }else{
                        price.setText("Price: $"+formatDouble(y1.itemPrice()*amount));
                    }
                }else{
                    price.setText("Price: $"+formatDouble(y1.itemPrice()*amount));
                }
            }

            /**
             * if nothing is selected, the price is set at $0.00
             * @param parent The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                price.setText("$0.00");
            }
        });
    }

    /**
     * When clicking on the "Add to Basket" button, the user will be met with an alert dialog
     * The alert dialog will notify the users whether they want to add the donut order to the basket
     * @param view
     */
    public void addDonutToBasket(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        alert.setTitle("Add to Order");
        alert.setMessage(donutAmountSpinner.getSelectedItem().toString() +" x "+ donutFlavorSelected.getText()+"\n"+price.getText().toString().split(" ")[1]);
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            /**
             * When adding the donut, an AlertDialog will be shown to the user which will give the user a choice of adding the order to the basket
             * When clicking yes, the order will be added
             * Will tell the user with a fading message the donut amount, type, and flavor added to the basket
             * @param dialog the dialog that received the click
             * @param which the button that was clicked (ex.
             *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
             *              of the item clicked
             */
            public void onClick(DialogInterface dialog, int which) {
                addToBasket(view);
            }
            //handle the "NO" click
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            /**
             * When adding the donut, an AlertDialog will be shown to the user which will give the user a choice of adding the order to the basket
             * When clicking no, the order will not be added and will display "Item Not Added."
             * @param dialog the dialog that received the click
             * @param which the button that was clicked (ex.
             *              {@link DialogInterface#BUTTON_POSITIVE}) or the position
             *              of the item clicked
             */
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(view.getContext(),
                        "Item Not Added.", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * adds the donut order to the basket
     * @param view
     */
    private void addToBasket(View view){
        MenuItem menuItem = getMenuItem(donutFlavorSelected.getText().toString());
        String amount = donutAmountSpinner.getSelectedItem().toString();
        BasketItem basketItem = new BasketItem(menuItem,Integer.parseInt(amount));
        /**
         * Add basketItem to basket
         */
        SharedPreferences preferences = getSharedPreferences("data_shared",MODE_PRIVATE);
        String json = preferences.getString("basket",null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            ArrayList<String> basket = gson.fromJson(json, type);
            Log.d("basket",basket.toString());
            String basketCode = menuItem.getMenuItemType()+" "+amount;
            basket.add(basketCode);
            Log.d("basket",basket.toString());
            /**
             * Section end
             */
            SharedPreferences pref = getSharedPreferences("data_shared",MODE_PRIVATE);
            Gson g = new Gson();
            String jsonToPut = g.toJson(basket);
            SharedPreferences.Editor newEdit = pref.edit();
            newEdit.putString("basket",jsonToPut);
            newEdit.apply();
            Toast.makeText(view.getContext(),basketItem.toString()+" Added.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * formats the price
     * @param ourDouble
     * @return
     */
    private String formatDouble(double ourDouble){
        return format(Math.round(ourDouble*100.00) / 100.00);
    }

    /**
     * Fetches the MenuItem which is the donut flavor type
     * @param name
     * @return
     */
    private MenuItem getMenuItem(String name){
        String[] nameArray = name.split(" ");
        if(nameArray.length>1){
            if(nameArray[nameArray.length-1].equals("Cake")){
                if(nameArray[0].equals("Vanilla")){
                    return new CakeDonut(CakeFlavor.PLAIN);
                }else if(nameArray[0].equals("Chocolate")){
                    return new CakeDonut(CakeFlavor.CHOCOLATE);
                }else{
                    return new CakeDonut(CakeFlavor.STRAWBERRY);
                }
            }else if(nameArray[nameArray.length-1].equals("Hole")){
                if(nameArray[0].equals("Glazed")){
                    return new DonutHole(HoleFlavor.GLAZED);
                }else if(nameArray[0].equals("Chocolate")){
                    return new DonutHole(HoleFlavor.CHOCOLATE);
                }else{
                    return new DonutHole(HoleFlavor.JELLY);
                }
            }else{
                switch(nameArray[0]){
                    case "Plain":
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
            }
        }else{
            switch(nameArray[0]){
                case "Plain":
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
        }
        return null;
    }

    /**
     * formatting for the price
     * @param num
     * @return
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