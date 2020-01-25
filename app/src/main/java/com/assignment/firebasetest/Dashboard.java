package com.assignment.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String washPackage;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        radioGroup = findViewById(R.id.radioGroup);
        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (washPackage.equals("CLASS A WASH")){
                    Intent intent = new Intent(v.getContext(), ClassWash.class);
                    startActivity(intent);
                }
                else if (washPackage.equals("CLASS B WASH")){
                    Intent intent = new Intent(v.getContext(), ClassWashB.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(v.getContext(), ClassWashC.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void checkButton(View v){
        int radioID = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioID);
        washPackage = radioButton.getText().toString();
        Toast.makeText(this, "Selected: " +washPackage, Toast.LENGTH_LONG).show();
    }
}
