package com.example.project5cafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class ItemSelectedActivity extends AppCompatActivity {
    private Button btn_itemName;
    private ImageView donutImageView;
    private Spinner donutAmountSpinner;
    private TextView donutFlavor;
    private TextView donutFlavorSelected;
    private TextView price;
    private String [] donutAmounts = {"1", "2", "3", "4", "5","6"};
    private ArrayAdapter<String> adapter;

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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                price.setText("$0.00");
            }
        });
    }

    private String formatDouble(double ourDouble){
        return Double.toString( Math.round(ourDouble*100.00) / 100.00);
    }

    public void addDonutToBasket(View view){
        MenuItem menuItem = getMenuItem(donutFlavorSelected.getText().toString());
        String amount = donutAmountSpinner.getSelectedItem().toString();
        BasketItem basketItem = new BasketItem(menuItem,Integer.parseInt(amount));
        Toast.makeText(view.getContext(),basketItem.toString()+" Added.", Toast.LENGTH_LONG).show();

    }

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
}