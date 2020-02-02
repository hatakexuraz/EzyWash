package com.assignment.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText txt_email;
    private EditText txt_pass;
    private Button btn_login;
    private String email;
    private String password;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_email = findViewById(R.id.txt_login_email);
        txt_pass = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login_signin);

        Log.d("TAG", "Activity Login");
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Login button clicked");
                email = txt_email.getText().toString();
                password = txt_pass.getText().toString();

                if(email.isEmpty()){
                    txt_email.setError("Please enter email");
                    txt_email.requestFocus();
                }
                else if(password.isEmpty()){
                    txt_pass.setError("Please enter password");
                    txt_pass.requestFocus();
                }
                else if(email.isEmpty() && password.isEmpty()) {
                    txt_email.setError("Please enter email");
                    txt_email.requestFocus();
                    txt_pass.setError("Please enter password");
                    txt_pass.requestFocus();

                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_LONG).show();
                }
                else {
                    Log.d("TAG", "email & pass entered");
                     logUserIn(email, password);
                }
            }
        });
    }

    //checks user email and password to verify the user with database
    private void logUserIn(final String email, final String password){
        Log.d("TAG", "Inside login function");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        final Query query = databaseReference.orderByChild("email").equalTo(email);   //checks email in the firebase
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Global.email = email;   //saved as Global value

                    Query query2 = databaseReference.orderByChild("password").equalTo(password);  //checks password in firebase
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                Intent dashboard = new Intent(LoginActivity.this, Dashboard.class);
                                startActivity(dashboard);
                            }
                            else {
                                txt_pass.setText("");
                                txt_pass.setError("Password is incorrect");
                                txt_pass.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    txt_email.setText("");
                    txt_email.setError("Email is incorrect");
                    txt_email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String data_email = dataSnapshot.child("email").getValue().toString();
//                String data_pass = dataSnapshot.child("password").getValue().toString();
//
//                if (email.equals(data_email) && password.equals(data_pass)){
//                    Toast.makeText(LoginActivity.this, "User is correct", Toast.LENGTH_LONG).show();
//                }
//                else {
//                    Toast.makeText(LoginActivity.this, "User is coinrrect", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
