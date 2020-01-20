package com.assignment.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_main_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_main_signin = findViewById(R.id.btn_sign_in);

        btn_main_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(v.getContext(), LoginActivity.class);
                startActivity(login);
            }
        });
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }
}
