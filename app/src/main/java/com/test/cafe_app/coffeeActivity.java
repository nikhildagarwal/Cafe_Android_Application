/**
 * Project package
 */
package com.test.cafe_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.test.cafe_app.cofee.AddOn;
import com.test.cafe_app.cofee.Coffee;
import com.test.cafe_app.cofee.Size;
import com.test.cafe_app.data.BasketItem;
import com.test.cafe_app.data.MenuItem;
import com.test.cafe_app.donut.CakeDonut;
import com.test.cafe_app.donut.DonutHole;
import com.test.cafe_app.donut.YeastDonut;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class is the activity for activity_coffee.xml
 * Contains methods to set up the view and adapters
 * Contains methods to calculate coffee prices depending on the user's coffee size, amount, and add-ons
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class coffeeActivity extends AppCompatActivity{

    /**
     * spinner drop down
     */
    private Spinner coffeeSizeSpinner;
    /**
     * Spinner drop down
     */
    private Spinner coffeeAmountSpinner;

    /**
     * Size arrays
     */
    private String [] coffeeSizes = {"Short", "Tall", "Grande", "Venti"};
    /**
     * Amount arrays
     */
    private String [] coffeeAmount = {"1", "2", "3", "4", "5"};
    /**
     * Adapter for views
     */
    private ArrayAdapter<String> adapter1;
    /**
     * Adapter for views
     */
    private ArrayAdapter<String> adapter2;

    /**
     * Text Field total
      */
    private TextView total;
    /**
     * Check Box item
     */
    private CheckBox sweetCreamBox;
    /**
     * Check Box item
     */
    private CheckBox mochaBox;
    /**
     * Check Box item
     */
    private CheckBox vanillaBox;
    /**
     * Check Box item
     */
    private CheckBox caramelBox;
    /**
     * Check Box item
     */
    private CheckBox creamBox;
    /**
     * Add to order button
     */
    private Button addToOrder;

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
        setContentView(R.layout.activity_coffee);

        coffeeSizeSpinner = findViewById(R.id.coffeeSize);
        adapter1 = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, coffeeSizes);
        coffeeSizeSpinner.setAdapter(adapter1);

        coffeeAmountSpinner = findViewById(R.id.coffeeAmount);
        adapter2 = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, coffeeAmount);
        coffeeAmountSpinner.setAdapter(adapter2);

        total = findViewById(R.id.editTextText);
        total.setText("Total: $1.89");
        sweetCreamBox = findViewById(R.id.checkBox);
        mochaBox = findViewById(R.id.checkBox2);
        vanillaBox = findViewById(R.id.checkBox3);
        caramelBox = findViewById(R.id.checkBox4);
        creamBox = findViewById(R.id.checkBox5);

        addToOrder = findViewById(R.id.button);

        coffeeAmountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Selecting an amount from the coffeeAmountSpinner will update the price
             * @param parent The AdapterView where the selection happened
             * @param view The view within the AdapterView that was clicked
             * @param position The position of the view in the adapter
             * @param id The row id of the item that is selected
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePrice(view);
            }

            /**
             * Updates the total price text field
             * @param parent The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                total.setText("$0.00");
            }
        });

        coffeeSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Updates the price in the view based on the coffee size chosen
             * @param parent The AdapterView where the selection happened
             * @param view The view within the AdapterView that was clicked
             * @param position The position of the view in the adapter
             * @param id The row id of the item that is selected
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePrice(view);
            }

            /**
             * Updates the total price text field
             * @param parent The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                total.setText("$0.00");
            }
        });
    }

    /**
     * Contains logic that will update the price based on the user's choice of coffee add-ons, amount, and size
     * @param view default view
     */
    public void updatePrice(View view){
        boolean sc = sweetCreamBox.isChecked();
        boolean m = mochaBox.isChecked();
        boolean fv = vanillaBox.isChecked();
        boolean c = caramelBox.isChecked();
        boolean ic = creamBox.isChecked();
        HashSet<AddOn> addOns = new HashSet<>();
        if(sc){
            addOns.add(AddOn.SWEETCREAM);
        }
        if(m){
            addOns.add(AddOn.MOCHA);
        }
        if(fv){
            addOns.add(AddOn.FRENCHVANILLA);
        }
        if(c){
            addOns.add(AddOn.CARAMEL);
        }
        if(ic){
            addOns.add(AddOn.IRISHCREAM);
        }
        String coffeeType = coffeeSizeSpinner.getSelectedItem().toString();
        int quantity = Integer.parseInt(coffeeAmountSpinner.getSelectedItem().toString());
        MenuItem coffee = getCoffeeFromData(coffeeType,addOns);
        total.setText("Total: $"+ format(coffee.itemPrice() * quantity));
    }

    /**
     * This method contains the logic to add the coffee order to the basket view
     * @param view default view
     */
    public void addCoffeeToBasket(View view){
        String coffeeCode = getCode();
        SharedPreferences preferences = getSharedPreferences("data_shared",MODE_PRIVATE);
        String json = preferences.getString("basket",null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            ArrayList<String> basket = gson.fromJson(json, type);
            basket.add(coffeeCode);
            /**
             * Section end
             */
            SharedPreferences pref = getSharedPreferences("data_shared",MODE_PRIVATE);
            Gson g = new Gson();
            String jsonToPut = g.toJson(basket);
            SharedPreferences.Editor newEdit = pref.edit();
            newEdit.putString("basket",jsonToPut);
            newEdit.apply();
            BasketItem basketItem = getCoffeeBasketItem();
            Toast.makeText(view.getContext(),basketItem.toString()+" Added.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Fetches the coffee basket item which will be used in addCoffeeToBasket()
     * @return Basket item based on form data
     */
    private BasketItem getCoffeeBasketItem(){
        boolean sc = sweetCreamBox.isChecked();
        boolean m = mochaBox.isChecked();
        boolean fv = vanillaBox.isChecked();
        boolean c = caramelBox.isChecked();
        boolean ic = creamBox.isChecked();
        HashSet<AddOn> addOns = new HashSet<>();
        if(sc){
            addOns.add(AddOn.SWEETCREAM);
        }
        if(m){
            addOns.add(AddOn.MOCHA);
        }
        if(fv){
            addOns.add(AddOn.FRENCHVANILLA);
        }
        if(c){
            addOns.add(AddOn.CARAMEL);
        }
        if(ic){
            addOns.add(AddOn.IRISHCREAM);
        }
        String coffeeType = coffeeSizeSpinner.getSelectedItem().toString();
        int quantity = Integer.parseInt(coffeeAmountSpinner.getSelectedItem().toString());
        MenuItem coffee = getCoffeeFromData(coffeeType,addOns);
        return new BasketItem(coffee,quantity);
    }

    /**
     * Contains coffee logic to decrease or increase coffee prices depending on how many add-ons are chosen
     * @return String code to parse later
     */
    private String getCode(){
        String code = "Z ";
        boolean sc = sweetCreamBox.isChecked();
        boolean m = mochaBox.isChecked();
        boolean fv = vanillaBox.isChecked();
        boolean c = caramelBox.isChecked();
        boolean ic = creamBox.isChecked();
        if(sc){
            code += "true ";
        }else{
            code += "false ";
        }
        if(m){
            code += "true ";
        }else{
            code += "false ";
        }
        if(fv){
            code += "true ";
        }else{
            code += "false ";
        }
        if(c){
            code += "true ";
        }else{
            code += "false ";
        }
        if(ic){
            code += "true ";
        }else{
            code += "false ";
        }
        String coffeeType = coffeeSizeSpinner.getSelectedItem().toString();
        code += (coffeeType +" ");
        String amount = coffeeAmountSpinner.getSelectedItem().toString();
        code += amount;
        return code;
    }

    /**
     * Formats the pricing text
     * @param num double that we want to format
     * @return The string that we have truncated.
     */
    private String format(double num){
        num = Math.round(num * 100.00) / 100.00;
        String numString = Double.toString(num);
        String[] parts = numString.split("\\.");
        if(parts[1].length()<2){
            while(parts[1].length()<2){
                parts[1] += "0";
            }
        }
        return parts[0]+"."+parts[1];
    }

    /**
     * Returns the user's chosen coffee size along with add-ons
     * @param coffeeType Size string of coffee
     * @param addOns HashSet of addons.
     * @return Creates a coffee object and returns reference to it.
     */
    private Coffee getCoffeeFromData(String coffeeType,HashSet<AddOn> addOns){
        switch(coffeeType){
            case "Short":
                return new Coffee(Size.SHORT,addOns);
            case "Tall":
                return new Coffee(Size.TALL,addOns);
            case "Grande":
                return new Coffee(Size.GRANDE,addOns);
            case "Venti":
                return new Coffee(Size.VENTI,addOns);
        }
        return null;
    }
}