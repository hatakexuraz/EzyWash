package com.assignment.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private TextView txt_forget_pass;
    private ProgressBar progressBar;
    private TextView txt_messsage;

    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        txt_email = findViewById(R.id.txt_login_email);
        txt_pass = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login_signin);
        txt_forget_pass = findViewById(R.id.txt_forget_pass);
        progressBar = findViewById(R.id.progressBar);
        txt_messsage = findViewById(R.id.please_wait);

        progressBar.setVisibility(View.GONE);
        txt_messsage.setVisibility(View.GONE);

        Log.d("TAG", "Activity Login");
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                txt_messsage.setVisibility(View.VISIBLE);
                Log.d("TAG", "Login button clicked");
                email = txt_email.getText().toString();
                password = txt_pass.getText().toString();

                if(email.isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    txt_messsage.setVisibility(View.GONE);
                    txt_email.setError("Please enter email");
                    txt_email.requestFocus();
                }
                else if(password.isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    txt_messsage.setVisibility(View.GONE);
                    txt_pass.setError("Please enter password");
                    txt_pass.requestFocus();
                }
                else if(email.isEmpty() && password.isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    txt_messsage.setVisibility(View.GONE);
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

        txt_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    //checks user email and password to verify the user with database
    private void logUserIn(final String email, final String password){
        Log.d("TAG", "Inside login function");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    if (firebaseAuth.getCurrentUser().isEmailVerified()){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userid=user.getUid();
                        Global.email = email;   //saved as Global value
                        Global.uid = databaseReference.push().getKey();
                        Log.d("LoginActivity", "User id is: "+Global.uid);
                        txt_email.setText("");
                        txt_pass.setText("");
                        progressBar.setVisibility(View.GONE);
                        txt_messsage.setVisibility(View.GONE);
                        Intent dashboard = new Intent(LoginActivity.this, Dashboard2.class);
                        startActivity(dashboard);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Please verify you email address", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    txt_messsage.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
//        final Query query = databaseReference.orderByChild("email").equalTo(email);   //checks email in the firebase
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    Global.email = email;   //saved as Global value
//                    Log.d("LoginActivity", "Global email: "+Global.email);
//                    Query query2 = databaseReference.orderByChild("password").equalTo(password);  //checks password in firebase
//                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()){
//
//                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                                String userid=user.getUid();
//                                Global.uid = databaseReference.push().getKey();
//                                Log.d("LoginActivity", "User id is: "+Global.uid);
//                                Intent dashboard = new Intent(LoginActivity.this, Dashboard2.class);
//                                startActivity(dashboard);
//                            }
//                            else {
//                                txt_pass.setText("");
//                                txt_pass.setError("Password is incorrect");
//                                txt_pass.requestFocus();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//                else {
//                    txt_email.setText("");
//                    txt_email.setError("Email is incorrect");
//                    txt_email.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                throw databaseError.toException();
//            }
//        });

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
