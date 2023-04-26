package com.example.project5cafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    }

    public void updatePrice(View view){
        boolean sc = sweetCreamBox.isChecked();
        boolean m = mochaBox.isChecked();
        boolean fv = vanillaBox.isChecked();
        boolean c = caramelBox.isChecked();
        boolean ic = creamBox.isChecked();
        
    }
}