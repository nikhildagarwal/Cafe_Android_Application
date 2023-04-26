package com.example.project5cafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project5cafeapp.cofee.AddOn;
import com.example.project5cafeapp.cofee.Coffee;
import com.example.project5cafeapp.cofee.Size;
import com.example.project5cafeapp.data.MenuItem;

import java.util.HashSet;

public class coffeeActivity extends AppCompatActivity{

    private Spinner coffeeSizeSpinner;
    private Spinner coffeeAmountSpinner;
    private String [] coffeeSizes = {"Short", "Tall", "Grande", "Venti"};
    private String [] coffeeAmount = {"1", "2", "3", "4", "5"};
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private TextView total;
    private CheckBox sweetCreamBox;
    private CheckBox mochaBox;
    private CheckBox vanillaBox;
    private CheckBox caramelBox;
    private CheckBox creamBox;

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
    }

    public void updatePrice(View view){
        boolean sc = sweetCreamBox.isChecked();
        boolean m = mochaBox.isChecked();
        boolean fv = vanillaBox.isChecked();
        boolean c = caramelBox.isChecked();
        boolean ic = creamBox.isChecked();
        Log.d("no",sc+"");
        Log.d("no",m+"");
        Log.d("no",fv+"");
        Log.d("no",c+"");
        Log.d("no",ic+"");
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