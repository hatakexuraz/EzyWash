package com.assignment.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ClassWashB extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_wash_b);

        listView = findViewById(R.id.package_list);

        ArrayList<String> features = new ArrayList<>();
        features.add("Full Wash Exterior");
        features.add("Hi-Foam Hand Shampoo");
        features.add("Tyre Dress & Rim Shine");
        features.add("Windows Exterior & Interior");
        features.add("Interior Vaccum");
        features.add("Deodorised");
        features.add("Door Panels & Frames");
        features.add("Machine & Hand Polish");
        features.add("Upholstery Seats Shampoo");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, features);
        listView.setAdapter(adapter);

        Button button;
        button = findViewById(R.id.btn_select);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassWashB.this, BookingActivity.class);
                startActivity(intent);
            }
        });
    }
}
