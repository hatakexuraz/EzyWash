package com.assignment.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class Dashboard extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String washPackage = "CLASS A WASH";
    private Button btn_next;
    private TextView txt_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        int radiobutton=2131230855;   //class a wash radio button id
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.check(radiobutton);  //choose first option as default
        btn_next = findViewById(R.id.btn_next);

        txt_error = findViewById(R.id.txt_error);
        txt_error.setVisibility(View.INVISIBLE);

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
                else if (washPackage.equals("CLASS C WASH")){
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
        Toast.makeText(this, "Selected: " +washPackage+ " id: "+radioID, LENGTH_LONG).show();

        Global.class_wash = washPackage;
    }
}
