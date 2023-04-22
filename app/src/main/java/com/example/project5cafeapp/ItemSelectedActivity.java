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

public class ItemSelectedActivity extends AppCompatActivity {
    private Button btn_itemName;
    private ImageView donutImageView;
    private Spinner donutAmountSpinner;
    private TextView donutFlavor;

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



    }
}