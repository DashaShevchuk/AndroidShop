package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int quantity = 0;
    Spinner spinner;
    ArrayList spinnerArrayList;
    ArrayAdapter spinnerAdapter;

    HashMap goodsMap;
    String goodsName;
    double price;
    EditText userNameEditText;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameEditText = findViewById(R.id.username);
        email = findViewById(R.id.email);

        createSpinner();
        createMap();
    }

    void createSpinner() {
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spinnerArrayList = new ArrayList();

        spinnerArrayList.add("pizza");
        spinnerArrayList.add("burger");
        spinnerArrayList.add("hotdog");
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    void createMap() {
        goodsMap = new HashMap();
        goodsMap.put("pizza", 9.0);
        goodsMap.put("hotdog", 5.0);
        goodsMap.put("burger", 7.0);
    }

    public void increaseQuantity(View view) {
        quantity = quantity + 1;
        TextView quantityTextView = findViewById(R.id.count);
        quantityTextView.setText("" + quantity);
        TextView priceTextView = findViewById(R.id.price);
        priceTextView.setText("" + quantity * price);
    }

    public void decreaseQuantity(View view) {
        quantity = quantity - 1;
        if (quantity < 0) {
            quantity = 0;
        }
        TextView quantityTextView = findViewById(R.id.count);
        quantityTextView.setText("" + quantity);
        TextView priceTextView = findViewById(R.id.price);
        priceTextView.setText("" + quantity * price);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        goodsName = spinner.getSelectedItem().toString();
        price = (double)goodsMap.get(goodsName);
        TextView priceTextView = findViewById(R.id.price);
        priceTextView.setText("" + quantity * price);

        ImageView goodsImageView = findViewById(R.id.imageView);

        switch (goodsName) {
            case "pizza":
                goodsImageView.setImageResource(R.drawable.pizza);
                break;
            case "hotdog":
                goodsImageView.setImageResource(R.drawable.hotdog);
                break;
            case "burger":
                goodsImageView.setImageResource(R.drawable.burger);
                break;
            default:
                goodsImageView.setImageResource(R.drawable.pizza);
                break;
        }

    }

    public void addToCart (View view) {
        Order order = new Order();
        order.username = userNameEditText.getText().toString();
        order.price = price;
        order.totalPrice = quantity * price;
        order.count = quantity;
        order.email = email.getText().toString();

        Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);
        orderIntent.putExtra("userNameForIntent", order.username);
        orderIntent.putExtra("quantity", order.count);
        orderIntent.putExtra("price", order.price);
        orderIntent.putExtra("orderPrice", order.totalPrice);
        orderIntent.putExtra("email", order.email);


        startActivity(orderIntent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}